package menus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Options extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private BufferedImage backgroundImage;
	private ObservableButton trioButton;
	private ObservableButton tetroButton;
	private ObservableButton pentoButton;
	private ObservableButton hexaButton;
	private ObservableButton returnButton;
	private short polyominoType;
	private Font minecraftia;

	public Options() {
		this.minecraftia = TFont.loadFont("data/font/Minecraftia.ttf");
		
		polyominoType = 4;
		
		try {
  	      backgroundImage = ImageIO.read(new File("data/img/bg_accueil.jpg"));
  	    } catch(IOException e) {
  	      e.printStackTrace();
  	    }
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        JPanel borderTop = new JPanel();
        borderTop.setOpaque(false);
        borderTop.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 6));
        add(borderTop, BorderLayout.NORTH);
        
        
        JPanel l1 = new JPanel();
        l1.setOpaque(false);
        l1.setLayout(new BoxLayout(l1, BoxLayout.LINE_AXIS));
        JLabel polyominosLabel = new JLabel("Polyominos", JLabel.CENTER);
        polyominosLabel.setForeground(Color.white);
        polyominosLabel.setFont(minecraftia.deriveFont(Font.PLAIN, 16));
        l1.add(polyominosLabel, BorderLayout.CENTER);
        add(l1, BorderLayout.CENTER);
        
        JPanel polyominos = new JPanel();
        polyominos.setOpaque(false);
        polyominos.setLayout(new BoxLayout(polyominos, BoxLayout.LINE_AXIS));
        add(polyominos, BorderLayout.CENTER);
        polyominos.setBorder(new EmptyBorder(10, 10, 40, 10));
        
        trioButton = new ObservableButton("Triominos");
        polyominos.add(trioButton.getButton());
        
        tetroButton = new ObservableButton("Tetrominos");
        tetroButton.getButton().setEnabled(false);
        polyominos.add(tetroButton.getButton());
        
        pentoButton = new ObservableButton("Pentominos");
        polyominos.add(pentoButton.getButton());
        
        hexaButton = new ObservableButton("Hexaminos");
        polyominos.add(hexaButton.getButton());
        
        
        JPanel retour = new JPanel();
        retour.setOpaque(false);
        retour.setLayout(new BoxLayout(retour, BoxLayout.LINE_AXIS));
        add(retour, BorderLayout.CENTER);
        returnButton = new ObservableButton("Retour");
        retour.add(returnButton.getButton(), BorderLayout.CENTER);
        
        
        JPanel borderBottom = new JPanel();
        borderBottom.setOpaque(false);
        borderBottom.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 6));
        add(borderBottom, BorderLayout.SOUTH);
		
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
        g.setColor(Color.white);
        g.setFont(minecraftia.deriveFont(Font.PLAIN, 32));
        g.drawString("Paramètres", this.getWidth() / 2 - this.getWidth() / 8, this.getHeight() / 4);
    }
	
	@Override
	public Dimension getPreferredSize() {
        return new Dimension(this.getWidth(), this.getHeight());
    }

	public ObservableButton getTrioButton() {
		return trioButton;
	}

	public ObservableButton getTetroButton() {
		return tetroButton;
	}

	public ObservableButton getPentoButton() {
		return pentoButton;
	}

	public ObservableButton getHexaButton() {
		return hexaButton;
	}

	public short getPolyominoType() {
		return polyominoType;
	}

	public void setPolyominoType(short polyominoType) {
		this.polyominoType = polyominoType;
	}

	public ObservableButton getReturnButton() {
		return returnButton;
	}

}
