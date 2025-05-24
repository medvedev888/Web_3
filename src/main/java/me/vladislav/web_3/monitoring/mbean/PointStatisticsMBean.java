package me.vladislav.web_3.monitoring.mbean;

import me.vladislav.web_3.dto.PointDTO;

import javax.management.MXBean;

@MXBean
public interface PointStatisticsMBean {

    long getTotalPoints(); // Общее число точек, установленных пользователем

    long getPointsOutsideArea(); // Число точек, не попавших в область

    void notifyOutOfBounds(double x, double y, double r); // Вызывается, если точка вне отображаемой области
}

