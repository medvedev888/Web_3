package me.vladislav.web_3.monitoring.mbean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// MBean, определяющий средний интервал между кликами пользователя по координатной плоскости.

@SessionScoped
@Named
public class ClickInterval implements ClickIntervalMBean, Serializable {
    private final AtomicLong lastClickTime = new AtomicLong(-1);
    private final AtomicLong totalInterval = new AtomicLong(0);
    private final AtomicInteger intervalsCount = new AtomicInteger(0);

    @Override
    public void registerClick(long timestamp) {
        long prev = lastClickTime.getAndSet(timestamp);
        if (prev != -1) {
            long interval = timestamp - prev;
            totalInterval.addAndGet(interval);
            intervalsCount.incrementAndGet();
        }
    }

    @Override
    public double getAverageClickIntervalMillis() {
        int count = intervalsCount.get();
        if (count == 0) {
            return 0;
        }
        System.out.println(totalInterval.get() / count);
        return (double) totalInterval.get() / count;
    }

    @Override
    public void reset() {
        lastClickTime.set(-1);
        totalInterval.set(0);
        intervalsCount.set(0);
    }
}
