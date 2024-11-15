package me.vladislav.web_3.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.inject.Named;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Named("point")
public class Point implements Serializable {
    private double x;
    private double y;
    private double r;
    private boolean result;

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = checkArea(x, y, r);
    }

    // function for getting result
    public boolean checkArea(double x, double y, double r) {
        boolean result = false;

        //    TODO: need to change this block
        if (x >= 0 && y >= 0 && ((x * x + y * y) <= (r * r))) {
            result = true;
        } else if (x >= 0 && y <= 0 && x <= r && y >= (-1) * (r / 2)) {
            result = true;
        } else if (x <= 0 && x >= -1 * r && y <= 0 && y >= -1 * r && y >= (-1 * x) - r) {
            result = true;
        }

        return result;
    }

}
