package net.serenitybdd.screenplay.ensure

import net.serenitybdd.annotations.Step
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.Performable
import net.serenitybdd.screenplay.Question

/**
 * A Performable that evaluates a Question with a predicate, with support for soft assertions.
 * Unlike AnonymousPerformableFunction, this class routes failures through handleException(),
 * which checks BlackBox.isUsingSoftAssertions() and defers errors when soft assertions are active.
 */
class PerformableQuestionPredicate<A>(
    private val questionDescription: String,
    private val question: Question<A>,
    private val predicate: (A) -> Boolean
) : Performable {

    private val description = "Ensure that $questionDescription"

    @Step("{0} should see #description")
    override fun <T : Actor?> performAs(actor: T) {
        BlackBox.reset()
        val actual = question.answeredBy(actor)
        if (!predicate.invoke(actual)) {
            handleException("Expected $questionDescription but was $actual")
        }
    }
}
