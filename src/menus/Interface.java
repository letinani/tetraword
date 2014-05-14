package menus;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import control.Keyboard;

public class Interface extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private int widthScreen;
	private int heightScreen;
	private Menu mainMenu;
	private MenuInGame subMenu;
	private static boolean isPaused = false;
	private Gameboard gb;
	private Options options;
	private BufferedReader reader;
	private int nbWords;
	private HashSet<String> words;
	
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
		
		subMenu = new MenuInGame();
		subMenu.getQuitButton().addObserver(this);
		subMenu.getMenuButton().addObserver(this);
		subMenu.getBackButton().addObserver(this);

		try {
			reader = new BufferedReader(new FileReader("data/conf/words.txt"));
			nbWords = Integer.parseInt(reader.readLine());
			
			words = new HashSet<String>();
			
			for(int p = 0; p < nbWords; ++p) {
				words.add(reader.readLine());
			}
		
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		addKeyListener(new Keyboard(this));


	}
	
	@Override
	public Dimension getPreferredSize() {
        return new Dimension(this.widthScreen,this.heightScreen);
    }

	@Override
	public void update(Observable obs, Object obj) { // Fonction qui sert �� r��cup��rer les clics des boutons du menu
		
		ObservableButton button = (ObservableButton) obs;
		
		if(button.getText() == "Solo") {
			gb = new Gameboard(options.getPolyominoType(), (short) 1, words, false, options.getProbability());
			getContentPane().add(gb);
			getContentPane().remove(mainMenu);
			
		} else if(button.getText() == "VS Bot") {
			gb = new Gameboard(options.getPolyominoType(), (short) 2, words, true, options.getProbability());
			getContentPane().add(gb);
			getContentPane().remove(mainMenu);
			
		} else if(button.getText() == "2 Joueurs") {
			gb = new Gameboard(options.getPolyominoType(), (short) 2, words, false, options.getProbability());
			getContentPane().add(gb);
			getContentPane().remove(mainMenu);
			
		} else if(button.getText() == "3 Joueurs") {
			gb = new Gameboard(options.getPolyominoType(), (short) 3, words, false, options.getProbability());
			getContentPane().add(gb);
			getContentPane().remove(mainMenu);
			
		} else if(button.getText() == "4 Joueurs") {
			gb = new Gameboard(options.getPolyominoType(), (short) 4, words, false, options.getProbability());
			getContentPane().add(gb);
			getContentPane().remove(mainMenu);
			
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
		} else if(button.getText() == "Quitter") {
			System.exit(0);
		} else if(button.getText() == "Menu") {
			isPaused = false;
			for(int i = 0; i < gb.getPlayers().length; ++i) {
				gb.getPlayer(i).setOver(true);
			}
			getContentPane().remove(subMenu);
			endGame();
			
		}else if(button.getText() == "Reprendre le jeu") {
			backtoGame();
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
	
	public void pauseGame() { // Exécutée quand le jeu est en pause
		isPaused = true;
		
		if(gb != null) {
			getContentPane().remove(gb);
			getContentPane().add(subMenu);
		}

		this.validate();
		this.repaint();

	}
	public void backtoGame() { // Exécutée quand le jeu est en pause
		isPaused = false;
		
		if(gb != null) {
			getContentPane().remove(subMenu);
			getContentPane().add(gb);
		}

		this.validate();
		this.repaint();
	}
	public Gameboard getGameboard() {
		return gb;
	}

	public static boolean getIsPaused() {
		return isPaused;
	}
}
