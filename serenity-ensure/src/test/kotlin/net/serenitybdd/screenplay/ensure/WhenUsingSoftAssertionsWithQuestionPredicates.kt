package net.serenitybdd.screenplay.ensure

import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.Question
import net.thucydides.core.steps.BaseStepListener
import net.thucydides.core.steps.StepEventBus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.nio.file.Files

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WhenUsingSoftAssertionsWithQuestionPredicates {

    private val aster = Actor.named("Aster")

    @BeforeAll
    fun setup() {
        val outputDirectory = Files.createTempDirectory("output")
        val stepListener = BaseStepListener(outputDirectory.toFile())
        StepEventBus.getEventBus().registerListener(stepListener)
    }

    private fun colorRed(): Question<String> {
        return Question.about("color red").answeredBy { "RED" }
    }

    @Test
    fun `question predicate assertions should respect soft assertions`() {
        try {
            BlackBox.startSoftAssertions()

            // Both of these should be deferred, not thrown immediately
            PerformableQuestionPredicate("color starts with Z", colorRed()) { it.startsWith("Z") }
                .performAs(aster)
            PerformableQuestionPredicate("color ends with Z", colorRed()) { it.endsWith("Z") }
                .performAs(aster)

            BlackBox.reportAnySoftAssertions()
            fail("Should have thrown an AssertionError")
        } catch (e: AssertionError) {
            assertThat(e.message).contains("ERROR 1")
            assertThat(e.message).contains("ERROR 2")
        } finally {
            BlackBox.endSoftAssertions()
        }
    }

    @Test
    fun `question predicate assertions should throw immediately without soft assertions`() {
        BlackBox.endSoftAssertions()
        try {
            PerformableQuestionPredicate("color starts with Z", colorRed()) { it.startsWith("Z") }
                .performAs(aster)
            fail("Should have thrown an AssertionError")
        } catch (e: AssertionError) {
            assertThat(e.message).contains("Expected color starts with Z")
        }
    }

    @Test
    fun `question predicate assertions should pass when predicate matches`() {
        PerformableQuestionPredicate("color starts with R", colorRed()) { it.startsWith("R") }
            .performAs(aster)
    }
}
