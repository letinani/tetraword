package modificators;

public class Reserve {
	
	private boolean empty;
	
	private Modificator modificator;
	
	public Reserve() {
		empty = true;
	}
	
	public void action() {
		
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
		setEmpty(false);
	}

}
