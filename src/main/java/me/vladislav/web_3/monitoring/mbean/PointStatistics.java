package me.vladislav.web_3.monitoring.mbean;

import lombok.NoArgsConstructor;
import me.vladislav.web_3.beans.PointBean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
@NoArgsConstructor
public class PointStatistics implements PointStatisticsMBean, Serializable {
    private PointBean pointBean;


    public PointStatistics(PointBean pointBean) {
        this.pointBean = pointBean;
    }

    @Override
    public long getTotalPoints() {
        return pointBean.getResultListOfPoints().size();
    }

    @Override
    public long getPointsOutsideArea() {
        return pointBean.getResultListOfPoints()
                .stream()
                .filter(pointDTO -> !pointDTO.isResult()).count();
    }

    @Override
    public void notifyOutOfBounds(double x, double y) {

    }
}
