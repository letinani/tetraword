package control;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import polyominos.Brick;
import polyominos.Letter;
import polyominos.Polyomino;
import polyominos.PolyominoPattern;
import menus.GameScreen;
import menus.Gameboard;
import modificators.Modificator;
import modificators.Reserve;

public class Player extends Thread {
	
	private static final int framePerSecond = 30;
	private double speed;
	private double time;
	
	private GameScreen elements;
	private LinkedList<PolyominoPattern> polyominosPatterns;
	private LinkedList<Letter[]> lettersPatterns;
	private int indice;
	private HashSet<String> words;
	private Gameboard gb;
	private int numPlayer;
	private static boolean anagramModeOn;
	private static boolean wordleModeOn;
	private boolean over;
	private boolean win;
	private Modificator modificator;
	private Reserve reserve;
	
	public Player(LinkedList<PolyominoPattern> polyominosPatterns, LinkedList<Letter[]> lettersPatterns, HashSet<String> words, int player, Gameboard gb) {
		this.polyominosPatterns = polyominosPatterns;
		this.lettersPatterns = lettersPatterns;
		this.words = words;
		
		indice = 0;
		
		elements = new GameScreen(player);
		
		this.numPlayer = player;
		
		elements.addPolyomino(new Polyomino(this.polyominosPatterns.get(0), this.lettersPatterns.get(0)));
		elements.addPolyomino(new Polyomino(this.polyominosPatterns.get(1), this.lettersPatterns.get(1)));
		
		this.setSpeed(1);
		this.setTime(0);
		
		this.gb = gb;
		
		setAnagramModeOn(false);
		setWordleModeOn(false);
		
		setOver(false);
		setWin(false);
		
		modificator = new Modificator();
		reserve = new Reserve(gb);
		reserve.start();
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			Long start = System.currentTimeMillis();
		    this.setTime(this.getTime() + this.getSpeed());
		    
		    if(!isWin() && !isOver()) tetris();
		    if(modificator.collision(getCurrentPolyomino()) && reserve.isEmpty()) {
		    	reserve.copyModificator(modificator);
		    	modificator.createModificator();
		    	gb.repaint();
		    }
		    
		    if(reserve.isFired() && reserve.getModificator().getType() == 7) {
    			if(!isWordleModeOn()) wordle();
    		}
		    
		    Long end = System.currentTimeMillis();
		    // On attend pour respecter le nombre d'images par seconde.
		    if(end - start < (double) (1000.0 / Player.framePerSecond)) {
			    try {
			    	long result = 1000/Player.framePerSecond - (end - start);
					Thread.sleep(result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		}
	}
	
	public void tetris() {
		SoundEffect.init();
	    SoundEffect.volume = SoundEffect.Volume.LOW;  
		
	    if((int) this.getTime() % 3 == 0) {
		    if(reserve.isFired() && reserve.getModificator().getType() == 3)
		    	elements.setScore(elements.getScore() + 1);
		    
		    if(reserve.isFired() && reserve.getModificator().getType() == 4)
			    elements.setScore(elements.getScore() - 1);
	    }
	    
	    
	    int bonusTime = 1;
	    if(reserve.isFired() && reserve.getModificator().getType() == 1)  bonusTime = 2;
	    
		if(this.getTime() >= Player.framePerSecond * bonusTime / ((elements.getLinesDestroyed() / 10) + 1)) {
	    	this.setTime(0);
	    		
	    	modificator.update();
	    	
		    	Polyomino current = getCurrentPolyomino();
		    	
		    	if(tryMove(current, "down", 1)) {
		    		HashMap<Integer, Integer> modifs = gb.modificationMap();
		    		if(!modifs.containsKey(numPlayer) && modifs.containsValue(5)) {
		    			if(tryMove(current, "right", 1)) {
		    				current.setPosition(current.getPosition().x + 1, current.getPosition().y);
		    			}
		    		} else if(!modifs.containsKey(numPlayer) && modifs.containsValue(6)) {
		    			if(tryMove(current, "left", -1)) {
		    				current.setPosition(current.getPosition().x - 1, current.getPosition().y);
		    			}
		    		}
		    		current.setPosition(current.getPosition().x, current.getPosition().y + 1);
		    	} else {
		    		int nbLinesDestroyed = destroyLines();
		    		SoundEffect.HIT.play();
		    		if(current.isOutOfLimits()) {
		    			setOver(true);
		    		} else {
		    			elements.setScore(elements.getScore() + (4 * ((elements.getLinesDestroyed() / 10) + 1)));
		    			elements.setScore(elements.getScore() + (nbLinesDestroyed*nbLinesDestroyed * ((elements.getLinesDestroyed() / 10) + 1)));
		  
		    			// On récupère l'élément suivant dans la liste.
		    			++this.indice;
		    		}
		    	}
		    	
	    	gb.repaint();
	    }
	}
	
	public synchronized void anagram(LinkedList<Brick> bricks) {
		
		setAnagramModeOn(true);
		
		AnagramListener anagram = new AnagramListener(bricks, elements.getBrickSize(), elements.getWidth() * numPlayer);
		gb.addMouseListener(anagram);
		
		elements.setAnagramOn(bricks.get(0).getAbsoluteCoords().y);
		
		while(true) {
			if(isWin() || isOver()) break;
			Long start = System.currentTimeMillis();
			
			anagram.setBrickSize(elements.getBrickSize());
			anagram.setBorder(elements.getWidth() * numPlayer);
			elements.setBricksClicked(anagram.getBricksClicked());
			
			gb.repaint();
			if(anagram.isValidate()) {
				if(searchWord(anagram.getWord())) {
					elements.setScore(elements.getScore() + anagram.getScore());
				}
				elements.setAnagramOn(-10);
				gb.repaint();
				setAnagramModeOn(false);
				break;
			}
			
			Long end = System.currentTimeMillis();
		    // On attend pour respecter le nombre d'images par seconde.
		    if(end - start < (double) (1000.0 / Player.framePerSecond)) {
			    try {
			    	long result = 1000/Player.framePerSecond - (end - start);
					Thread.sleep(result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		}
	}
	
	public synchronized void wordle() {
		setWordleModeOn(true);
		
		WordleListener wordle = new WordleListener(elements.getPolyominos(), getCurrentPolyomino(), elements.getPolyomino(indice + 1), elements.getBrickSize(), elements.getWidth() * numPlayer);
		gb.addMouseListener(wordle);
		
		while(true) {
			if(isWin() || isOver()) break;
			Long start = System.currentTimeMillis();
			
			wordle.setBrickSize(elements.getBrickSize());
			wordle.setBorder(elements.getWidth() * numPlayer);
			elements.setBricksWordle(wordle.getBricksClicked());
			wordle.setCurrent(getCurrentPolyomino());
			wordle.setNext(elements.getPolyomino(indice + 1));
			
			gb.repaint();
			if(wordle.isValidate()) {
				if(searchWord(wordle.getWord())) {
					elements.setScore(elements.getScore() + wordle.getScore());
					wordle.deleteBricks();
				} else {
					elements.setBricksWordle(null);
					gb.repaint();
					setWordleModeOn(false);
					reserve.setEmpty(true);
		    		reserve.setFired(false);
					break;
				}
			}
			
			Long end = System.currentTimeMillis();
		    // On attend pour respecter le nombre d'images par seconde.
		    if(end - start < (double) (1000.0 / Player.framePerSecond)) {
			    try {
			    	long result = 1000/Player.framePerSecond - (end - start);
					Thread.sleep(result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		}
	}

	public boolean tryMove(Polyomino current, String direction, int nbCase) {
		if(!current.keepInBox(direction, nbCase)) {
			return false;
		} else {
			for(int k = 0; k < indice + 1; ++k) {
				Polyomino p = elements.getPolyomino(k);
				if(!p.equals(current)) {
					for(int i = 0; i < p.getNumberOfBricks(); ++i) {
						for(int j = 0; j < current.getNumberOfBricks(); ++j) {
							if(direction == "down") {
								if(current.getBrick(j).getAbsoluteCoords().y + nbCase == p.getBrick(i).getAbsoluteCoords().y) {
									if(current.getBrick(j).getAbsoluteCoords().x == p.getBrick(i).getAbsoluteCoords().x) {	
										return false;
									}
								}
							} else if(direction == "left") {
								if(current.getBrick(j).getCoords().x + current.getPosition().x + nbCase == p.getBrick(i).getCoords().x + p.getPosition().x) {	
									if(current.getBrick(j).getCoords().y + current.getPosition().y == p.getBrick(i).getCoords().y + p.getPosition().y) {
										return false;
									}
								}
							} else if(direction == "right") {
								if(current.getBrick(j).getCoords().x + current.getPosition().x + nbCase == p.getBrick(i).getCoords().x + p.getPosition().x) {
									if(current.getBrick(j).getCoords().y + current.getPosition().y == p.getBrick(i).getCoords().y + p.getPosition().y) {
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean rotate(String direction, Polyomino current) {
		Point[] tmpCoords = new Point[current.getNumberOfBricks()];
		for(int i = 0; i < current.getNumberOfBricks(); ++i) {
			tmpCoords[i] = new Point(current.getBrick(i).getCoords());
		}
		
		for(int i = 0; i < current.getNumberOfBricks(); ++i) {
			if(direction == "left") current.getBrick(i).setCoords(new Point(tmpCoords[i].y,-tmpCoords[i].x));
			else current.getBrick(i).setCoords(new Point(-tmpCoords[i].y,tmpCoords[i].x));
		}
		
		if(!tryMove(current, "down", 0) || !tryMove(current, "left", 0) || !tryMove(current, "right", 0)) {
			
			for(int i = 1; i < current.getNumberOfBricks(); ++i) {
				if(tryMove(current, "right", i) && tryMove(current, "left", i) && tryMove(current, "down", 0)) {
					current.setPosition(current.getPosition().x + i, current.getPosition().y);
					return true;
				}
				if(tryMove(current, "left", -i) && tryMove(current, "right", -i) && tryMove(current, "down", 0)) {
					current.setPosition(current.getPosition().x - i, current.getPosition().y);
					return true;
				}
			}
			
			for(int i = 0; i < current.getNumberOfBricks(); ++i) {
				current.getBrick(i).setCoords(new Point(tmpCoords[i].x,tmpCoords[i].y));
			}
		}
		
		return true;
	}
	
	public int destroyLines() {
		
		// On recherche les lignes à supprimer.
		HashMap<Integer, Integer> lines = new HashMap<Integer, Integer>();
		
		for(int k = 0; k < indice + 1; ++k) {
			Polyomino p = elements.getPolyomino(k);
			
			for(int i = 0; i < p.getNumberOfBricks(); ++i) {
				int nbBricks = 0;
				int coordY = p.getBrick(i).getAbsoluteCoords().y;
				if(lines.containsKey(coordY)) nbBricks = lines.get(coordY);
				lines.put(coordY, nbBricks + 1);
			}
		}
		
		// On stocke les briques pour l'anagramme.
		Set<Integer> keys = lines.keySet();
		Iterator<Integer> it = keys.iterator();
		LinkedList<Integer> coordsY = new LinkedList<Integer>();
		
		while(it.hasNext()) {
		    Integer coordY = it.next();
		    Integer nbBricks = lines.get(coordY);
		    
		    if(nbBricks == 10) {
		    	coordsY.add(coordY);
		    	
		    	LinkedList<Brick> bricksToDelete = new LinkedList<Brick>();
		    	
		    	Iterator<Polyomino> itr2 = elements.getPolyominos().iterator();
				while(itr2.hasNext()) {
					Polyomino p = (Polyomino) itr2.next();
					
					Iterator<Brick> itrBricks = p.getBricks().iterator();
					while(itrBricks.hasNext()) {
						Brick b = (Brick) itrBricks.next();
						if(b.getCoords().y + p.getPosition().y == coordY) {
							bricksToDelete.add(b);
						}
					}
				}
				
				if(!isAnagramModeOn()) anagram(bricksToDelete);
		    }
		}
		
		if(coordsY.size() == 0) return 0;
		
		// On supprime les lignes.
		Iterator<Integer> itrCoords = coordsY.iterator();
		while(itrCoords.hasNext()) {
			int coordY = (int) itrCoords.next();
			elements.setLinesDestroyed(elements.getLinesDestroyed() + 1);
			
			LinkedList<Brick> bricksToDelete = new LinkedList<Brick>();
	    	
			for(int k = 0; k < indice + 1; ++k) {
				Polyomino p = elements.getPolyomino(k);
				
				Iterator<Brick> itrBricks = p.getBricks().iterator();
				while(itrBricks.hasNext()) {
					Brick b = (Brick) itrBricks.next();
					if(b.getAbsoluteCoords().y == coordY) {
						bricksToDelete.add(b);
						SoundEffect.COMPLETE_LINE.play();
					}
				}
				
				int nbBricksToDelete = bricksToDelete.size();
				for(int i = nbBricksToDelete - 1; i >= 0; --i) {
					p.removeBrick(bricksToDelete.get(i));
				}
			}
		}
		
		// On fait tomber les briques au dessus des lignes détruites
		for(int k = 0; k < indice + 1; ++k) {
			Polyomino p = elements.getPolyomino(k);
			
			Iterator<Brick> itrBricks = p.getBricks().iterator();
			while(itrBricks.hasNext()) {
				Brick b = (Brick) itrBricks.next();
				
				int nbCase = 0;
				for(int i = 0; i < coordsY.size(); ++i) {
					if(b.getAbsoluteCoords().y < coordsY.get(i)) {
						++nbCase;
					}
				}
				
				if(nbCase > 0) b.setCoords(new Point(b.getCoords().x, b.getCoords().y + nbCase));
			}
		}
		
		// On supprime les polyominos sans briques.
		/*Iterator<Polyomino> itr3 = elements.getPolyominos().iterator();
		LinkedList<Polyomino> polyominosToDelete = new LinkedList<Polyomino>();
		while(itr3.hasNext()) {
			Polyomino p = (Polyomino) itr3.next();
			
			if(p.getNumberOfBricks() == 0) polyominosToDelete.add(p);
		}
		
		int nbPolyominosToDelete = polyominosToDelete.size();
		for(int i = nbPolyominosToDelete - 1; i >= 0; --i) {
			elements.removePolyomino(polyominosToDelete.get(i));
		}*/
		
		return coordsY.size();
	}
	
	public boolean searchWord(String s) {
		
		return words.contains(s);
	}
	
	public void updatePolyominoList() {
		int i = elements.getPolyominos().size();
		elements.addPolyomino(new Polyomino(this.polyominosPatterns.get(i), this.lettersPatterns.get(i)));
	}
	
	public Polyomino getCurrentPolyomino() {
		return elements.getPolyomino(indice);
	}
	
	public GameScreen getGameScreen() {
		return elements;
	}
	
	public void setGameScreen(GameScreen elements) {
		this.elements = elements;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	public int getIndice() {
		return indice;
	}

	public static boolean isAnagramModeOn() {
		return anagramModeOn;
	}

	public static void setAnagramModeOn(boolean anagramModeOn) {
		Player.anagramModeOn = anagramModeOn;
	}
	
	public static boolean isWordleModeOn() {
		return wordleModeOn;
	}
	
	private static void setWordleModeOn(boolean b) {
		Player.wordleModeOn = b;
	}

	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public Modificator getModificator() {
		return modificator;
	}

	public void setModificator(Modificator modificator) {
		this.modificator = modificator;
	}
	
	public Reserve getReserve() {
		return reserve;
	}

	public void setReserve(Reserve reserve) {
		this.reserve = reserve;
	}
	
	public Gameboard getGameboard() {
		return gb;
	}
	
	public void setGameboard(Gameboard gb) {
		this.gb = gb;
	}
	
	public static int getFramePerSecond() {
		return framePerSecond;
	}

}
