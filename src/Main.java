import control.SoundEffect;
import menus.Interface;


public class Main {

	public static void main(String[] args) {
		Interface tetraword = new Interface(800, 600);
		tetraword.pack();
		tetraword.setVisible(true);
		SoundEffect.init();
	    SoundEffect.volume = SoundEffect.Volume.LOW;  
		SoundEffect.WELCOME.play();

	}
}
