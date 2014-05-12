package polyominos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Polyomino {

	private LinkedList<Brick> bricks;
	private int color;
	private Point position;
	private Dimension dimension;
	
	public Polyomino(PolyominoPattern pattern, Letter[] letters) {
		
		this.setColor(pattern.getColor());
		bricks = new LinkedList<Brick>();
		dimension = new Dimension(30,30);
		
		for(int i = 0; i < pattern.getCoords().length; ++i) {
			if(pattern.getCoords()[i].getX() * 30 + 30 > dimension.getWidth()) 
				dimension.setSize(pattern.getCoords()[i].getX() * 30 + 30, dimension.getHeight());
			if(pattern.getCoords()[i].getY() * 30 + 30 > dimension.getHeight()) 
				dimension.setSize(dimension.getWidth(), pattern.getCoords()[i].getY() * 30 + 30);
			
			bricks.add(new Brick(pattern.getCoords()[i], letters[i], new Point(5, 0)));
		}
		
		setPosition(new Point(5, (int) (-dimension.getHeight()/30)));
	}
	
	public boolean keepInBox(String direction, int nbCase) {
		Iterator<Brick> itr = this.getBricks().iterator();
		while(itr.hasNext()) {	
			Brick b = (Brick) itr.next();
			if(direction == "down" && b.getAbsoluteCoords().y + nbCase >= 20) {
				return false;
			}
			
			if(direction == "left" && b.getAbsoluteCoords().x + nbCase <= -1) {
				return false;
			} else if (direction == "right" && b.getAbsoluteCoords().x + nbCase >= 10) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isOutOfLimits() {
		Iterator<Brick> itr = this.getBricks().iterator();
		while(itr.hasNext()) {	
			Brick b = (Brick) itr.next();
			if(b.getAbsoluteCoords().y < 0) {
				return true;
			}
		}
		
		return false;
	}
	
	public void draw(Graphics g, int brickSize, int anagramOn, HashSet<Integer> bricksClicked, boolean gameOver) {
        
        g.translate(position.x * brickSize, position.y * brickSize);
        
        
        for(int i = 0; i < this.getNumberOfBricks(); ++i) {
        	boolean clicked = false;
        	if(bricksClicked != null) {
	        	if(bricksClicked.contains(this.getBrick(i).getAbsoluteCoords().x)) {
        			clicked = true;
        		}
        	}
        	if(gameOver) this.getBrick(i).draw(g, Color.gray, brickSize, anagramOn, clicked);
        	else this.getBrick(i).draw(g, new Color(this.getColor()), brickSize, anagramOn, clicked);
        }		
        
        g.translate(-position.x * brickSize, -position.y * brickSize);
    }
	
	public void drawNext(Graphics g, int brickSize) {
        
        g.translate(11 * brickSize - brickSize/2, brickSize);
        g.setColor(Color.black);
        g.fillRect(0, 0, brickSize*2 + brickSize/2, brickSize*2 + brickSize/2);
        g.translate(brickSize/4, brickSize/4);
        for(int i = 0; i < this.getNumberOfBricks(); ++i) {
        	this.getBrick(i).drawNext(g, brickSize, bricks.size(), new Color(this.getColor()));
        }
        g.translate(-brickSize/4, -brickSize/4);
        g.translate(-11 * brickSize + brickSize/2, -brickSize);
    }
	
	public void removeBrick(int i) {
		this.bricks.remove(i);
	}
	
	public void removeBrick(Brick b) {
		this.bricks.remove(b);
	}

	public Brick getBrick(int i) {
		return bricks.get(i);
	}
	
	public LinkedList<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(LinkedList<Brick> bricks) {
		this.bricks = bricks;
	}
	
	public int getNumberOfBricks() {
		return this.bricks.size();
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = new Point(position.x,position.y);
		Iterator<Brick> itr = bricks.iterator();
		while(itr.hasNext()) {
			Brick b = (Brick) itr.next();
			b.setPosition(this.position);
		}
	}
	
	public void setPosition(int x, int y) {
		this.position.x = x;
		this.position.y = y;
		
		Iterator<Brick> itr = bricks.iterator();
		while(itr.hasNext()) {
			Brick b = (Brick) itr.next();
			b.setPosition(this.position);
		}
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	

}
