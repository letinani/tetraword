package menus;

import menus.ObservableButton;


import javax.swing.JPanel;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class MenuInGame extends JPanel {

	private static final long serialVersionUID = 1L;
	private ObservableButton saveButton;
	private ObservableButton quitButton;

	private Font minecraftia;
	
	public MenuInGame() {
		this.minecraftia = TFont.loadFont("data/font/Minecraftia.ttf");
		
        saveButton = new ObservableButton("Sauvegarder");
        add(saveButton.getButton(),BorderLayout.CENTER);
        
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

	public ObservableButton getSaveButton() {
		return saveButton;
	}

	public ObservableButton getQuitButton() {
		return quitButton;
	}


}
