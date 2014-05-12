package control;

import java.io.*;

import javax.sound.sampled.*;

import sun.audio.ContinuousAudioDataStream;



public enum SoundEffect {
   WELCOME("data/sounds/Pickup_Coin9.wav"),   
   HIT("data/sounds/Pickup_Coin10.wav"),
   LOOSE("data/sounds/Danger2.wav"),
   WIN("data/sounds/Emerge5.wav"),// la fin de la partie
   COMPLETE_LINE("data/sounds/Beep8.wav"),
   MUSIC("data/sounds/POL-mecha-world-short.wav");     // son : quand la ligne est complétée 
   
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
   
   public static Volume volume = Volume.LOW;
   
   private Clip clip;
   
   SoundEffect(String soundFileName) {
      try {
    	  File soundFile = new File(soundFileName);
         // Set up an audio input stream piped from the sound file.
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
         // Get a clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop();   
         clip.setFramePosition(0); 
         clip.start();     
      }
   }
   public void playLoop() {
	   clip.loop(Clip.LOOP_CONTINUOUSLY);
   } 
   public void stop() {
	   clip.stop();
   } 
   // Optional static method to pre-load all the sound files.
   public static void init() {
      values(); // calls the constructor for all the elements
   }
}