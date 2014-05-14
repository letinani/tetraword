package modificators;

import java.io.Serializable;

import control.SoundEffect;
import menus.Gameboard;
import menus.Interface;

public class Reserve extends Thread implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int framePerSecond = 30;
	private double speed;
	private double time;
	
	private boolean empty;
	private boolean fired;
	private int delay;
	private Gameboard gb;
	
	private Modificator modificator;
	
	public Reserve(Gameboard gb) {
		this.gb = gb;
		empty = true;
		delay = 14;
		
		setSpeed(1);
		setTime(0);
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			if(Interface.getIsPaused()) continue;
			
			Long start = System.currentTimeMillis();
		    this.setTime(this.getTime() + this.getSpeed());
		    Long end = System.currentTimeMillis();
		    
		    	
		    if(!isEmpty() && modificator.getType() != 7) {
			    if(this.getTime() >= Reserve.framePerSecond) {
			    	this.setTime(0);
			    	
				    if(!isEmpty() && isFired()) {
				    	modificator.update();
				    	
				    	if(modificator.getDelay() < -delay) {
				    		setEmpty(true);
				    		setFired(false);
				    	}
				    }
			    }
			    
			    if(!isEmpty() && isFired()) {
			    	gb.repaint();
			    }
		    }
		    
		    // On attend pour respecter le nombre d'images par seconde.
		    if(end - start < (double) (1000.0 / Reserve.framePerSecond)) {
			    try {
			    	long result = 1000/Reserve.framePerSecond - (end - start);
					Thread.sleep(result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		}
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public Modificator getModificator() {
		return modificator;
	}

	public void setModificator(Modificator modificator) {
		this.modificator = modificator;
	}
	
	public void copyModificator(Modificator modificator) {
		this.modificator = modificator.copy();
		this.modificator.setDelay(-9);
		setEmpty(false);
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

	public boolean isFired() {
		return fired;
	}

	public void setFired(boolean fired) {
		SoundEffect.APPLY_MODIF.play();
		this.fired = fired;
	}

}
