package control;

import java.util.HashSet;
import java.util.LinkedList;

import menus.Gameboard;
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
		    	getGameboard().repaint();
		    }
		    
		    if(getReserve().isFired() && getReserve().getModificator().getType() == 7) {
    			if(!isWordleModeOn()) wordle();
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
	
	public void control() {
		Polyomino current = getCurrentPolyomino();
		if(tryMove(current, "left", -1)) {
	    	current.setPosition(current.getPosition().x - 1, current.getPosition().y);
	    	getGameboard().repaint();
    	}
	}

}
