package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import menus.Interface;
import polyominos.Polyomino;

public class Keyboard extends KeyAdapter {
	
	private Interface iface;
	
	public Keyboard(Interface iface) {
		this.iface = iface;
	}

	@Override
    public void keyPressed(KeyEvent e) {

        int keycode = e.getKeyCode();
        
        for(int i = 0; i < iface.getGameboard().getPlayers().length; ++i) {
        	if(iface.getGameboard().getPlayer(i).getGameScreen().getAnagramOn() > 0 || iface.getGameboard().getPlayer(i).isOver() || iface.getGameboard().getPlayer(i).isWin()) continue;
        	
        	Polyomino current = iface.getGameboard().getPlayer(i).getCurrentPolyomino();
        	
        	if(i == 1) {
		        switch (keycode) {
		            
			        case KeyEvent.VK_LEFT:
			        	
			        	if(iface.getGameboard().getPlayer(i).tryMove(current, "left", -1)) {
					    	current.setPosition(current.getPosition().x - 1, current.getPosition().y);
					    	iface.getGameboard().repaint();
				    	}
			            break;
			            
			        case KeyEvent.VK_RIGHT:
		
			        	if(iface.getGameboard().getPlayer(i).tryMove(current, "right", 1)) {
					    	current.setPosition(current.getPosition().x + 1, current.getPosition().y);
					    	iface.getGameboard().repaint();
				    	}
			            break;
			            
			        case KeyEvent.VK_DOWN:
			        	
			        	if(iface.getGameboard().getPlayer(i).tryMove(current, "down", 1)) {
					    	current.setPosition(current.getPosition().x, current.getPosition().y + 1);
					    	iface.getGameboard().repaint();
				    	}
			            break;
			            
			        case KeyEvent.VK_UP:
		
			        	while(iface.getGameboard().getPlayer(i).tryMove(current, "down", 1)) {
					    	current.setPosition(current.getPosition().x, current.getPosition().y + 1);
				    	}
			        	iface.getGameboard().repaint();
			        	iface.getGameboard().getPlayer(i).setTime(30);
			            break;
			        
			        case KeyEvent.VK_CONTROL:
			        	iface.getGameboard().getPlayer(i).rotate("left", current, false);
			        	iface.getGameboard().repaint();
			            break;
			            
			        case KeyEvent.VK_SHIFT:
			        	iface.getGameboard().getPlayer(i).rotate("right", current, false);
			        	iface.getGameboard().repaint();
			            break;
			            
			        case KeyEvent.VK_SPACE:
			            break;
		        }
        	} else if(i == 0) {
        		switch (keycode) {
		            
			        case KeyEvent.VK_Q:
			        	
			        	if(iface.getGameboard().getPlayer(i).tryMove(current, "left", -1)) {
					    	current.setPosition(current.getPosition().x - 1, current.getPosition().y);
					    	iface.getGameboard().repaint();
				    	}
			            break;
			            
			        case KeyEvent.VK_D:
		
			        	if(iface.getGameboard().getPlayer(i).tryMove(current, "right", 1)) {
					    	current.setPosition(current.getPosition().x + 1, current.getPosition().y);
					    	iface.getGameboard().repaint();
				    	}
			            break;
			            
			        case KeyEvent.VK_S:
			        	
			        	if(iface.getGameboard().getPlayer(i).tryMove(current, "down", 1)) {
					    	current.setPosition(current.getPosition().x, current.getPosition().y + 1);
					    	iface.getGameboard().repaint();
				    	}
			            break;
			            
			        case KeyEvent.VK_Z:
		
			        	while(iface.getGameboard().getPlayer(i).tryMove(current, "down", 1)) {
					    	current.setPosition(current.getPosition().x, current.getPosition().y + 1);
				    	}
			        	iface.getGameboard().repaint();
			        	iface.getGameboard().getPlayer(i).setTime(30);
			            break;
			        
			        case KeyEvent.VK_A:
			        	iface.getGameboard().getPlayer(i).rotate("left", current, false);
			        	iface.getGameboard().repaint();
			            break;
			            
			        case KeyEvent.VK_E:
			        	iface.getGameboard().getPlayer(i).rotate("right", current, false);
			        	iface.getGameboard().repaint();
			            break;
			            
			        /*case KeyEvent.VK_SPACE:
			            break;*/
		        }
        	} else if(i == 2) {
        		switch (keycode) {
	            
		        case KeyEvent.VK_G:
		        	
		        	if(iface.getGameboard().getPlayer(i).tryMove(current, "left", -1)) {
				    	current.setPosition(current.getPosition().x - 1, current.getPosition().y);
				    	iface.getGameboard().repaint();
			    	}
		            break;
		            
		        case KeyEvent.VK_J:
	
		        	if(iface.getGameboard().getPlayer(i).tryMove(current, "right", 1)) {
				    	current.setPosition(current.getPosition().x + 1, current.getPosition().y);
				    	iface.getGameboard().repaint();
			    	}
		            break;
		            
		        case KeyEvent.VK_H:
		        	
		        	if(iface.getGameboard().getPlayer(i).tryMove(current, "down", 1)) {
				    	current.setPosition(current.getPosition().x, current.getPosition().y + 1);
				    	iface.getGameboard().repaint();
			    	}
		            break;
		            
		        case KeyEvent.VK_Y:
	
		        	while(iface.getGameboard().getPlayer(i).tryMove(current, "down", 1)) {
				    	current.setPosition(current.getPosition().x, current.getPosition().y + 1);
			    	}
		        	iface.getGameboard().repaint();
		        	iface.getGameboard().getPlayer(i).setTime(30);
		            break;
		        
		        case KeyEvent.VK_T:
		        	iface.getGameboard().getPlayer(i).rotate("left", current, false);
		        	iface.getGameboard().repaint();
		            break;
		            
		        case KeyEvent.VK_U:
		        	iface.getGameboard().getPlayer(i).rotate("right", current, false);
		        	iface.getGameboard().repaint();
		            break;
		            
		        /*case KeyEvent.VK_SPACE:
		            break;*/
        		}
	        } else if(i == 3) {
        		switch (keycode) {
	            
		        case KeyEvent.VK_K:
		        	
		        	if(iface.getGameboard().getPlayer(i).tryMove(current, "left", -1)) {
				    	current.setPosition(current.getPosition().x - 1, current.getPosition().y);
				    	iface.getGameboard().repaint();
			    	}
		            break;
		            
		        case KeyEvent.VK_M:
	
		        	if(iface.getGameboard().getPlayer(i).tryMove(current, "right", 1)) {
				    	current.setPosition(current.getPosition().x + 1, current.getPosition().y);
				    	iface.getGameboard().repaint();
			    	}
		            break;
		            
		        case KeyEvent.VK_L:
		        	
		        	if(iface.getGameboard().getPlayer(i).tryMove(current, "down", 1)) {
				    	current.setPosition(current.getPosition().x, current.getPosition().y + 1);
				    	iface.getGameboard().repaint();
			    	}
		            break;
		            
		        case KeyEvent.VK_O:
	
		        	while(iface.getGameboard().getPlayer(i).tryMove(current, "down", 1)) {
				    	current.setPosition(current.getPosition().x, current.getPosition().y + 1);
			    	}
		        	iface.getGameboard().repaint();
		        	iface.getGameboard().getPlayer(i).setTime(30);
		            break;
		        
		        case KeyEvent.VK_I:
		        	iface.getGameboard().getPlayer(i).rotate("left", current, false);
		        	iface.getGameboard().repaint();
		            break;
		            
		        case KeyEvent.VK_P:
		        	iface.getGameboard().getPlayer(i).rotate("right", current, false);
		        	iface.getGameboard().repaint();
		            break;
		            
		        /*case KeyEvent.VK_SPACE:
		            break;*/
        		}
	        }
        }
        
        switch (keycode) {
        
        	case KeyEvent.VK_ENTER:
        		boolean test = true;
        		for(int i = 0; i < iface.getGameboard().getPlayers().length; ++i) {
        			if(!iface.getGameboard().getPlayer(i).isOver() && !iface.getGameboard().getPlayer(i).isWin()) {
        				test = false;
        				break;
        			}
        		}
        		
        		if(test) iface.endGame();
        		break;
        }
    }
}
