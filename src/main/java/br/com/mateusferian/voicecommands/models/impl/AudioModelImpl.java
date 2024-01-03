package br.com.mateusferian.voicecommands.models.impl;

import br.com.mateusferian.voicecommands.models.AudioModel;
import lombok.extern.slf4j.Slf4j;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;

@Slf4j
public class AudioModelImpl implements AudioModel {

    private static final String VOICES_PROPERTY = "freetts.voices";
    private static final String VOICES_VALUE = "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";
    private static final String ENGINE_CENTRAL_NAME = "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral";
    private static final String SYNTHESIZER_MODE_NAME = "general";
    private static final Locale SYNTHESIZER_LOCALE = Locale.US;

    @Override
    public void speak(String text) {
        if (text != null) {
            try {
                System.setProperty(VOICES_PROPERTY, VOICES_VALUE);

                Central.registerEngineCentral(ENGINE_CENTRAL_NAME);

                SynthesizerModeDesc desc = new SynthesizerModeDesc(
                        null,
                        SYNTHESIZER_MODE_NAME,
                        SYNTHESIZER_LOCALE,
                        null,
                        null);

                Synthesizer synthesizer = Central.createSynthesizer(desc);
                synthesizer.allocate();
                synthesizer.resume();

                synthesizer.speakPlainText(text, null);
                synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

                synthesizer.deallocate();
                log.info("Talking application");
            } catch (Exception e) {
                log.error("Error while speaking", e);
            }
        }
    }
}

