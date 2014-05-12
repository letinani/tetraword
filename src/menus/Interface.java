package menus;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import control.Keyboard;
import control.SoundEffect;

public class Interface extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private int widthScreen;
	private int heightScreen;
	private Menu mainMenu;
	private MenuInGame subMenu;
	private Gameboard gb;
	private Options options;
	private BufferedReader reader;
	private int nbWords;
	private String[] words;
	
	public Interface(int widthScreen, int heightScreen) {
		this.widthScreen = widthScreen;
		this.heightScreen = heightScreen;
		
		setTitle("Tetraword");
		setSize(this.widthScreen, this.heightScreen);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setFocusable(true);
		
		// On crée les éléments du jeu
		mainMenu = new Menu();
		getContentPane().add(mainMenu);
		mainMenu.getSoloButton().addObserver(this);
		mainMenu.getOrdiButton().addObserver(this);
		mainMenu.getTwoButton().addObserver(this);
		mainMenu.getThreeButton().addObserver(this);
		mainMenu.getFourButton().addObserver(this);
		mainMenu.getParamButton().addObserver(this);
		
		options = new Options();
		options.getTrioButton().addObserver(this);
		options.getTetroButton().addObserver(this);
		options.getPentoButton().addObserver(this);
		options.getHexaButton().addObserver(this);
		options.getReturnButton().addObserver(this);
		
		
		try {
			reader = new BufferedReader(new FileReader("data/conf/words.txt"));
			nbWords = Integer.parseInt(reader.readLine());
			
			words = new String[nbWords];
			
			for(int p = 0; p < nbWords; ++p) {
				words[p] = reader.readLine();
			}
		
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public Dimension getPreferredSize() {
        return new Dimension(this.widthScreen,this.heightScreen);
    }

	@Override
	public void update(Observable obs, Object obj) { // Fonction qui sert à récupérer les clics des boutons du menu
		
		ObservableButton button = (ObservableButton) obs;
		
		if(button.getText() == "Solo") {
			gb = new Gameboard(options.getPolyominoType(), (short) 1, words);
			subMenu = new MenuInGame();
			//gb.add(subMenu);
			getContentPane().add(gb);
			getContentPane().remove(mainMenu);
			
			addKeyListener(new Keyboard(this));
		} else if(button.getText() == "VS Bot") {
			
		} else if(button.getText() == "2 Joueurs") {
			gb = new Gameboard(options.getPolyominoType(), (short) 2, words);
			getContentPane().add(gb);
			getContentPane().remove(mainMenu);
			
			addKeyListener(new Keyboard(this));
		} else if(button.getText() == "3 Joueurs") {
			gb = new Gameboard(options.getPolyominoType(), (short) 3, words);
			getContentPane().add(gb);
			getContentPane().remove(mainMenu);
			
			addKeyListener(new Keyboard(this));
		} else if(button.getText() == "4 Joueurs") {
			gb = new Gameboard(options.getPolyominoType(), (short) 4, words);
			getContentPane().add(gb);
			getContentPane().remove(mainMenu);
			
			addKeyListener(new Keyboard(this));
		} else if(button.getText() == "Paramètres") {
			getContentPane().add(options);
			getContentPane().remove(mainMenu);
		} else if(button.getText() == "Triominos") {
			options.getTetroButton().getButton().setEnabled(true);
			options.getPentoButton().getButton().setEnabled(true);
			options.getHexaButton().getButton().setEnabled(true);
			button.getButton().setEnabled(false);
			
			options.setPolyominoType((short) 3);
		} else if(button.getText() == "Tetrominos") {
			options.getTrioButton().getButton().setEnabled(true);
			options.getPentoButton().getButton().setEnabled(true);
			options.getHexaButton().getButton().setEnabled(true);
			button.getButton().setEnabled(false);
			
			options.setPolyominoType((short) 4);
		} else if(button.getText() == "Pentominos") {
			options.getTrioButton().getButton().setEnabled(true);
			options.getTetroButton().getButton().setEnabled(true);
			options.getHexaButton().getButton().setEnabled(true);
			button.getButton().setEnabled(false);
			
			options.setPolyominoType((short) 5);
		} else if(button.getText() == "Hexaminos") {
			options.getTrioButton().getButton().setEnabled(true);
			options.getTetroButton().getButton().setEnabled(true);
			options.getPentoButton().getButton().setEnabled(true);
			button.getButton().setEnabled(false);
			
			options.setPolyominoType((short) 6);
		} else if(button.getText() == "Retour") {
			getContentPane().remove(options);
			getContentPane().add(mainMenu);
		}
		
		this.validate();
		this.repaint();
	}
	
	public void endGame() { // Exécutée quand le jeu se termine
		getContentPane().remove(gb);
		getContentPane().add(mainMenu);
		
		this.validate();
		this.repaint();
	}
	
	public Gameboard getGameboard() {
		return gb;
	}
	public static void main(String[] args) {
		Interface tetraword = new Interface(800, 600);
		tetraword.pack();
		tetraword.setVisible(true);
		SoundEffect.init();
	    SoundEffect.volume = SoundEffect.Volume.LOW;  
		SoundEffect.WELCOME.play();

	}

}
