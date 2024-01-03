// ThreadVoiceModelImpl.java
package br.com.mateusferian.voicecommands.models.impl;

import br.com.mateusferian.voicecommands.controllers.VoiceController;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.IOException;

@Slf4j
public class ThreadVoiceModelImpl extends SwingWorker<Void, Void> {

    private static LiveSpeechRecognizer liveSpeechRecognizer;
    private static VoiceController voiceController;
    private static JButton voiceCallButton;

    public static final String ACOUSTIC_MODEL_PATH = "resource:/edu/cmu/sphinx/models/en-us/en-us";
    public static final String DICTIONARY_PATH = "resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict";
    public static final String GRAMMAR_PATH = "resource:/grammars";
    public static final String GRAMMAR_NAME = "grammar";

    public ThreadVoiceModelImpl(VoiceController voiceController, JButton voiceCallButton) {
        this.voiceController = voiceController;
        this.voiceCallButton = voiceCallButton;

        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath(ACOUSTIC_MODEL_PATH);
        configuration.setDictionaryPath(DICTIONARY_PATH);
        configuration.setGrammarPath(GRAMMAR_PATH);
        configuration.setGrammarName(GRAMMAR_NAME);
        configuration.setUseGrammar(true);

        try {
            liveSpeechRecognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            log.error("Error initializing speech recognizer", e);
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        startRecognition(voiceController, voiceCallButton);
        return null;
    }

    public void startRecognition(VoiceController voiceController, JButton voiceCallButton) throws Exception {
        if (liveSpeechRecognizer != null) {
            try {
                liveSpeechRecognizer.startRecognition(true);
            } catch (Exception e) {
                log.error("Error starting voice recognition", e);
                return;
            }
        } else {
            log.error("Speech recognition not initialized correctly");
            return;
        }

        voiceCallButton.setText("Listening...");

        while (!isCancelled()) {
            SpeechResult resultSpeech = liveSpeechRecognizer.getResult();
            if (resultSpeech == null) {
                log.warn("I couldn't understand what you said :(");
            } else {
                String recognizerResult = resultSpeech.getHypothesis();
                log.info("You said '{}'", recognizerResult);
                voiceController.executeVoiceCommand(recognizerResult);
            }
        }

        liveSpeechRecognizer.stopRecognition();
        voiceCallButton.setText("START");
    }
}


