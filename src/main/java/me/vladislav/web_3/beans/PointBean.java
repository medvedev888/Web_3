package me.vladislav.web_3.beans;

import lombok.Data;
import me.vladislav.web_3.models.Point;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PointBean implements Serializable {
    private Point currentPoint;
    private List<Point> listOfPoints;

    @PostConstruct
    private void initialize() {
        System.out.println("Init method");
        listOfPoints = new ArrayList<>();
        currentPoint = new Point();
    }

    public List<Point> getResultListOfPoints() {
        System.out.println("Return list of points");
//        TODO: need to add calling PointRepository method;
        return listOfPoints;
    }

    public void addPoint() {
        System.out.println("Add new point");
        //TODO: need to add db save logic
        listOfPoints.add(currentPoint);

        currentPoint = new Point();
    }

}