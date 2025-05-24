package me.vladislav.web_3.monitoring.mbean;

import me.vladislav.web_3.beans.PointBean;
import me.vladislav.web_3.dto.PointDTO;

import javax.management.*;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class PointStatistics
        extends NotificationBroadcasterSupport
        implements PointStatisticsMBean, NotificationBroadcaster, Serializable {

    private final AtomicLong sequenceNumber = new AtomicLong(1);
    private final PointBean pointBean;

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
                .filter(p -> !p.isResult())
                .count();
    }

    @Override
    public void notifyOutOfBounds(double x, double y, double r) {
        String message = String.format("Точка вне области: (x=%.2f, y=%.2f, r=%.2f)",
                x, y, r);
        Notification n = new Notification(
                "me.vladislav.web_3.outOfBounds",
                this,
                sequenceNumber.getAndIncrement(),
                System.currentTimeMillis(),
                message
        );
        sendNotification(n);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{ "me.vladislav.web_3.outOfBounds" };
        String name  = Notification.class.getName();
        String desc  = "Событие при выходе точки за пределы области";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, desc);
        return new MBeanNotificationInfo[]{ info };
    }
}
