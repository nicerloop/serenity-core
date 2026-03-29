package net.serenitybdd.core.photography;

import net.thucydides.model.domain.TestResult;
import net.thucydides.model.environment.MockEnvironmentVariables;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoundEngineerTest {

    @Test
    void shouldReturnDisabledRecorderByDefault() {
        MockEnvironmentVariables env = new MockEnvironmentVariables();
        SoundEngineer engineer = new SoundEngineer(env);

        PageSourceRecorder recorder = engineer.recordPageSourceUsing(null);

        assertInstanceOf(DisabledPageSourceRecorder.class, recorder);
    }

    @Test
    void shouldRecordPageSourceForFailuresWithDefaultConfig() {
        MockEnvironmentVariables env = new MockEnvironmentVariables();
        SoundEngineer engineer = new SoundEngineer(env);

        PageSourceRecorder recorder = engineer
                .ifRequiredForResult(TestResult.FAILURE)
                .recordPageSourceUsing(null);

        assertNotEquals(DisabledPageSourceRecorder.class, recorder.getClass());
    }

    @Test
    void shouldRecordPageSourceForErrorsWithDefaultConfig() {
        MockEnvironmentVariables env = new MockEnvironmentVariables();
        SoundEngineer engineer = new SoundEngineer(env);

        PageSourceRecorder recorder = engineer
                .ifRequiredForResult(TestResult.ERROR)
                .recordPageSourceUsing(null);

        assertNotEquals(DisabledPageSourceRecorder.class, recorder.getClass());
    }

    @Test
    void shouldNotRecordPageSourceForSuccessWithDefaultConfig() {
        MockEnvironmentVariables env = new MockEnvironmentVariables();
        SoundEngineer engineer = new SoundEngineer(env);

        PageSourceRecorder recorder = engineer
                .ifRequiredForResult(TestResult.SUCCESS)
                .recordPageSourceUsing(null);

        assertInstanceOf(DisabledPageSourceRecorder.class, recorder);
    }

    @Test
    void shouldAlwaysRecordPageSourceWhenConfiguredToAlways() {
        MockEnvironmentVariables env = new MockEnvironmentVariables();
        env.setProperty("serenity.store.html", "ALWAYS");
        SoundEngineer engineer = new SoundEngineer(env);

        PageSourceRecorder recorder = engineer
                .ifRequiredForResult(TestResult.SUCCESS)
                .recordPageSourceUsing(null);

        assertNotEquals(DisabledPageSourceRecorder.class, recorder.getClass());
    }

    @Test
    void shouldNeverRecordPageSourceWhenConfiguredToNever() {
        MockEnvironmentVariables env = new MockEnvironmentVariables();
        env.setProperty("serenity.store.html", "NEVER");
        SoundEngineer engineer = new SoundEngineer(env);

        PageSourceRecorder recorder = engineer
                .ifRequiredForResult(TestResult.FAILURE)
                .recordPageSourceUsing(null);

        assertInstanceOf(DisabledPageSourceRecorder.class, recorder);
    }
}
