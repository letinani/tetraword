package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import modificators.Modificator;
import modificators.Reserve;
import polyominos.Polyomino;
import polyominos.PolyominoList;

public class GameScreen implements Serializable {

	private static final long serialVersionUID = 1L;
	private PolyominoList polyominos;
	private int linesDestroyed;
	

	private static final double PI = 3.1415926536;
	
	private int anagramOn;
	private int score;
	private int player;
	private int brickSize;
	private int width;
	private HashSet<Integer> bricksClicked;
	private LinkedHashSet<Point> bricksWordle;
	private Font minecraftia;
	transient private BufferedImage confirm;
	transient private BufferedImage cancel;
	
	public GameScreen(int player) {
		this.minecraftia = TFont.loadFont("data/font/Minecraftia.ttf");
		
		try {
			confirm = ImageIO.read(new File("data/img/confirm.jpg"));
			cancel = ImageIO.read(new File("data/img/cancel.jpg"));
  	    } catch(IOException e) {
  	    	e.printStackTrace();
  	    }
		
		this.polyominos = new PolyominoList();
		
		linesDestroyed = 0;
		
		anagramOn = -10;
		
		score = 0;
		
		this.setPlayer(player);
		
		brickSize = 0;
		
		width = 0;
	}
	
	public void draw(Graphics g, int width, int height, int indice, boolean gameOver, Gameboard gb) {
		
        this.setWidth(width);
		if(width * 2 > height) width = height * 10 / 16;
		brickSize = width / 15;
		
		// Titre
		g.setColor(Color.white);
		g.setFont(minecraftia.deriveFont(Font.PLAIN, brickSize));
        g.drawString("Joueur " + Integer.toString(player + 1), brickSize * 4, brickSize * 3 / 2);
        
        
        g.translate(brickSize * 2, brickSize * 2);
        
        // Score.
        g.setColor(Color.white);
        g.setFont(minecraftia.deriveFont(Font.PLAIN, brickSize));
		g.drawString(Integer.toString(score), 0, 21 * brickSize + brickSize / 2);
		
		
		
		int xRand = gb.randomNumber(-brickSize, brickSize);
    	int yRand = gb.randomNumber(-brickSize, brickSize);
    	
    	HashMap<Integer, Integer> modifs = gb.modificationMap();
    	
    	if(!modifs.containsKey(player) && modifs.containsValue(0)) {
    		g.translate(xRand, yRand);
    	} 
    	if(!modifs.containsKey(player) && modifs.containsValue(2)) {
    		Graphics2D g2d = (Graphics2D) g;
    		g2d.rotate(PI);
    		g.translate(-brickSize * 10, -brickSize * 20);
    	}
    	
    	
    	//On choisit une couleur de fond pour le rectangle
        g.setColor(Color.black);
        //On le dessine de sorte qu'il recouvre la surface du jeu
        g.fillRect(0, 0, brickSize * 10, brickSize * 20);
		
        
        if(anagramOn > 0) {
	        g.drawImage(confirm, -brickSize * 2, anagramOn * brickSize, brickSize, brickSize, gb);
	        g.drawImage(cancel, -brickSize, anagramOn * brickSize, brickSize, brickSize, gb);
	        
        } else if(bricksWordle != null) {
	        g.drawImage(confirm, -brickSize * 2, 19 * brickSize, brickSize, brickSize, gb);
	        g.drawImage(cancel, -brickSize, 19 * brickSize, brickSize, brickSize, gb);
        	
        }
        
        // Modificateur
        Modificator modificator = gb.getPlayer(player).getModificator();
        if(modificator.isVisible() && gb.getPlayers().length > 1) {
        	g.drawImage(modificator.getIcon(), brickSize * modificator.getPosition().x, brickSize * modificator.getPosition().y, brickSize, brickSize, gb);
        }
        
        for(int i = 0; i < indice + 1; ++i) {
        	polyominos.get(i).draw(g, brickSize, anagramOn, bricksClicked, bricksWordle, gameOver);
        }
        
        if(!modifs.containsKey(player) && modifs.containsValue(2)) {
    		Graphics2D g2d = (Graphics2D) g;
    		g.translate(brickSize * 10, brickSize * 20);
    		g2d.rotate(-PI);
    	}
        if(!modifs.containsKey(player) && modifs.containsValue(0)) {
    		g.translate(-xRand, -yRand);
    	} 
    	
    	
        
        // Polyomino suivant
        polyominos.get(indice + 1).drawNext(g, brickSize);
        
        // Réserve
        if(gb.getPlayers().length > 1) {
	        g.setColor(Color.black);
	        g.fillRect(brickSize * 10 + brickSize / 2, brickSize * 4, brickSize * 2  + brickSize / 2, brickSize * 2 + brickSize / 2);
	        Reserve reserve = gb.getPlayer(player).getReserve();
	        if(!reserve.isEmpty() && reserve.getModificator().isVisible()) {
		        g.drawImage(reserve.getModificator().getIcon(), brickSize * 11, brickSize * 4 + brickSize / 2, brickSize * 2 - brickSize / 2, brickSize * 2 - brickSize / 2, gb);
	        }
		}
        
        int level = (linesDestroyed / 10) + 1;
        if(level > 10) level = 10;
        
        g.setColor(Color.white);        
        g.setFont(minecraftia.deriveFont(Font.PLAIN, brickSize * 2 / 3));
        g.drawString("Nv.", brickSize * 11, brickSize * 9);
        g.setFont(minecraftia.deriveFont(Font.PLAIN, brickSize));
        g.drawString(Integer.toString(level), brickSize * 11, brickSize * 10);
        
        g.setFont(minecraftia.deriveFont(Font.PLAIN, brickSize * 2 / 3));
        g.drawString("Nb.", brickSize * 11, brickSize * 12);
        g.setFont(minecraftia.deriveFont(Font.PLAIN, brickSize));
        g.drawString(Integer.toString(linesDestroyed % 10), brickSize * 11, brickSize * 13);
        
        g.translate(-brickSize * 2, -brickSize * 2);
    }
	
	public void addPolyomino(Polyomino p) {
		polyominos.add(p);
	}
	
	public void removePolyomino(Polyomino p) {
		polyominos.remove(p);
	}
	
	public Polyomino getPolyomino(int i) {
		return polyominos.get(i);
	}
	
	public LinkedList<Polyomino> getPolyominos() {
		return polyominos.getPolyominoList();
	}
	
	public PolyominoList getPolyominoList() {
		return polyominos;
	}
	
	public int getLinesDestroyed() {
		return linesDestroyed;
	}

	public void setLinesDestroyed(int linesDestroyed) {
		this.linesDestroyed = linesDestroyed;
	}

	public int getAnagramOn() {
		return anagramOn;
	}

	public void setAnagramOn(int anagramOn) {
		this.anagramOn = anagramOn;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}
	
	public int getBrickSize() {
		return brickSize;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setBricksClicked(HashSet<Integer> bricksClicked) {
		this.bricksClicked = bricksClicked;
	}

	public HashSet<Point> getBricksWordle() {
		return bricksWordle;
	}

	public void setBricksWordle(LinkedHashSet<Point> bricksWordle) {
		this.bricksWordle = bricksWordle;
	}

}
