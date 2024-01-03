package br.com.mateusferian.voicecommands.controllers.impl;

import br.com.mateusferian.voicecommands.controllers.VoiceController;
import br.com.mateusferian.voicecommands.models.AudioModel;
import br.com.mateusferian.voicecommands.models.impl.ThreadVoiceModelImpl;
import br.com.mateusferian.voicecommands.views.ColorScreenView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VoiceControllerImpl implements ActionListener, VoiceController {

    private ColorScreenView colorScreenView;
    private AudioModel audioModel;

    private static final Color COLOR_OF_LIGHT = new Color(255, 255, 153);
    private static final Color RESET_COLOR = new Color(30, 30, 30);
    private static final String VOICE_GREETING = "Hello, my name is Alfred, how can I help you?";
    private static final String LOADING_TEXT = "Loading...";


    private static final String TURN_ON_KITCHEN_LIGHTS = "turn on kitchen lights";
    private static final String TURN_ON_LIVING_ROOM_LIGHTS = "turn on living room lights";
    private static final String TURN_ON_ROOM_LIGHTS = "turn on room lights";
    private static final String TURN_ON_ALL_LIGHTS = "turn on all the lights";

    public VoiceControllerImpl(ColorScreenView colorScreenView, AudioModel audioControllerModel) {
        this.colorScreenView = colorScreenView;
        this.colorScreenView.getVoiceCallButton().addActionListener(this);
        this.audioModel = audioControllerModel;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        audioModel.speak(VOICE_GREETING);
        changeColor();
    }

    private void changeColor() {
        colorScreenView.getVoiceCallButton().setText(LOADING_TEXT);
        ThreadVoiceModelImpl threadVoz = new ThreadVoiceModelImpl(this, colorScreenView.getVoiceCallButton());
        threadVoz.execute();
    }

    @Override
    public void executeVoiceCommand(String text) throws Exception {
        resetColorLabels();
        switch (text.toLowerCase()) {
            case TURN_ON_KITCHEN_LIGHTS:
                colorScreenView.getKitchenLights().setBackground(COLOR_OF_LIGHT);
                audioModel.playSound();
                break;
            case TURN_ON_LIVING_ROOM_LIGHTS:
                colorScreenView.getLivingRoomLights().setBackground(COLOR_OF_LIGHT);
                audioModel.playSound();
                break;
            case TURN_ON_ROOM_LIGHTS:
                colorScreenView.getRoomLights().setBackground(COLOR_OF_LIGHT);
                audioModel.playSound();
                break;
            case TURN_ON_ALL_LIGHTS:
                colorScreenView.getKitchenLights().setBackground(COLOR_OF_LIGHT);
                colorScreenView.getLivingRoomLights().setBackground(COLOR_OF_LIGHT);
                colorScreenView.getRoomLights().setBackground(COLOR_OF_LIGHT);
                audioModel.playSound();
                break;
            default:
                break;
        }
    }

    private void resetColorLabels() {
        colorScreenView.getKitchenLights().setBackground(RESET_COLOR);
        colorScreenView.getLivingRoomLights().setBackground(RESET_COLOR);
        colorScreenView.getRoomLights().setBackground(RESET_COLOR);
    }
}