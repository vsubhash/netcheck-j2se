  
		package com.vsubhash.j2se.netlogic;

		import java.net.HttpURLConnection;
		import java.net.URL;
		import java.util.Timer;
		import java.util.TimerTask;

		public class NetCheck {
			static SysTray moSysTray;
	
			static Timer moTimer;
			static int moINTERVAL = 20;
			static String msURL = "https://www.google.com/";
			static boolean mbIsConnected = false;

			public static void main(String[] args) {
		
				// Add icon to notification panel and load additional images
			 	moSysTray = new SysTray(NetCheck.class, 
			 							"NetCheck", 
			 							new String[] { "network-checking.png",
			 														 "network-connected.png",
			 														 "network-disconnected.png"});

			 	moTimer = new Timer(); 

			 	// Check the net connection every 10 seconds
			 	moSysTray.suffixTitle("Checking...");
			 	moTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (checkUrl(msURL)) {
							if (!mbIsConnected) {
								moSysTray.showMessage("Connected");
							}
							moSysTray.changeIcon(1); /* "network-connected.png" */
							moSysTray.suffixTitle("Connected");
							mbIsConnected = true;
						} else {
							if (mbIsConnected) {
								moSysTray.showMessage("Disconnected");
							}
							moSysTray.changeIcon(2); /* "network-disconnected.png" */
							moSysTray.suffixTitle("Disconnected");
							mbIsConnected = false;
					
						}
					}
				}, 1000, moINTERVAL*1000);

			}
	
			static boolean checkUrl(String asUrl) {
				boolean bReturn = false;
				URL oUrl;
				HttpURLConnection oUrlConnection;
				 
				try {
					oUrl = new URL(asUrl);
					oUrlConnection = (HttpURLConnection) oUrl.openConnection();			
					oUrlConnection.setConnectTimeout(3*1000);
					oUrlConnection.setReadTimeout(3*1000);
					// Make a HEAD request instead of a GET so that 
					// the entire page is not downloaded
					oUrlConnection.setRequestMethod("HEAD");
			
					moSysTray.logMessage("Checking...");
					oUrlConnection.connect();
					String sContentType = oUrlConnection.getContentType();
			
					if (sContentType != null) {
						moSysTray.logMessage("Found " + sContentType);
						bReturn = true;
					} else {
						moSysTray.logMessage("Found " + null);
					}
					oUrlConnection.disconnect();			
				} catch (Exception e) {
					moSysTray.logMessage("Connection error " + e.getMessage());
					//e.printStackTrace();
				}
				
				return(bReturn);
			}
		}  	