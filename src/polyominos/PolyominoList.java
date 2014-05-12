package polyominos;

import java.util.LinkedList;
import java.util.Observable;

public class PolyominoList extends Observable {
	
	private LinkedList<Polyomino> polyominos;
	
	public PolyominoList() {
		polyominos = new LinkedList<Polyomino>();
	}
	
	public void updateObservers() {
		this.setChanged();
		this.notifyObservers(true);
	}
	
	public LinkedList<Polyomino> getPolyominoList() {
		return polyominos;
	}
	
	public synchronized Polyomino get(int i) {
		if(polyominos.size() <= i + 1) {
			updateObservers();
		}
		if(polyominos.get(i) == null) return get(i);
		return polyominos.get(i);
	}
	
	public void add(Polyomino p) {
		polyominos.add(p);
	}
	
	public void remove(Polyomino p) {
		polyominos.remove(p);
	}
	
	public int size() {
		return polyominos.size();
	}
}
