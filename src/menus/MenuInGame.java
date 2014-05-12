package menus;

import menus.ObservableButton;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class MenuInGame extends JPanel {

	private static final long serialVersionUID = 1L;
	private ObservableButton pauseButton;
	private ObservableButton quitButton;

	private Font minecraftia;
	
	public MenuInGame() {
		this.minecraftia = TFont.loadFont("data/font/Minecraftia.ttf");
		
        pauseButton = new ObservableButton("Pause");
        add(pauseButton.getButton(),BorderLayout.CENTER);
        
        quitButton = new ObservableButton("Quitter");
        add(quitButton.getButton(),BorderLayout.CENTER);
        

	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       

    }
	
	@Override
	public Dimension getPreferredSize() {
        return new Dimension(this.getWidth(), this.getHeight());
    }

	public ObservableButton getPauseButton() {
		return pauseButton;
	}

	public ObservableButton getQuitButton() {
		return quitButton;
	}


}
