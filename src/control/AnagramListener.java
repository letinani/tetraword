package control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import polyominos.Brick;

public class AnagramListener implements MouseListener {
	
	private LinkedList<Brick> bricks;
	private HashSet<Integer> bricksClicked;
	private StringBuilder word;
	private int score;
	private boolean validate;
	private int brickSize;
	private int border;
	
	public AnagramListener(LinkedList<Brick> bricks, int brickSize, int border) {
		this.bricks = bricks;
		bricksClicked = new HashSet<Integer>();
		word = new StringBuilder();
		score = 0;
		
		validate = false;
		
		this.brickSize = brickSize;
		this.border = border;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		Iterator<Brick> itr = bricks.iterator();
		while(itr.hasNext()) {
			Brick b = (Brick) itr.next();
			
			if(e.getX() > border + brickSize * 2 + b.getAbsoluteCoords().x * brickSize && e.getX() < border + brickSize * 2 + b.getAbsoluteCoords().x * brickSize + brickSize && e.getY() > brickSize * 2 + b.getAbsoluteCoords().y * brickSize && e.getY() < brickSize * 2 + b.getAbsoluteCoords().y * brickSize + brickSize) {
				if(!bricksClicked.contains(b.getAbsoluteCoords().x)) {
					word.append(b.getLetter().getValue());
					score += 15 - (int) b.getLetter().getFrequence();
					
					bricksClicked.add(b.getAbsoluteCoords().x);
				} else {
					bricksClicked.remove(b.getAbsoluteCoords().x);
				}
			}
		}
		
		int coordY = bricks.get(0).getAbsoluteCoords().y;
		
		if(e.getX() > border + brickSize && e.getX() < border + brickSize * 2 && e.getY() > brickSize * 2 + coordY * brickSize && e.getY() < brickSize * 2 + coordY * brickSize + brickSize) {
			word.delete(0, word.length());
			score = 0;
			for(int i = bricksClicked.size() - 1; i >= 0; --i) {
				bricksClicked.remove(bricksClicked.toArray()[i]);
			}
		} else if(e.getX() > border && e.getX() < border + brickSize && e.getY() > brickSize * 2 + coordY * brickSize && e.getY() < brickSize * 2 + coordY * brickSize + brickSize) {
			setValidate(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	public LinkedList<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(LinkedList<Brick> bricks) {
		this.bricks = bricks;
	}
	
	public String getWord() {
		return word.toString();
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getBrickSize() {
		return brickSize;
	}

	public void setBrickSize(int brickSize) {
		this.brickSize = brickSize;
	}
	
	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}
	
	public HashSet<Integer> getBricksClicked() {
		return bricksClicked;
	}

}
