package net.thucydides.model.domain.screenshots;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScreenshotTest {

    @Test
    void shouldStoreHtmlSourceName() {
        Screenshot screenshot = new Screenshot("screenshot.png", "Step description", 800, 1000L, null, 0, "pagesource123.html");

        assertEquals("pagesource123.html", screenshot.getHtmlSourceName());
        assertTrue(screenshot.hasHtmlSource());
    }

    @Test
    void shouldReportNoHtmlSourceWhenNull() {
        Screenshot screenshot = new Screenshot("screenshot.png", "Step description", 800, 1000L, null, 0, null);

        assertNull(screenshot.getHtmlSourceName());
        assertFalse(screenshot.hasHtmlSource());
    }

    @Test
    void shouldDefaultHtmlSourceToNull() {
        Screenshot screenshot = new Screenshot("screenshot.png", "Step description", 800, 1000L);

        assertNull(screenshot.getHtmlSourceName());
        assertFalse(screenshot.hasHtmlSource());
    }

    @Test
    void withDescriptionShouldPreserveHtmlSourceName() {
        Screenshot original = new Screenshot("screenshot.png", "Original", 800, 1000L, null, 0, "pagesource.html");
        Screenshot copy = original.withDescription("Updated");

        assertEquals("Updated", copy.getDescription());
        assertEquals("pagesource.html", copy.getHtmlSourceName());
    }

    @Test
    void withDepthShouldPreserveHtmlSourceName() {
        Screenshot original = new Screenshot("screenshot.png", "Step", 800, 1000L, null, 0, "pagesource.html");
        Screenshot copy = original.withDepth(3);

        assertEquals(3, copy.getDepth());
        assertEquals("pagesource.html", copy.getHtmlSourceName());
    }

    @Test
    void beforeShouldPreserveHtmlSourceName() {
        Screenshot original = new Screenshot("screenshot.png", "Step", 800, 1000L, null, 0, "pagesource.html");
        Screenshot copy = original.before();

        assertEquals(999L, copy.getTimestamp());
        assertEquals("pagesource.html", copy.getHtmlSourceName());
    }
}
