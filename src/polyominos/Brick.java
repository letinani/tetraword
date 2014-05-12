package polyominos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

public class Brick {

	private Point coords;
	private Point position;
	private Letter letter;
	
	public Brick(Point coords, Letter letter, Point position) {
		this.coords = new Point(coords.x, coords.y);
		this.letter = new Letter(letter.getValue(), letter.getFrequence());
		this.position = new Point(position.x, position.y);
	}
	
	public void draw(Graphics g, Color color, int brickSize, int anagramOn, boolean clicked) {
		if(this.getAbsoluteCoords().y >= 0) {
			if(anagramOn == getAbsoluteCoords().y && !clicked) g.setColor(Color.white);
			else if(anagramOn == getAbsoluteCoords().y && clicked) g.setColor(Color.black);
			else g.setColor(color);
			g.fillRect((int) coords.getX() * brickSize, (int) coords.getY() * brickSize, brickSize, brickSize);
			
			if(anagramOn == getAbsoluteCoords().y && !clicked) g.setColor(Color.red);
			else g.setColor(Color.white);
			g.drawRect((int) coords.getX() * brickSize, (int) coords.getY() * brickSize, brickSize, brickSize);
			
			if(anagramOn == getAbsoluteCoords().y && !clicked) g.setColor(Color.red);
			else g.setColor(Color.white);
			g.setFont(new Font("TimesRoman", Font.PLAIN, brickSize*2/3));
			g.drawString(letter.getValue(), (int) coords.getX() * brickSize + brickSize/3, (int) coords.getY() * brickSize + brickSize*2/3);
		}
	}
	
	public void drawNext(Graphics g, int brickSize, int nbBricks, Color color) {
		g.setColor(color);
		g.fillRect((int) coords.getX() * brickSize*2/nbBricks, (int) coords.getY() * brickSize*2/nbBricks, brickSize*2/nbBricks, brickSize*2/nbBricks);
		g.setColor(Color.white);
		g.drawRect((int) coords.getX() * brickSize*2/nbBricks, (int) coords.getY() * brickSize*2/nbBricks, brickSize*2/nbBricks, brickSize*2/nbBricks);
		g.setFont(new Font("TimesRoman", Font.PLAIN, brickSize/3));
		g.drawString(letter.getValue(), (int) coords.getX() * brickSize/2 + brickSize/6, (int) coords.getY() * brickSize/2 + brickSize/3);
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
