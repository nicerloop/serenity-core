package net.thucydides.core.scheduling;

import java.time.Clock;

public class NormalFluentWait<T> extends ThucydidesFluentWait<T> {

    public NormalFluentWait(T input) {
        super(input, Clock.systemDefaultZone());
    }

    public NormalFluentWait(T input, Clock clock) {
        super(input, clock);
    }

    @Override
    public void doWait() throws InterruptedException {
        Thread.sleep(interval.toMillis());
    }
}