package net.thucydides.core.scheduling;

import net.thucydides.core.scheduling.fluent.PollingSchedule;
import net.thucydides.core.scheduling.fluent.TimeoutSchedule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public class SerenityFluentWait extends FluentWait<WebDriver> {
    public SerenityFluentWait(WebDriver input) {
        super(input);
    }


    public TimeoutSchedule withTimeoutOf(int amount) {
        return new TimeoutSchedule(this, amount);
    }

    public PollingSchedule pollingEvery(int amount) {
        return new PollingSchedule(this, amount);
    }

}
