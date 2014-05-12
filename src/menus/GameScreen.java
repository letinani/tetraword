package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.LinkedList;

import polyominos.Polyomino;
import polyominos.PolyominoList;

public class GameScreen {

	private PolyominoList polyominos;
	private int linesDestroyed;
	
	private int anagramOn;
	private int score;
	private int player;
	private int brickSize;
	private int width;
	private HashSet<Integer> bricksClicked;
	private Font minecraftia;
	
	public GameScreen(int player) {
		this.minecraftia = TFont.loadFont("data/font/Minecraftia.ttf");
		
		this.polyominos = new PolyominoList();
		
		linesDestroyed = 0;
		
		anagramOn = -10;
		
		score = 0;
		
		this.setPlayer(player);
		
		brickSize = 0;
		
		width = 0;
	}
	
	public void draw(Graphics g, int width, int height, int indice, boolean gameOver) {
		
        this.setWidth(width);
		if(width * 2 > height) width = height * 10 / 16;
		brickSize = width / 15;
		
		// Titre
		g.setColor(Color.white);
		g.setFont(minecraftia.deriveFont(Font.PLAIN, brickSize));
        g.drawString("Joueur " + Integer.toString(player + 1), brickSize * 4, brickSize * 3 / 2);
        
        
        //On choisit une couleur de fond pour le rectangle
        g.setColor(Color.black);
        //On le dessine de sorte qu'il recouvre la surface du jeu
        g.translate(brickSize * 2, brickSize * 2);
        g.fillRect(0, 0, brickSize * 10, brickSize * 20);
        
        // Score.
        g.setColor(Color.white);
        g.setFont(minecraftia.deriveFont(Font.PLAIN, brickSize));
		g.drawString(Integer.toString(score), 0, 21 * brickSize + brickSize / 2);
		
        
        if(anagramOn > 0) {
	        g.setColor(Color.green);
	        g.fillRect(-brickSize * 2, anagramOn * brickSize, brickSize, brickSize);
	        
	        g.setColor(Color.red);
	        g.fillRect(-brickSize, anagramOn * brickSize, brickSize, brickSize);
        }
        
        for(int i = 0; i < indice + 1; ++i) {
        	polyominos.get(i).draw(g, brickSize, anagramOn, bricksClicked, gameOver);
        }
        
        // Polyomino suivant
        polyominos.get(indice + 1).drawNext(g, brickSize);
        
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

}
