// ColorScreen.java
package br.com.mateusferian.voicecommands.views;

import br.com.mateusferian.voicecommands.controllers.impl.VoiceControllerImpl;
import br.com.mateusferian.voicecommands.models.AudioModel;
import br.com.mateusferian.voicecommands.models.impl.AudioModelImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ColorScreenView extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String FRAME_TITLE = "Lights";
	private static final String START_BUTTON_LABEL = "START";
	private static final String KITCHEN_LABEL_TEXT = "Kitchen";
	private static final String LIVING_ROOM_LABEL_TEXT = "Living room";
	private static final String ROOM_LABEL_TEXT = "Room";

	private static final Color PANEL_BACKGROUND_COLOR = new Color(30, 30, 30);
	private static final Color BUTTON_BACKGROUND_COLOR = new Color(200, 200, 200);
	private static final Color DEFAULT_LABEL_BACKGROUND_COLOR = new Color(30, 30, 30);
	private static final Color DEFAULT_LABEL_BORDER_COLOR = Color.BLACK;

	private static final Font TITLE_FONT = new Font("Tahoma", Font.BOLD, 18);
	private static final Font BUTTON_FONT = new Font("Tahoma", Font.PLAIN, 15);

	private static final Insets PANEL_BORDER_INSETS = new Insets(10, 10, 10, 10);
	private static final int FLOW_LAYOUT_HGAP = 20;
	private static final int FLOW_LAYOUT_VGAP = 0;

	private static final int FRAME_X = 100;
	private static final int FRAME_Y = 100;
	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HEIGHT = 300;
	private static final int LABEL_PREFERRED_WIDTH = 100;
	private static final int LABEL_PREFERRED_HEIGHT = 100;

	private JPanel panel;
	private JButton voiceCallButton;
	private JLabel kitchenLights, livingRoomLights, roomLights;

	public static void main(String[] args) throws Exception {
		ColorScreenView frame = new ColorScreenView();
		frame.setVisible(true);
	}

	public ColorScreenView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);

		panel = new JPanel();
		panel.setBackground(PANEL_BACKGROUND_COLOR);
		panel.setBorder(new EmptyBorder(PANEL_BORDER_INSETS));
		panel.setLayout(new BorderLayout());
		setContentPane(panel);

		createTitlePanel();
		createButtonAndLabelsPanel();
	}

	private void createTitlePanel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(PANEL_BACKGROUND_COLOR);
		JLabel titleLabel = new JLabel(FRAME_TITLE, JLabel.CENTER);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(TITLE_FONT);
		titlePanel.add(titleLabel);

		panel.add(titlePanel, BorderLayout.NORTH);
	}

	private void createButtonAndLabelsPanel() {
		voiceCallButton = new JButton(START_BUTTON_LABEL);
		voiceCallButton.setFont(BUTTON_FONT);
		voiceCallButton.setBackground(BUTTON_BACKGROUND_COLOR);
		AudioModel audioModel = new AudioModelImpl();
		voiceCallButton.addActionListener(new VoiceControllerImpl(this, audioModel));
		kitchenLights = createColorLabel(null, KITCHEN_LABEL_TEXT);
		livingRoomLights = createColorLabel(null, LIVING_ROOM_LABEL_TEXT);
		roomLights = createColorLabel(null, ROOM_LABEL_TEXT);

		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, FLOW_LAYOUT_HGAP, FLOW_LAYOUT_VGAP));
		colorPanel.setBackground(PANEL_BACKGROUND_COLOR);
		colorPanel.add(kitchenLights);
		colorPanel.add(livingRoomLights);
		colorPanel.add(roomLights);

		panel.add(colorPanel, BorderLayout.CENTER);
		panel.add(voiceCallButton, BorderLayout.SOUTH);
	}

	private JLabel createColorLabel(Color color, String title) {
		JLabel colorLabel = new JLabel(title, JLabel.CENTER);
		colorLabel.setOpaque(true);
		colorLabel.setBackground(color != null ? color : DEFAULT_LABEL_BACKGROUND_COLOR);
		colorLabel.setBorder(BorderFactory.createLineBorder(DEFAULT_LABEL_BORDER_COLOR));
		colorLabel.setPreferredSize(new Dimension(LABEL_PREFERRED_WIDTH, LABEL_PREFERRED_HEIGHT));
		return colorLabel;
	}

	public JButton getVoiceCallButton() {
		return voiceCallButton;
	}

	public JLabel getKitchenLights() {
		return kitchenLights;
	}

	public JLabel getLivingRoomLights() {
		return livingRoomLights;
	}

	public JLabel getRoomLights() {
		return roomLights;
	}
}

