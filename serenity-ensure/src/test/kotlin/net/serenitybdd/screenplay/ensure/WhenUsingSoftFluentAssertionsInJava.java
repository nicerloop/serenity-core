package net.serenitybdd.screenplay.ensure;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SerenityRunner.class)
/**
 * Some high level smoke tests
 */
public class WhenUsingSoftFluentAssertionsInJava {

    Actor aster = Actor.named("Aster");

    @Before
    public void prepareSoftAsserts() {
        Ensure.enableSoftAssertions();
    }

    @After
    public void reportSoftAsserts() {
        Ensure.reportSoftAssertions();
    }

    @Test
    public void aTestWithSomeSoftAsserts() {

        int age = 20;

        aster.attemptsTo(
                Ensure.that(age).isEqualTo(20)
        );
    }

    @Test
    public void anotherTestWithSomeSoftAsserts() {
        Actor aster = Actor.named("Aster");

        String color = "red";

        aster.attemptsTo(
                Ensure.that(color).startsWith("r"),
                Ensure.that(color).endsWith("d"),
                Ensure.that(color).hasSize(3)
        );
    }

    @Test
    public void shouldSupportSoftAssertionsWithQuestionPredicates() {
        // Manage soft assertions manually for this test so we can verify the error
        Ensure.reportSoftAssertions(); // End the @Before soft assertions
        try {
            Ensure.enableSoftAssertions();
            Question<String> colorRed = Question.about("color red").answeredBy(actor -> "RED");

            aster.attemptsTo(
                    Ensure.that("color starts with Z", colorRed, value -> value.startsWith("Z")),
                    Ensure.that("color ends with Z", colorRed, value -> value.endsWith("Z"))
            );
            Ensure.reportSoftAssertions();
            fail("Should have thrown an AssertionError");
        } catch (AssertionError e) {
            assertThat(e.getMessage()).contains("ERROR 1");
            assertThat(e.getMessage()).contains("ERROR 2");
        }
    }

}
