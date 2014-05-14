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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuInGame extends JPanel {

	private static final long serialVersionUID = 1L;
	private ObservableButton menuButton;
	private ObservableButton quitButton;
	private BufferedImage backgroundImage;

	private Font minecraftia;
	
	public MenuInGame() {
		this.minecraftia = TFont.loadFont("data/font/Minecraftia.ttf");
		try {
	  	      backgroundImage = ImageIO.read(new File("data/img/bg_game.jpg"));
	  	    } catch(IOException e) {
	  	      e.printStackTrace();
	  	    }
			
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
        
		JPanel l1 = new JPanel();
        l1.setOpaque(false);
        l1.setLayout(new BoxLayout(l1, BoxLayout.LINE_AXIS));
        JLabel pauseLabel = new JLabel("Pause", JLabel.CENTER);
        pauseLabel.setBorder(new EmptyBorder(100, 10, 10, 10));
        pauseLabel.setForeground(Color.white);
        pauseLabel.setFont(minecraftia.deriveFont(Font.PLAIN, 32));
        l1.add(pauseLabel, BorderLayout.CENTER);
        add(l1, BorderLayout.CENTER);
    	
		JPanel l2 = new JPanel();
        l2.setBorder(new EmptyBorder(20, 10, 10, 10));
        l2.setOpaque(false);
        l2.setLayout(new BoxLayout(l2, BoxLayout.LINE_AXIS));
        
        menuButton = new ObservableButton("Menu");
        quitButton = new ObservableButton("Quitter");
        l2.add(menuButton.getButton(), BorderLayout.CENTER);
        l2.add(quitButton.getButton(), BorderLayout.CENTER);
        add(l2);

        setOpaque(false);
	}
	
	public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
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
