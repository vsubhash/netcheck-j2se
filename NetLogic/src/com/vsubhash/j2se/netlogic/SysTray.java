package com.vsubhash.j2se.netlogic;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

@SuppressWarnings("rawtypes")
public class SysTray {
	String msAppName;
	boolean mbAvailable = false;
	SystemTray moSysTray;
	TrayIcon moSysTrayIcon;
	MvUtils moOink;
	Image[] moIconImages;
	Class moLoaderClass;
	
	public SysTray( Class asLoaderClass, 
									String asProgram, 
									String[] asIconResourceImages) {
		moLoaderClass = asLoaderClass;
		msAppName = asProgram;		
		moOink = new MvUtils(asProgram);
		moOink.mbIS_DEBUG = true;
		
		if (SystemTray.isSupported()) {
			
			try {
				// Load tray icon images
				int i, n;
				n = asIconResourceImages.length;
				moIconImages = new Image[n];
				moSysTray = SystemTray.getSystemTray();
				for (i = 0; i < n; i++) {
					moIconImages[i] = loadTrayImage(asIconResourceImages[i]);
				}
				
				if (moIconImages[0] != null) {
					moSysTrayIcon = new TrayIcon(moIconImages[0]);
					moSysTrayIcon.setToolTip(asProgram);
					moSysTray.add(moSysTrayIcon);
					moSysTrayIcon.addMouseListener(new MouseListenerHandler());
				} else {
					moOink.debug("Icon image not found.");
				}
				mbAvailable = true;				
			} catch (Exception err) {
				moOink.debug("Error in indicator panel acquisition - " + err.getMessage());
			}
		} else {
			moOink.debug("Indicator panel not supported.");			
		}
	}

	public void suffixTitle(String aTitle) {
		if (mbAvailable) {
			moSysTrayIcon.setToolTip(msAppName + " [" + aTitle + "]");
		}
	}
	
	public void showMessage(String asMessage) {
		if (mbAvailable) {
			moSysTrayIcon.displayMessage(msAppName, asMessage, MessageType.INFO);
		}
	}
	
	public void logMessage(String asMessage) {
		moOink.debug(asMessage);
	}
	
	
	public void changeIcon(int aiImageNumber) {
		moSysTrayIcon.setImage(moIconImages[aiImageNumber]);
	}
	
	// Loads image from bundled resource file
	Image loadTrayImage(String asIconImageName) {
		try {
			BufferedImage bi = 
					ImageIO.read(
							moLoaderClass.getClassLoader().getResource(asIconImageName));
			
			Image oReturnImage = 
					bi.getScaledInstance(
							moSysTray.getTrayIconSize().width, 
							moSysTray.getTrayIconSize().height, 
							Image.SCALE_SMOOTH);
			
			return(oReturnImage);
		} catch (Exception err) {
			moOink.debug("Error in image loading - " + err.getMessage());
			return(null);
		}
	}
	
	// Clicking the tray icon exits the app
	private class MouseListenerHandler implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.exit(0);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
