package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.net.ConnectException;
import java.net.Socket;
import static util.IOUtil.isNumber;

public class Const {
	
	
	public static final String PROPERTIES_FILE = "Properties/client.properties";
	public static final double WIDTH;
	public static final double HEIGHT;
	public static final int SERVER_PORT;
	public static Integer sessionID;
	
	static {
		
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(PROPERTIES_FILE));//read in from properties file
			
			//get info from properties file
			WIDTH = Integer.parseInt(props.getProperty("window.width"));
			HEIGHT = Integer.parseInt(props.getProperty("window.height"));
			SERVER_PORT = Integer.parseInt(props.getProperty("server.port"));
			
			//Test connection with server
			/*
			 * 
			 * Socket test = new Socket(props.getProperty("server.ip"),SERVER_PORT);
			test.close();
			
			String sessID = props.getProperty("server.sessionid");
			
			//If the client has a stored sessionID then use it, otherwise set sessionID to null
			if (sessID == null || sessID.trim().length() == 0 || !isNumber(sessID)) {
				sessionID = null;
			} else {
				sessionID = Integer.parseInt(sessID);
			}
			
			*/
			
		} catch (FileNotFoundException f) {
			throw new StartUpException("Could not find program data on start up.");
		} catch (NumberFormatException n) {
			throw new StartUpException("Encountered an error loading information on start up.");
		} catch (ConnectException c) {
			throw new StartUpException("Could not connect to server.");
		} catch (IOException e) {
			throw new StartUpException("An unknown error occurred.");
		}
	}
	
	private Const() {
		
	}
}
