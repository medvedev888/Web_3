package me.vladislav.web_3.monitoring.mbean;

import javax.management.MXBean;

@MXBean
public interface ClickIntervalMBean {

    // Средний интервал между кликами в миллисекундах
    double getAverageClickIntervalMillis();

    // Вызывается при каждом клике
    void registerClick(long timestamp);

    void reset();
}

