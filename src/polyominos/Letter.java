package polyominos;

public class Letter {
	
	private String value;
	private double frequence;
	
	public Letter(String value, double frequence) {
		this.value = value;
		this.frequence = frequence;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = new String(value);
	}

	public double getFrequence() {
		return frequence;
	}

	public void setFrequence(double frequence) {
		this.frequence = frequence;
	}

}
