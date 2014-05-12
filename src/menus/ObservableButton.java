package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;

public class ObservableButton extends Observable {
	private boolean isClicked;
	private String text;
	private JButton button;
	
	public ObservableButton(String text) {
		
		this.setClicked(false);
		
		this.text = text;
		button = new JButton(this.text);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateObservers();
			}
		});
	}
	
	public void updateObservers() {
		this.setClicked(true);
		this.setChanged();
		this.notifyObservers(true);
	}

	public boolean getClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
