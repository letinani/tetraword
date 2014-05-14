package control;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import polyominos.Brick;
import polyominos.Polyomino;

public class WordleListener implements MouseListener {
	
	private LinkedList<Polyomino> polyominos;
	private LinkedHashSet<Point> bricksClicked;
	private StringBuilder word;
	private int score;
	private boolean validate;
	private int brickSize;
	private int border;
	private Polyomino current;
	private Polyomino next;
	
	public WordleListener(LinkedList<Polyomino> polyominos, Polyomino current, Polyomino next, int brickSize, int border) {
		this.polyominos = polyominos;
		bricksClicked = new LinkedHashSet<Point>();
		word = new StringBuilder();
		score = 0;
		
		validate = false;
		
		this.brickSize = brickSize;
		this.border = border;
		
		this.current = current;
		this.next = next;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		Iterator<Polyomino> itr = polyominos.iterator();
		while(itr.hasNext()) {
			Polyomino p = (Polyomino) itr.next();
			if(!p.equals(current) && !p.equals(next)) {
				Iterator<Brick> itr2 = p.getBricks().iterator();
				while(itr2.hasNext()) {
					Brick b = (Brick) itr2.next();
					
					if(e.getX() > border + brickSize * 2 + b.getAbsoluteCoords().x * brickSize && e.getX() < border + brickSize * 2 + b.getAbsoluteCoords().x * brickSize + brickSize) {
						if(e.getY() > border + brickSize * 2 + b.getAbsoluteCoords().y * brickSize && e.getY() < border + brickSize * 2 + b.getAbsoluteCoords().y * brickSize + brickSize) {
							
							if(!bricksClicked.contains(b.getAbsoluteCoords())) {
								if(bricksClicked.size() == 0) {
									word.append(b.getLetter().getValue());
									score += 15 - (int) b.getLetter().getFrequence();
									
									bricksClicked.add(b.getAbsoluteCoords());
								}
								else {
									Object[] points = bricksClicked.toArray();
									Point lastPoint = (Point) points[bricksClicked.size() - 1];
									if(lastPoint.x <= b.getAbsoluteCoords().x + 1 && lastPoint.x >= b.getAbsoluteCoords().x - 1) {
										if(lastPoint.y <= b.getAbsoluteCoords().y + 1 && lastPoint.y >= b.getAbsoluteCoords().y - 1) {
											word.append(b.getLetter().getValue());
											score += 15 - (int) b.getLetter().getFrequence();
											
											bricksClicked.add(new Point(b.getAbsoluteCoords().x, b.getAbsoluteCoords().y));
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		if(e.getX() > border + brickSize && e.getX() < border + brickSize * 2 && e.getY() > brickSize * 2 + 19 * brickSize && e.getY() < brickSize * 2 + 19 * brickSize + brickSize) {
			reset();
		} else if(e.getX() > border && e.getX() < border + brickSize && e.getY() > brickSize * 2 + 19 * brickSize && e.getY() < brickSize * 2 + 19 * brickSize + brickSize) {
			setValidate(true);
		}
	}
	
	public void deleteBricks() {
		// On supprime les briques.
		for(int i = polyominos.size() - 1; i >= 0; --i) {
			Polyomino p = polyominos.get(i);
			if(!p.equals(current) && !p.equals(next)) {
				for(int j = p.getBricks().size() - 1; j >= 0; --j) {
					Brick b = p.getBrick(j);
					
					if(bricksClicked.contains(b.getAbsoluteCoords())) {
						p.removeBrick(b);
					}
				}
			}
		}
				
		// On fait tomber les briques.
		for(int i = 0; i < polyominos.size() - 1; ++i) {
			Polyomino p = polyominos.get(i);
			if(!p.equals(current) && !p.equals(next)) {
				for(int j = 0; j < p.getBricks().size(); ++j) {
					Brick b = p.getBrick(j);
					
					if(!bricksClicked.contains(b.getAbsoluteCoords())) {
						int nbCase = 0;
						Object[] points = bricksClicked.toArray();
						for(int k = 0; k < bricksClicked.size(); ++k) {
							Point point = (Point) points[k];
							if(point.x == b.getAbsoluteCoords().x && point.y > b.getAbsoluteCoords().y) {
								++nbCase;
							}
						}
						
						if(nbCase > 0) b.setCoords(new Point(b.getCoords().x, b.getCoords().y + nbCase));
					}
				}
			}
		}
		
		reset();
	}
	
	public void reset() {
		word.delete(0, word.length());
		score = 0;
		setValidate(false);
		for(int i = bricksClicked.size() - 1; i >= 0; --i) {
			bricksClicked.remove(bricksClicked.toArray()[i]);
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

	public LinkedList<Polyomino> getPolyominos() {
		return polyominos;
	}

	public void setPolyominos(LinkedList<Polyomino> polyominos) {
		this.polyominos = polyominos;
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
	
	public LinkedHashSet<Point> getBricksClicked() {
		return bricksClicked;
	}

	public Polyomino getCurrent() {
		return current;
	}

	public void setCurrent(Polyomino current) {
		this.current = current;
	}

	public Polyomino getNext() {
		return next;
	}

	public void setNext(Polyomino next) {
		this.next = next;
	}

}
