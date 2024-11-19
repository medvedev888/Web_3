package me.vladislav.web_3.beans;

import lombok.Data;
import me.vladislav.web_3.dao.PointDataAccessObject;
import me.vladislav.web_3.dto.PointDTO;
import me.vladislav.web_3.models.Point;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class PointBean implements Serializable {
    private PointDataAccessObject pointDataAccessObject;
    private PointDTO currentPointDTO;
    private List<PointDTO> listOfPointDTOS;

    @PostConstruct
    private void initialize() {
        pointDataAccessObject = new PointDataAccessObject();
        listOfPointDTOS = new ArrayList<>();
        currentPointDTO = new PointDTO();
    }

    public List<PointDTO> getResultListOfPoints() {
        // TODO: need to add calling PointRepository method;
        return listOfPointDTOS;
    }

    public void addPoint() {
        PointDTO resultPointDTO = new PointDTO(currentPointDTO.getX(), currentPointDTO.getY(), currentPointDTO.getR());
        Point point = new Point(resultPointDTO.getX(), resultPointDTO.getY(), resultPointDTO.getR(), resultPointDTO.isResult());
        pointDataAccessObject.save(point);
        currentPointDTO = new PointDTO();
    }

    public void updatePoints() {
        System.out.println("update Points");
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if (!map.isEmpty()) {
            String rStr = map.get("pointForm:rValue");
            double r = Double.parseDouble(rStr);

            for (PointDTO pointDTO : listOfPointDTOS) {
                pointDTO.setR(r);
                pointDTO.setResult(pointDTO.checkArea(pointDTO.getX(), pointDTO.getY(), pointDTO.getR()));
            }
        }

    }


}