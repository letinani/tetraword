package control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import menus.Gameboard;
import polyominos.Brick;
import polyominos.Letter;
import polyominos.Polyomino;
import polyominos.PolyominoPattern;

public class Bot extends Player {
	

	public Bot(LinkedList<PolyominoPattern> polyominosPatterns, LinkedList<Letter[]> lettersPatterns, HashSet<String> words, int player, Gameboard gb) {
		super(polyominosPatterns, lettersPatterns, words, player, gb);
		
	}
	
	@Override
	public synchronized void run() {
		while(!Thread.interrupted()) {
			Long start = System.currentTimeMillis();
		    this.setTime(this.getTime() + this.getSpeed());
		    
		    if((int) this.getTime() % 10 == 0) control();
		    
		    if(!isWin() && !isOver()) tetris();
		    if(getModificator().collision(getCurrentPolyomino()) && getReserve().isEmpty()) {
		    	getReserve().copyModificator(getModificator());
		    	getModificator().createModificator();
		    	getReserve().setFired(true);
		    	getGameboard().repaint();
		    }
		    
		    if(getReserve().isFired() && getReserve().getModificator().getType() == 7) {
    			getReserve().setEmpty(true);
    			getReserve().setFired(false);
    		}
		    
		    Long end = System.currentTimeMillis();
		    // On attend pour respecter le nombre d'images par seconde.
		    if(end - start < (double) (1000.0 / Player.getFramePerSecond())) {
			    try {
			    	long result = 1000/Player.getFramePerSecond() - (end - start);
					Thread.sleep(result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		}
	}
	
	@Override
	public void tetris() {
		SoundEffect.init();
	    SoundEffect.volume = SoundEffect.Volume.LOW;  
		
	    if((int) this.getTime() % 10 == 0) {
		    if(getReserve().isFired() && getReserve().getModificator().getType() == 3) {
		    	getGameScreen().setScore(getGameScreen().getScore() + 1);
		    	SoundEffect.BONUS.play();
		    }
		    
		    if(getReserve().isFired() && getReserve().getModificator().getType() == 4) {
		    	getGameScreen().setScore(getGameScreen().getScore() - 1);
		    	SoundEffect.MALUS.play();
		    }
	    }
	    
	    
	    int bonusTime = 1;
	    if(getReserve().isFired() && getReserve().getModificator().getType() == 1)  bonusTime = 2;
	    
		if(this.getTime() >= Player.getFramePerSecond() * bonusTime / ((getGameScreen().getLinesDestroyed() / 10) + 1)) {
	    	this.setTime(0);
	    		
	    	getModificator().update();
	    	
		    	Polyomino current = getCurrentPolyomino();
		    	
		    	if(tryMove(current, "down", 1)) {
		    		HashMap<Integer, Integer> modifs = getGameboard().modificationMap();
		    		if(!modifs.containsKey(1) && modifs.containsValue(5)) {
		    			if(tryMove(current, "right", 1)) {
		    				current.setPosition(current.getPosition().x + 1, current.getPosition().y);
		    			}
		    		} else if(!modifs.containsKey(1) && modifs.containsValue(6)) {
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
		    			getGameScreen().setScore(getGameScreen().getScore() + (4 * ((getGameScreen().getLinesDestroyed() / 10) + 1)));
		    			getGameScreen().setScore(getGameScreen().getScore() + (nbLinesDestroyed*nbLinesDestroyed * ((getGameScreen().getLinesDestroyed() / 10) + 1)));
		  
		    			// On récupère l'élément suivant dans la liste.
		    			setIndice(getIndice()+1);
		    		}
		    	}
		    	
	    	getGameboard().repaint();
	    }
	}
	
	public void control() {
		Polyomino current = getCurrentPolyomino();
		
		HashMap<Integer, Integer> topCoords = new HashMap<Integer, Integer>();
		for(int i = 0; i < 10; ++i) {
			topCoords.put(i, 20-i);
		}
		
		Iterator<Polyomino> itr = getGameScreen().getPolyominos().iterator();
		while(itr.hasNext()) {
			Polyomino p = (Polyomino) itr.next();
			if(!p.equals(current)) {
				Iterator<Brick> itr2 = p.getBricks().iterator();
				while(itr2.hasNext()) {
					Brick b = (Brick) itr2.next();
					
					if(!topCoords.containsKey(b.getAbsoluteCoords().x)) {
						topCoords.put(b.getAbsoluteCoords().x, b.getAbsoluteCoords().y);
					} else if(b.getAbsoluteCoords().y < topCoords.get(b.getAbsoluteCoords().x)) {
						topCoords.put(b.getAbsoluteCoords().x, b.getAbsoluteCoords().y);
					}
				}
			}
		}
		
		Set<Integer> keys = topCoords.keySet();
		Iterator<Integer> it = keys.iterator();
		int bestXCoord = 0;
		int bestYCoord = 0;
		
		while(it.hasNext()) {
		    int coordX = it.next();
		    int coordY = topCoords.get(coordX);
		    
		    if(coordY > bestYCoord) {
		    	bestYCoord = coordY;
		    	bestXCoord = coordX;
		    }
		}		
		
		if(current.getPosition().x < bestXCoord) {
			
			if(tryMove(current, "right", 1)) {
		    	current.setPosition(current.getPosition().x + 1, current.getPosition().y);
	    	} else if(!rotate("right", current)) {
	    		if(tryMove(current, "left", -1)) {
		    		current.setPosition(current.getPosition().x - 1, current.getPosition().y);
		    		if(!rotate("right", current)) {
		    			rotate("left", current);
		    		}
	    		}
	    	}
		} else if(current.getPosition().x > bestXCoord) {
		
			if(tryMove(current, "left", -1)) {
		    	current.setPosition(current.getPosition().x - 1, current.getPosition().y);
	    	} else if(!rotate("left", current)) {
	    		if(tryMove(current, "right", 1)) {
		    		current.setPosition(current.getPosition().x + 1, current.getPosition().y);
		    		if(!rotate("left", current)) {
		    			rotate("right", current);
		    		}
	    		}
	    	}
		} else {
			if(tryMove(current, "down", 1)) {
		    	current.setPosition(current.getPosition().x, current.getPosition().y + 1);
	    	} else if(tryMove(current, "right", 1)) {
	    		current.setPosition(current.getPosition().x + 1, current.getPosition().y);
	    		if(!tryMove(current, "down", 1)) {
	    			current.setPosition(current.getPosition().x - 1, current.getPosition().y);
	    		}
	    	} else if(tryMove(current, "left", 1)) {
	    		current.setPosition(current.getPosition().x - 1, current.getPosition().y);
	    		if(!tryMove(current, "down", 1)) {
	    			current.setPosition(current.getPosition().x, current.getPosition().y + 1);
	    		}
	    	}
		}
			
		getGameboard().repaint();
	}

}
