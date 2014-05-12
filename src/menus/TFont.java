package menus;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class TFont {

	public static Font loadFont(String fontURL) {

		Font ttfBase = null;
  		Font ttfReal = null;
		try {
			InputStream myStream = new BufferedInputStream(new FileInputStream(fontURL));
			ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
			ttfReal = ttfBase.deriveFont(Font.PLAIN, 32);
		} catch (Exception ex) {
	        ex.printStackTrace();
	        System.err.println("CODE2000 not loaded.");
	    }
		return ttfReal;
	}

}
