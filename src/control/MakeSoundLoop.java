package control;

import java.io.FileInputStream;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class MakeSoundLoop {
	
	private static AudioPlayer MGP = AudioPlayer.player;
	private static AudioStream BGM;
	private static AudioData MD;
	private static ContinuousAudioDataStream loop = null;
	
	
    public static void playSound(String filename, boolean is_loop){
    	try {
    		BGM = new AudioStream (new FileInputStream (filename));
    		MD = BGM.getData();
    		loop = new ContinuousAudioDataStream(MD);
    	}catch (Exception e) {}
    	if (is_loop) {
    		MGP.start(loop);
    	}else {MGP.start();}
    }
		
		
		
		
		
		
		
		
		
		
		
	/*
	    private final static int BUFFER_SIZE = 128000;
	    private static File soundFile;
	    private static AudioInputStream audioStream;
	    private static AudioFormat audioFormat;
	    private static SourceDataLine sourceLine;

	    /**
	     * @param filename the name of the file that is going to be played
	     */
		/*
	    public static void playSound(String filename){

	        String strFilename = filename;

	        try {
	            soundFile = new File(strFilename);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }

	        try {
	            audioStream = AudioSystem.getAudioInputStream(soundFile);
	        } catch (Exception e){
	            e.printStackTrace();
	            System.exit(1);
	        }

	        audioFormat = audioStream.getFormat();

	        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
	        try {
	            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
	            sourceLine.open(audioFormat);
	        } catch (LineUnavailableException e) {
	            e.printStackTrace();
	            System.exit(1);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }

	        sourceLine.start();

	        int nBytesRead = 0;
	        byte[] abData = new byte[BUFFER_SIZE];
	        while (nBytesRead != -1) {
	            try {
	                nBytesRead = audioStream.read(abData, 0, abData.length);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            if (nBytesRead >= 0) {
	                @SuppressWarnings("unused")
	                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
	            }
	        }

	        sourceLine.drain();
	        //sourceLine.close();
	    }
	 */   	
}
