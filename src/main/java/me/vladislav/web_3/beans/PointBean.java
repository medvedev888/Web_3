package me.vladislav.web_3.beans;

import lombok.Data;
import me.vladislav.web_3.models.Point;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class PointBean implements Serializable {
    private Point currentPoint;
    private List<Point> listOfPoints;
    private String newR;

    @PostConstruct
    private void initialize() {
        listOfPoints = new ArrayList<>();
        currentPoint = new Point();
    }

    public List<Point> getResultListOfPoints() {
//        TODO: need to add calling PointRepository method;
        return listOfPoints;
    }

    public void addPoint() {
        Point resultPoint = new Point(currentPoint.getX(), currentPoint.getY(), currentPoint.getR());
        //TODO: need to add db save logic
        listOfPoints.add(listOfPoints.size(), resultPoint);

        currentPoint = new Point();
    }

    public void updatePoints() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        if (!map.isEmpty()) {
            String rStr = (String) map.get("pointForm:rValue");
//        String rStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("r");
            double r = Double.parseDouble(rStr);

            System.out.println("update Points is working!!   r == " + r);
        }


//        double newR = currentPoint.getR();
//        for (Point point : listOfPoints) {
//            point.setR(newR);
//            point.setResult(point.checkArea(point.getX(), point.getY(), point.getR()));
//        }

    }

}