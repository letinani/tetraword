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

public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage backgroundImage;
	private ObservableButton soloButton;
	private ObservableButton ordiButton;
	private ObservableButton twoButton;
	private ObservableButton threeButton;
	private ObservableButton fourButton;
	private ObservableButton paramButton;
	private Font minecraftia;
	
	public Menu() {
		this.minecraftia = TFont.loadFont("data/font/Minecraftia.ttf");
		
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
        JLabel soloLabel = new JLabel("Partie Solo", JLabel.CENTER);
        soloLabel.setForeground(Color.white);
        soloLabel.setFont(minecraftia.deriveFont(Font.PLAIN, 16));
        l1.add(soloLabel, BorderLayout.CENTER);
        add(l1, BorderLayout.CENTER);
        
        JPanel solo = new JPanel();
        solo.setOpaque(false);
        solo.setLayout(new BoxLayout(solo, BoxLayout.LINE_AXIS));
        add(solo, BorderLayout.CENTER);
        solo.setBorder(new EmptyBorder(10, 10, 20, 10));
        
        soloButton = new ObservableButton("Solo");
        solo.add(soloButton.getButton());
        
        ordiButton = new ObservableButton("VS Bot");
        solo.add(ordiButton.getButton());
        
        
        JPanel l2 = new JPanel();
        l2.setOpaque(false);
        l2.setLayout(new BoxLayout(l2, BoxLayout.LINE_AXIS));
        JLabel multiLabel = new JLabel("Multijoueur", JLabel.CENTER);
        multiLabel.setForeground(Color.white);
        multiLabel.setFont(minecraftia.deriveFont(Font.PLAIN, 16));
        l2.add(multiLabel, BorderLayout.CENTER);
        add(l2, BorderLayout.CENTER);
        
        JPanel multi = new JPanel();
        multi.setOpaque(false);
        multi.setLayout(new BoxLayout(multi, BoxLayout.LINE_AXIS));
        multi.setBorder(new EmptyBorder(10, 10, 20, 10));
        add(multi, BorderLayout.CENTER);
        
        twoButton = new ObservableButton("2 Joueurs");
        multi.add(twoButton.getButton());
        
        threeButton = new ObservableButton("3 Joueurs");
        multi.add(threeButton.getButton());
        
        fourButton = new ObservableButton("4 Joueurs");
        multi.add(fourButton.getButton());
        
        
        JPanel l3 = new JPanel();
        l3.setOpaque(false);
        l3.setLayout(new BoxLayout(l3, BoxLayout.LINE_AXIS));
        JLabel optionsLabel = new JLabel("Options", JLabel.CENTER);
        optionsLabel.setForeground(Color.white);
        optionsLabel.setFont(minecraftia.deriveFont(Font.PLAIN, 16));
        l3.add(optionsLabel, BorderLayout.CENTER);
        add(l3, BorderLayout.CENTER);
        
        JPanel options = new JPanel();
        options.setOpaque(false);
        options.setLayout(new BoxLayout(options, BoxLayout.LINE_AXIS));
        options.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(options, BorderLayout.CENTER);
        
        paramButton = new ObservableButton("Paramètres");
        options.add(paramButton.getButton());
        
        
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
        g.drawString("Tetraword", this.getWidth() / 2 - this.getWidth() / 8, this.getHeight() / 4);
    }
	
	@Override
	public Dimension getPreferredSize() {
        return new Dimension(this.getWidth(), this.getHeight());
    }
	
	public ObservableButton getSoloButton() {
		return soloButton;
	}
	
	public ObservableButton getOrdiButton() {
		return ordiButton;
	}

	public ObservableButton getTwoButton() {
		return twoButton;
	}

	public ObservableButton getThreeButton() {
		return threeButton;
	}

	public ObservableButton getFourButton() {
		return fourButton;
	}
	
	public ObservableButton getParamButton() {
		return paramButton;
	}

}
