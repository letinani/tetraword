package menus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import control.Bot;
import control.Player;
import control.SoundEffect;
import polyominos.Letter;
import polyominos.PolyominoPattern;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Gameboard extends JPanel implements Observer, Serializable {

	private static final long serialVersionUID = 1L;
	private short polyominoType;
	private org.jdom.Document documentPolyominos;
	private org.jdom.Document documentLetters;
	private Element configPolyominos;
	private Element configLetters;
	private int numberOfPolyominos;
	private PolyominoPattern[] patterns;
	private LinkedList<PolyominoPattern> polyominosPatterns;
	private Letter[] letters;
	private LinkedList<Letter[]> lettersPatterns;
	private HashSet<String> words;
	private Font minecraftia;
	transient private BufferedImage backgroundImage;
 
	private Player[] players;
	

	public Gameboard(short n, short nbPlayers, HashSet<String> words, boolean bot) {
        try {
  	      backgroundImage = ImageIO.read(new File("data/img/bg_game.jpg"));
  	    } catch(IOException e) {
  	      e.printStackTrace();
  	    }
        
		try {
			this.minecraftia = TFont.loadFont("data/font/Minecraftia.ttf");
			
			SAXBuilder sxb = new SAXBuilder();
			
			switch(n) {
				case 3:
					documentPolyominos = sxb.build(new File("data/conf/triominos.xml"));
					break;
				case 4:
					documentPolyominos = sxb.build(new File("data/conf/tetrominos.xml"));
					break;
				case 5:
					documentPolyominos = sxb.build(new File("data/conf/pentominos.xml"));
					break;
				case 6:
					documentPolyominos = sxb.build(new File("data/conf/hexaminos.xml"));
					break;
				default:
					break;
			}
			
			this.setPolyominoType(n);
			
			this.words = words;
			
			documentLetters = sxb.build(new File("data/conf/letters.xml"));
			this.setConfigLetters(documentLetters.getRootElement());			
			
			int nbLetters = Integer.parseInt(this.getConfigLetters().getAttributeValue("number"));
			letters = new Letter[nbLetters];
			
			List<Element> listLetters = this.getConfigLetters().getChildren("letter");
			
			Iterator<Element> itr2 = listLetters.iterator();
			int k = 0;
			while(itr2.hasNext())
			{
			  Element current = (Element) itr2.next();
			  letters[k++] = new Letter(current.getValue(), Double.parseDouble(current.getAttributeValue("frequence")));
			}
			
			
			
			this.setConfig(documentPolyominos.getRootElement());	
			this.setNumberOfPolyominos(Integer.parseInt(this.getConfig().getAttributeValue("number")));
			
			patterns = new PolyominoPattern[this.getNumberOfPolyominos()];
			int i = 0;
			
			List<Element> listPolyominos = this.getConfig().getChildren("polyomino");
			
			Iterator<Element> itr = listPolyominos.iterator();
			while(itr.hasNext())
			{
			  Element current = (Element) itr.next();
			  List<Element> listPoints = current.getChildren("point");
			  
			  Iterator<Element> itrPoints = listPoints.iterator();
			  
			  Point[] pattern = new Point[polyominoType];
			  int j = 0;
			  while(itrPoints.hasNext())
			  {
				  Element point = (Element) itrPoints.next();
				  pattern[j++] = new Point(Integer.parseInt(point.getAttributeValue("x")), Integer.parseInt(point.getAttributeValue("y")));
			  }
			  
			  patterns[i++] = new PolyominoPattern(pattern,Integer.parseInt(current.getAttributeValue("color")));
			}
			
			this.polyominosPatterns = new LinkedList<PolyominoPattern>();
			this.polyominosPatterns.add(patterns[randomNumber(0, patterns.length-1)]);
			this.polyominosPatterns.add(patterns[randomNumber(0, patterns.length-1)]);
			
			this.lettersPatterns = new LinkedList<Letter[]>();
			Letter[] l = new Letter[getPolyominoType()];
			Letter[] l2 = new Letter[getPolyominoType()];
			
			for(int m = 0; m < getPolyominoType(); ++m) {
				double rand = randomDouble(0.0, 100.0);
				Letter tmp = searchNearestLetter(rand);
				
				l[m] = new Letter(tmp.getValue(), tmp.getFrequence());
				
				double rand2 = randomDouble(0.0, 100.0);
				Letter tmp2 = searchNearestLetter(rand2);
				
				l2[m] = new Letter(tmp2.getValue(), tmp2.getFrequence());
			}
			
			this.lettersPatterns.add(l);
			this.lettersPatterns.add(l2);
			
			
			if(!bot) {
				this.players = new Player[nbPlayers];
				
				for(int j = 0; j < nbPlayers; ++j) {
					this.players[j] = new Player(this.polyominosPatterns, this.lettersPatterns, this.words, j, this);
					this.players[j].start();
					this.players[j].getGameScreen().getPolyominoList().addObserver(this);
				}
			} else {
				this.players = new Player[nbPlayers];
				
				for(int j = 0; j < nbPlayers; ++j) {
					if(j == 0) this.players[j] = new Player(this.polyominosPatterns, this.lettersPatterns, this.words, j, this);
					else this.players[j] = new Bot(this.polyominosPatterns, this.lettersPatterns, this.words, j, this);
					this.players[j].start();
					this.players[j].getGameScreen().getPolyominoList().addObserver(this);
				}
			}
    	
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error while reading the file.");
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// lancement de la musique de fond
		SoundEffect.MUSIC.playLoop();
		
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        int nbLosers = 0;
        int winner = 0;
        
        for(int i = 0; i < players.length; ++i) {
        	
        	g.translate(this.getWidth() / players.length * i, 0);
        	
        	players[i].getGameScreen().draw(g, this.getWidth() / players.length, this.getHeight(), players[i].getIndice(), players[i].isOver(), this);
        	
        	g.translate(-this.getWidth() / players.length * i, 0);
        	
        	
        	if(players[i].isOver()) {
        		++nbLosers;
        	} else {
        		winner = i + 1;
        	}
        }
        
        if(nbLosers == players.length - 1 && players.length > 1) {
        	// arret de la musique de fond
        	SoundEffect.MUSIC.stop();
        	if(!players[winner - 1].isWin()) SoundEffect.WIN.play();
        	//affichage des résultats
        	g.setColor(Color.black);
        	g.fillRect(0, this.getHeight() / 3, this.getWidth(), this.getHeight() / 3);
        	g.setColor(Color.red);
        	g.setFont(minecraftia.deriveFont(Font.PLAIN, this.getWidth() / 20));
        	g.drawString("Le joueur " + winner + " gagne !", this.getWidth() / 6, this.getHeight() / 2);
        	g.setFont(minecraftia.deriveFont(Font.PLAIN, this.getWidth() / 40));
        	g.drawString("Appuyez sur Entrée", this.getWidth() / 3, this.getHeight() * 3 / 5);
        	
        	players[winner - 1].setWin(true);
        } else if(nbLosers == players.length && players.length > 1) {
        	// arret de la musique de fond
        	SoundEffect.MUSIC.stop();
        	//affichage des résultats
        	g.setColor(Color.black);
        	g.fillRect(0, this.getHeight() / 3, this.getWidth(), this.getHeight() / 3);
        	g.setColor(Color.red);
        	g.setFont(minecraftia.deriveFont(Font.PLAIN, this.getWidth() / 20));
        	g.drawString("Egalité !", this.getWidth() * 2 / 5, this.getHeight() / 2);
        	g.setFont(minecraftia.deriveFont(Font.PLAIN, this.getWidth() / 40));
        	g.drawString("Appuyez sur Entrée", this.getWidth() / 3, this.getHeight() * 3 / 5);
        } else if(nbLosers == 1 && players.length == 1) {
        	// arret de la musique de fond
        	SoundEffect.MUSIC.stop();
        	// bruitage
    		SoundEffect.LOOSE.play();
    		//affichage des résultats
        	g.setColor(Color.black);
        	g.fillRect(0, this.getHeight() / 3, this.getWidth(), this.getHeight() / 3);
        	g.setColor(Color.red);
        	g.setFont(minecraftia.deriveFont(Font.PLAIN, this.getWidth() / 20));
        	g.drawString("Score : " + players[0].getGameScreen().getScore(), this.getWidth() / 4, this.getHeight() / 2);
        	g.setFont(minecraftia.deriveFont(Font.PLAIN, this.getWidth() / 40));
        	g.drawString("Appuyez sur Entrée", this.getWidth() / 3, this.getHeight() * 3 / 5);
        }
	}
	
	public HashMap<Integer, Integer> modificationMap() {
		HashMap<Integer, Integer> modifs = new HashMap<Integer, Integer>();
        for(int i = 0; i < players.length; ++i) {
        	if(players[i].getReserve().isFired() && players[i].getReserve().getModificator().getType() == 0) {
        		modifs.put(i, 0);
        	} else if(players[i].getReserve().isFired() && players[i].getReserve().getModificator().getType() == 2) {
        		modifs.put(i, 2);
        	} else if(players[i].getReserve().isFired() && players[i].getReserve().getModificator().getType() == 5) {
        		modifs.put(i, 5);
        	} else if(players[i].getReserve().isFired() && players[i].getReserve().getModificator().getType() == 6) {
        		modifs.put(i, 6);
        	}
        }
        
        return modifs;
	}
	
	@Override
	public Dimension getPreferredSize() {
        return new Dimension(this.getWidth(), this.getHeight());
    }
	
	public int randomNumber(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}
	
	public double randomDouble(double min, double max) {
		return min + (max - min) * new Random().nextDouble();
	}

	public short getPolyominoType() {
		return polyominoType;
	}

	public void setPolyominoType(short polyominoType) {
		this.polyominoType = polyominoType;
	}
	
	public int getNumberOfPolyominos() {
		return numberOfPolyominos;
	}

	public void setNumberOfPolyominos(int numberOfPolyominos) {
		this.numberOfPolyominos = numberOfPolyominos;
	}

	public Element getConfig() {
		return configPolyominos;
	}

	public void setConfig(Element configPolyominos) {
		this.configPolyominos = configPolyominos;
	}
	
	public LinkedList<PolyominoPattern> getPolyominos() {
		return polyominosPatterns;
	}
	
	public void setPolyominos(LinkedList<PolyominoPattern> polyominosPatterns) {
		this.polyominosPatterns = polyominosPatterns;
	}
	
	public Player getPlayer(int i) {
		return  players[i];
	}
	
	public Player[] getPlayers() {
		return  players;
	}

	@Override
	public synchronized void update(Observable arg0, Object arg1) {
		
		this.polyominosPatterns.add(patterns[randomNumber(0, patterns.length-1)]);
		
		Letter[] l = new Letter[getPolyominoType()];
		
		for(int m = 0; m < getPolyominoType(); ++m) {
			double rand = randomDouble(0.0, 100.0);
			Letter tmp = searchNearestLetter(rand);
			
			l[m] = new Letter(tmp.getValue(), tmp.getFrequence());
		}
		this.lettersPatterns.add(l);
		
		for(int i = 0; i < players.length; ++i) {
			players[i].updatePolyominoList();
		}
	}
	
	public Letter searchNearestLetter(double rand) {
		
		double currentFreq;
		Letter l = new Letter("e", 17.1);
		
		double sumFreq = 0;
		
		for(int i = 0; i < letters.length; ++i) {
			currentFreq = letters[i].getFrequence();
			
			if(rand >= sumFreq && rand < currentFreq + sumFreq) {
				l.setValue(letters[i].getValue());
				l.setFrequence(letters[i].getFrequence());
				break;
			}
			
			sumFreq += currentFreq;
		}
		return l;
	}

	public Element getConfigLetters() {
		return configLetters;
	}

	public void setConfigLetters(Element letters) {
		this.configLetters = letters;
	}

	public Letter[] getLetters() {
		return letters;
	}

	public void setLetters(Letter[] letters) {
		this.letters = letters;
	}
	
}
