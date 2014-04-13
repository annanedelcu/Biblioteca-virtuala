package graphics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

// clasa ce contine o metoda statica pt centrarea pe ecran unei ferestre primita ca parametru
public class CenterFrame {
	
	public static void center (Window w) { 
		Dimension ws = w.getSize(); 
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize(); 
		int newX = ( ss.width - ws.width ) / 2; 
		int newY = ( ss.height- ws.height ) / 2; 
		w.setLocation( newX, newY );
		}

}
