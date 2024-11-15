package me.vladislav.web_3.beans;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String getResultListOfPointsAsJson() {
        System.out.println("Return list of points as json");
        ObjectMapper mapper = new ObjectMapper();
//        TODO: need to add calling PointRepository method;
        try {
            return mapper.writeValueAsString(listOfPoints);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPoint() {
        System.out.println("Add new point");
        Point resultPoint = new Point(currentPoint.getX(), currentPoint.getY(), currentPoint.getR());
        //TODO: need to add db save logic
        listOfPoints.add(listOfPoints.size(), resultPoint);

        currentPoint = new Point();
    }

}