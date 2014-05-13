package modificators;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;

import polyominos.Brick;
import polyominos.Polyomino;

public class Modificator {
	
	private int delay;
	private Point position;
	private boolean visible;
	private int nbTypes;
	private int type;
	private BufferedImage icon;

	public Modificator() {
		
		createModificator();
	}
	
	public void createModificator() {
		setDelay(randomNumber(7, 15));
		setPosition(new Point(randomNumber(0, 8), randomNumber(0, 18)));
		setVisible(false);
		setNbTypes(7);
		setType(randomNumber(0, getNbTypes() - 1));
		setIcon(modificatorType(getType()));
	}
	
	public BufferedImage modificatorType(int t) {
		try {
			switch(t) {
				case 0: 
					return ImageIO.read(new File("data/img/wave.jpg"));
				case 1:
					return ImageIO.read(new File("data/img/chrono.jpg"));
				case 2:
					return ImageIO.read(new File("data/img/reversal.jpg"));
				case 3:
					return ImageIO.read(new File("data/img/bonus.jpg"));
				case 4:
					return ImageIO.read(new File("data/img/malus.jpg"));
				case 5:
					return ImageIO.read(new File("data/img/right_wind.jpg"));
				case 6:
					return ImageIO.read(new File("data/img/left_wind.jpg"));
			}
		} catch(IOException e) {
  	    	e.printStackTrace();
  	    }
		
		return null;
	}
	
	public void update() {
		--delay;
		if(delay == 0) {
			setVisible(true);
		} else if(delay == -10) {
			setVisible(false);
		} else if(delay == -11) {
			setVisible(true);
		} else if(delay == -12) {
			setVisible(false);
		} else if(delay == -13) {
			setVisible(true);
		} else if(delay == -14) {
			setVisible(false);
		} else if(delay < -15) {
			createModificator();
		}
	}
	
	public boolean collision(Polyomino p) {
		if(getDelay() > 0) return false;
		Iterator<Brick> itr = p.getBricks().iterator();
		while(itr.hasNext()) {
			Brick b = (Brick) itr.next();
			if(b.getAbsoluteCoords().x == position.x && b.getAbsoluteCoords().y == position.y) {
				return true;
			}
		}
		return false;
	}
	
	public Modificator copy() {
		Modificator clone = new Modificator();
		clone.setVisible(true);
		clone.setType(getType());
		clone.setIcon(modificatorType(getType()));
		
		return clone;
	}
	
	public int randomNumber(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public BufferedImage getIcon() {
		return icon;
	}

	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}

	public int getNbTypes() {
		return nbTypes;
	}

	public void setNbTypes(int nbTypes) {
		this.nbTypes = nbTypes;
	}
}
