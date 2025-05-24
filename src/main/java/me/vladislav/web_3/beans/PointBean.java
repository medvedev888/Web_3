package me.vladislav.web_3.beans;

import lombok.Data;
import me.vladislav.web_3.dao.PointDataAccessObject;
import me.vladislav.web_3.dto.PointDTO;
import me.vladislav.web_3.models.Point;
import me.vladislav.web_3.monitoring.mbean.ClickInterval;
import me.vladislav.web_3.monitoring.mbean.PointStatistics;
import me.vladislav.web_3.monitoring.mbean.PointStatisticsMBean;
import me.vladislav.web_3.utils.HibernateUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@SessionScoped
@Named
public class PointBean implements Serializable {
    private PointDataAccessObject pointDataAccessObject;
    private PointDTO currentPointDTO;

    private ObjectName clickIntervalName;
    private ObjectName pointStatisticsName;

    private ClickInterval clickInterval;
    private PointStatisticsMBean pointStatistics;

    @PostConstruct
    private void initialize() {
        pointDataAccessObject = new PointDataAccessObject();
        currentPointDTO = new PointDTO();

        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            String sessionId = getSessionId();

            // Создаем и регистрируем ClickInterval
            clickInterval = new ClickInterval();
            String clickIntervalNameStr = "me.vladislav.web_3.monitoring.mbean:type=clickInterval,session=" + sessionId;
            clickIntervalName = new ObjectName(clickIntervalNameStr);
            if (!mbs.isRegistered(clickIntervalName)) {
                mbs.registerMBean(clickInterval, clickIntervalName);
            }

            // Создаем и регистрируем PointStatistics, передаем this в конструктор (или сеттер)
            pointStatistics = new PointStatistics(this);
            String pointStatsNameStr = "me.vladislav.web_3.monitoring.mbean:type=pointStatistics,session=" + sessionId;
            pointStatisticsName = new ObjectName(pointStatsNameStr);
            if (!mbs.isRegistered(pointStatisticsName)) {
                mbs.registerMBean(pointStatistics, pointStatisticsName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void preDestroy() {
        try {
            HibernateUtils.shutdown();

            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            if (clickIntervalName != null && mbs.isRegistered(clickIntervalName)) {
                mbs.unregisterMBean(clickIntervalName);
            }

            if (pointStatisticsName != null && mbs.isRegistered(pointStatisticsName)) {
                mbs.unregisterMBean(pointStatisticsName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSessionId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) return "no-faces-context";

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return session != null ? session.getId() : "no-session";
    }


    public List<PointDTO> getResultListOfPoints() {
        return pointDataAccessObject.getList()
                .orElseGet(ArrayList::new)
                .stream()
                .map(point -> new PointDTO(
                        point.getX(),
                        point.getY(),
                        point.getR(),
                        point.getResult()
                )).collect(Collectors.toList());
    }

    public void addPoint() {
        try {
            PointDTO resultPointDTO = new PointDTO(currentPointDTO.getX(), currentPointDTO.getY(), currentPointDTO.getR());
            Point point = new Point(resultPointDTO.getX(), resultPointDTO.getY(), resultPointDTO.getR(), resultPointDTO.isResult());
            pointDataAccessObject.save(point);
            currentPointDTO = new PointDTO();

            if (!resultPointDTO.isResult()) {
                pointStatistics.notifyOutOfBounds(resultPointDTO.getX(), resultPointDTO.getY(), resultPointDTO.getR());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePoints() {
        try {
            Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (!map.isEmpty()) {
                String rStr = map.get("pointForm:rValue");
                double r = Double.parseDouble(rStr);

                pointDataAccessObject.getList().ifPresent(points -> points.forEach(point -> {
                    point.setR(r);
                    point.setResult(new PointDTO(point.getX(), point.getY(), point.getR()).checkArea(point.getX(), point.getY(), r));
                    pointDataAccessObject.update(point);
                }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePoints() {
        try {
            pointDataAccessObject.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleClientTime() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        String clientTime = params.get("pointBean.clientTime");
        if (clientTime != null) {
            try {
                long timestamp = Instant.parse(clientTime).toEpochMilli();
                clickInterval.registerClick(timestamp);
//                System.out.println("Зарегистрирован клик от клиента с временем: " + clientTime);
            } catch (DateTimeParseException e) {
                System.out.println("Некорректный формат времени: " + clientTime);
            }
        } else {
            System.out.println("Клиентское время не передано");
        }
    }


}
