package me.vladislav.web_3.beans;

import lombok.Data;
import me.vladislav.web_3.dao.PointDataAccessObject;
import me.vladislav.web_3.dto.PointDTO;
import me.vladislav.web_3.models.Point;
import me.vladislav.web_3.utils.HibernateUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
public class PointBean implements Serializable {
    private PointDataAccessObject pointDataAccessObject;
    private PointDTO currentPointDTO;

    @PostConstruct
    private void initialize() {
        pointDataAccessObject = new PointDataAccessObject();
        currentPointDTO = new PointDTO();
    }

    @PreDestroy
    private void preDestroy() {
        HibernateUtils.shutdown();
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
        PointDTO resultPointDTO = new PointDTO(currentPointDTO.getX(), currentPointDTO.getY(), currentPointDTO.getR());
        Point point = new Point(resultPointDTO.getX(), resultPointDTO.getY(), resultPointDTO.getR(), resultPointDTO.isResult());
        pointDataAccessObject.save(point);
        currentPointDTO = new PointDTO();
    }

    public void updatePoints() {
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (!map.isEmpty()) {
            String rStr = map.get("pointForm:rValue");
            double r = Double.parseDouble(rStr);

            pointDataAccessObject.getList().ifPresent(points -> points.forEach(point -> {
                point.setR(r);
                point.setResult(new PointDTO(point.getX(), point.getY(), point.getR()).checkArea(point.getX(), point.getY(), r));
                pointDataAccessObject.updatePoint(point);
            }));
        }
    }

}
