package polyominos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import menus.TFont;

public class Brick implements Serializable {

	private static final long serialVersionUID = 1L;
	private Point coords;
	private Point position;
	private Letter letter;
	private Font minecraftia;
	
	public Brick(Point coords, Letter letter, Point position) {
		this.minecraftia = TFont.loadFont("data/font/Minecraftia.ttf");
		this.coords = new Point(coords.x, coords.y);
		this.letter = new Letter(letter.getValue(), letter.getFrequence());
		this.position = new Point(position.x, position.y);
	}
	
	public void draw(Graphics g, Color color, int brickSize, int anagramOn, boolean clicked, boolean wordleClicked) {
		if(this.getAbsoluteCoords().y >= 0) {
			if(anagramOn == getAbsoluteCoords().y && !clicked) g.setColor(Color.white);
			else if((anagramOn == getAbsoluteCoords().y && clicked) || wordleClicked) g.setColor(Color.black);
			else g.setColor(color);
			g.fillRect((int) coords.getX() * brickSize, (int) coords.getY() * brickSize, brickSize, brickSize);
			
			if(anagramOn != getAbsoluteCoords().y && !wordleClicked) {
				
				g.setColor(surexpose(color, 192));
				g.fillRect((int) coords.getX() * brickSize + brickSize - brickSize / 8, (int) coords.getY() * brickSize + brickSize - brickSize / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize + brickSize - brickSize * 2 / 8, (int) coords.getY() * brickSize + brickSize - brickSize / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize + brickSize - brickSize / 8, (int) coords.getY() * brickSize + brickSize - brickSize * 2 / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				
				
				g.setColor(surexpose(color, 128));
				g.fillRect((int) coords.getX() * brickSize, (int) coords.getY() * brickSize, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize + brickSize / 8, (int) coords.getY() * brickSize, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize, (int) coords.getY() * brickSize + brickSize / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				
				g.fillRect((int) coords.getX() * brickSize + brickSize - brickSize * 3 / 8, (int) coords.getY() * brickSize + brickSize - brickSize / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize + brickSize - brickSize / 8, (int) coords.getY() * brickSize + brickSize - brickSize * 3 / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize + brickSize - brickSize * 2 / 8, (int) coords.getY() * brickSize + brickSize - brickSize * 2 / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				
				
				g.setColor(surexpose(color, 64));
				g.fillRect((int) coords.getX() * brickSize + brickSize * 2 / 8, (int) coords.getY() * brickSize, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize, (int) coords.getY() * brickSize + brickSize * 2 / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize + brickSize / 8, (int) coords.getY() * brickSize + brickSize / 8 + 1, brickSize / 8 + 1, brickSize / 8 + 1);
				
				
				g.fillRect((int) coords.getX() * brickSize + brickSize - brickSize / 8, (int) coords.getY() * brickSize + brickSize - brickSize * 4 / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize + brickSize - brickSize * 2 / 8, (int) coords.getY() * brickSize + brickSize - brickSize * 3 / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize + brickSize - brickSize * 3 / 8, (int) coords.getY() * brickSize + brickSize - brickSize * 2 / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				g.fillRect((int) coords.getX() * brickSize + brickSize - brickSize * 4 / 8, (int) coords.getY() * brickSize + brickSize - brickSize / 8, brickSize / 8 + 1, brickSize / 8 + 1);
				
			}
			
			if((anagramOn == getAbsoluteCoords().y && !clicked) || wordleClicked) g.setColor(Color.red);
			else g.setColor(Color.white);
			g.drawRect((int) coords.getX() * brickSize, (int) coords.getY() * brickSize, brickSize, brickSize);
			
			if((anagramOn == getAbsoluteCoords().y && !clicked) || wordleClicked) g.setColor(Color.red);
			else g.setColor(Color.white);
			g.setFont(minecraftia.deriveFont(Font.PLAIN, brickSize * 2 / 3));
			g.drawString(letter.getValue(), (int) coords.getX() * brickSize + brickSize/3, (int) coords.getY() * brickSize + brickSize * 3/4);
			
		}
	}
	
	public void drawNext(Graphics g, int brickSize, int nbBricks, Color color) {
		g.setColor(color);
		g.fillRect((int) coords.getX() * brickSize*2/nbBricks + brickSize/nbBricks, (int) coords.getY() * brickSize*2/nbBricks + brickSize/nbBricks, brickSize*2/nbBricks, brickSize*2/nbBricks);
		g.setColor(Color.white);
		g.drawRect((int) coords.getX() * brickSize*2/nbBricks + brickSize/nbBricks, (int) coords.getY() * brickSize*2/nbBricks + brickSize/nbBricks, brickSize*2/nbBricks, brickSize*2/nbBricks);
		g.setFont(minecraftia.deriveFont(Font.PLAIN, brickSize / 3));
		g.drawString(letter.getValue(), (int) coords.getX() * brickSize*2/nbBricks + brickSize*3/2/nbBricks, (int) coords.getY() * brickSize*2/nbBricks + brickSize*5/2/nbBricks);
	}
	
	public Color surexpose(Color color, int level) {
		int red = color.getRed() + level;
		if(red > 255) red = 255;
		int green = color.getGreen() + level;
		if(green > 255) green = 255;
		int blue = color.getBlue() + level;
		if(blue > 255) blue = 255;
		
		return new Color(red, green, blue);
	}
	
	public Point getCoords() {
		return coords;
	}
	
	public void setCoords(Point coords) {
		this.coords = coords;
	}

	public Letter getLetter() {
		return letter;
	}

	public void setLetter(Letter letter) {
		this.letter = letter;
	}

	public Point getAbsoluteCoords() {
		return new Point(position.x + coords.x, position.y + coords.y);
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
}
