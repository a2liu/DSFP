package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import server.terminal.TerminalCommands;
import users.User;

/**
 * A basic RequestHandler to handle the communication between the client and server.
 * @author aliu
 *
 */
public abstract class BaseRequestHandler {

	private TerminalCommands terminal;
	private volatile boolean running;
	private final Server server;
	private User user;
	private boolean root;
	
	public BaseRequestHandler(Server server) {
		terminal = new TerminalCommands(this);
		this.server = server;
		root = false;
	}
	
	protected void setCommands(TerminalCommands commands) {
		this.terminal = commands;
	}
	
	protected TerminalCommands getCommands() {
		return terminal;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Checks if currently logged in
	 * @return true if logged in
	 */
	public boolean loggedIn() {
		return !(user == null || user.getID() == null);
	}
	
	public void setRoot(boolean root) {
		this.root = root;
	}
	
	/**
	 * checks if currently a server admin or 'root user'
	 * @return true if an admin
	 */
	public boolean isRoot() {
		return root;
	}
	
	/**
	 * start the requestHandler
	 */
	public void start() {
		running = true;
	}
	
	/**
	 * quit the requestHandler
	 */
	public void quit() {
		running = false;
	}
	
	/**
	 * start the server. Used by admins.
	 */
	protected void startServer() {
		server.goOnline();
	}
	
	/**
	 * stop the server. Used by admins.
	 */
	protected void stopServer() {
		server.goOffline();
	}
	
	/**
	 * gets the server reference.
	 * @return server
	 */
	public Server getServer() {
		return server;
	}
	
	/**
	 * checks whether this should still be running
	 * @return true if it's running
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * log out.
	 */
	public void logout() {
		user = null;
	}
}
