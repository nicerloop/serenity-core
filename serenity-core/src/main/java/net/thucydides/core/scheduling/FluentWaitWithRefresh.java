package net.thucydides.core.scheduling;

import org.openqa.selenium.WebDriver;

import java.time.Clock;

public class FluentWaitWithRefresh<T> extends ThucydidesFluentWait<T> {

    public FluentWaitWithRefresh(T input, Clock clock) {
        super(input, clock);
    }

    @Override
    public void doWait() throws InterruptedException {
        Thread.sleep(interval.toMillis());
        ((WebDriver) getInput()).navigate().refresh();
    }
}