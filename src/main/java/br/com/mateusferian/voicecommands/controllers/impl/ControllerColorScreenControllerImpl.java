package br.com.mateusferian.voicecommands.controllers.impl;

import br.com.mateusferian.voicecommands.controllers.VoiceController;
import br.com.mateusferian.voicecommands.models.AudioModel;
import br.com.mateusferian.voicecommands.models.impl.ThreadVoiceModelImpl;
import br.com.mateusferian.voicecommands.view.ColorScreen;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ControllerColorScreenControllerImpl implements ActionListener, VoiceController {

    private ColorScreen colorScreen;
    private AudioModel audioControllerModel;

    private static final Color COLOR_OF_LIGHT = new Color(255, 255, 153);
    private static final Color RESET_COLOR = new Color(30, 30, 30);
    private static final String VOICE_GREETING = "Hello, my name is Alfred, how can I help you?";
    private static final String LOADING_TEXT = "Loading...";


    private static final String TURN_ON_KITCHEN_LIGHTS = "turn on kitchen lights";
    private static final String TURN_ON_LIVING_ROOM_LIGHTS = "turn on living room lights";
    private static final String TURN_ON_ROOM_LIGHTS = "turn on room lights";
    private static final String TURN_ON_ALL_LIGHTS = "turn on all the lights";

    public ControllerColorScreenControllerImpl(ColorScreen colorScreen, AudioModel audioControllerModel) {
        this.colorScreen = colorScreen;
        this.colorScreen.getVoiceCallButton().addActionListener(this);
        this.audioControllerModel = audioControllerModel;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        audioControllerModel.speak(VOICE_GREETING);
        changeColor();
    }

    private void changeColor() {
        colorScreen.getVoiceCallButton().setText(LOADING_TEXT);
        ThreadVoiceModelImpl threadVoz = new ThreadVoiceModelImpl(this, colorScreen.getVoiceCallButton());
        threadVoz.execute();
    }

    @Override
    public void executeVoiceCommand(String text) throws Exception {
        resetColorLabels();
        switch (text.toLowerCase()) {
            case TURN_ON_KITCHEN_LIGHTS:
                colorScreen.getKitchenLights().setBackground(COLOR_OF_LIGHT);
                playSound();
                break;
            case TURN_ON_LIVING_ROOM_LIGHTS:
                colorScreen.getLivingRoomLights().setBackground(COLOR_OF_LIGHT);
                playSound();
                break;
            case TURN_ON_ROOM_LIGHTS:
                colorScreen.getRoomLights().setBackground(COLOR_OF_LIGHT);
                playSound();
                break;
            case TURN_ON_ALL_LIGHTS:
                colorScreen.getKitchenLights().setBackground(COLOR_OF_LIGHT);
                colorScreen.getLivingRoomLights().setBackground(COLOR_OF_LIGHT);
                colorScreen.getRoomLights().setBackground(COLOR_OF_LIGHT);
                playSound();
                break;
            default:
                break;
        }
    }

    private void resetColorLabels() {
        colorScreen.getKitchenLights().setBackground(RESET_COLOR);
        colorScreen.getLivingRoomLights().setBackground(RESET_COLOR);
        colorScreen.getRoomLights().setBackground(RESET_COLOR);
    }

    private void playSound() {
        Toolkit.getDefaultToolkit().beep();
    }
}