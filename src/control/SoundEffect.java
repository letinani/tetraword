package control;

import java.io.*;

import javax.sound.sampled.*;


public enum SoundEffect {
   WELCOME("data/sounds/Pickup_Coin9.wav"),  // au lancement du jeu
   HIT("data/sounds/Pickup_Coin10.wav"), // quand un polyomino touche le sol
   LOOSE("data/sounds/Danger2.wav"), // quand la partie est perdue
   WIN("data/sounds/Emerge5.wav"),// la fin de la partie
   COMPLETE_LINE("data/sounds/Beep8.wav"), // son : quand la ligne est complete
   MUSIC("data/sounds/GameBoyRocker.wav"), // musique du jeu
   MOVE("data/sounds/Blip_Select.wav"), // quand une pièce est déplacée
   GET_MODIF("data/sounds/Randomize32.wav"), // quand un modificateur est ramassé
   APPLY_MODIF("data/sounds/Randomize6.wav"),// quand un modificateur est utilisé
   BONUS("data/sounds/Powerup3.wav"), // pour les bonnes choses
   MALUS("data/sounds/Shut_Down2.wav"); // pour les mauvaise choses
   
   // nouvelle enumération pour le volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
   
   public static Volume volume = Volume.LOW;
   
   private Clip clip;
   
   SoundEffect(String soundFileName) {
      try {
    	  File soundFile = new File(soundFileName);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
         clip = AudioSystem.getClip();
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   // lire le son
   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop();  
         // remise du son au début
         clip.setFramePosition(0); 
         clip.start();     
      }
   }
   // lire le son en boucle
   public void playLoop() {
	   clip.loop(Clip.LOOP_CONTINUOUSLY);
   } 
   // arret du son
   public void stop() {
	   clip.stop();
	   clip.setFramePosition(0); 
   } 
   // pour pré charger les sons
   public static void init() {
      values();
   }
}