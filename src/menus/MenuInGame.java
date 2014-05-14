package menus;

import menus.ObservableButton;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class MenuInGame extends JPanel {

	private static final long serialVersionUID = 1L;
	private ObservableButton menuButton;
	private ObservableButton quitButton;

	private Font minecraftia;
	
	public MenuInGame() {
		this.minecraftia = TFont.loadFont("data/font/Minecraftia.ttf");
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel l1 = new JPanel();
        l1.setOpaque(false);
        l1.setLayout(new BoxLayout(l1, BoxLayout.LINE_AXIS));
        JLabel pauseLabel = new JLabel("Pause", JLabel.CENTER);
        pauseLabel.setForeground(Color.white);
        pauseLabel.setFont(minecraftia.deriveFont(Font.PLAIN, 32));
        l1.add(pauseLabel, BorderLayout.CENTER);
        add(l1, BorderLayout.CENTER);
		
        menuButton = new ObservableButton("Menu");
        add(menuButton.getButton(),BorderLayout.CENTER);
        
        quitButton = new ObservableButton("Quitter");
        add(quitButton.getButton(),BorderLayout.CENTER);
        
        setOpaque(false);
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       

    }
	
	@Override
	public Dimension getPreferredSize() {
        return new Dimension(this.getWidth(), this.getHeight());
    }

	public ObservableButton getMenuButton() {
		return menuButton;
	}

	public ObservableButton getQuitButton() {
		return quitButton;
	}


}
