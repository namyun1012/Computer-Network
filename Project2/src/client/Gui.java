package client;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gui extends JFrame {
	
	public Gui() {
		JLabel imgLabel = new JLabel();
		
		ImageIcon icon = new ImageIcon(Gui.class.getResource("image.jpg"));
		imgLabel.setIcon(icon);
		getContentPane().add(imgLabel);
		
		imgLabel.setBounds(210,  30, 165, 150);
		imgLabel.setHorizontalAlignment(JLabel.CENTER);
		
		
		setVisible(true);
	}
}
