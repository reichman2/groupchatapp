package me.brianr.groupchat;

import javax.swing.SwingUtilities;

import me.brianr.groupchat.filemanager.Configuration;
import me.brianr.groupchat.users.User;
import me.brianr.groupchat.window.LoginWindow;
import me.brianr.groupchat.window.Window;

/**
 * The Main GroupChat Application class.
 * <p>
 * This class links all of the classes that run the client together.
 * </p>
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class GroupChat {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	private static GroupChatClient clientConnection;
	private Configuration config;
	private static GroupChat instance;
	
	public static Window window;
	
	private User currentUser;
	
	public volatile boolean userIsAnonymous;
	public String dispName;
	
	
	/**
	 * Construct an instance of a group chat client.
	 */
	public GroupChat() {
		this.config = Configuration.constructFromYamlFile("files/config.yml");
		
		// Assign the instance to this.
		instance = this;
		
		// Have the user log in.
		login();
	}
	
	
	/**
	 * Main method. Program starts here.
	 * @param args
	 */
	public static void main(String[] args) {
		new GroupChat();
	}
	
	
	/**
	 * Open a login window thast assigns the user.
	 */
	public void login() {
		this.currentUser = null;
		            
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.setVisible(true);
	}
	
	/**
	 * Check if the user is logged in.
	 * @return true if the user is logged in, otherwise false.
	 */
	public boolean isLoggedIn() {
		return currentUser != null;
	}
	
	/**
	 * Open the chat window when the user is logged in and ready to start chatting.
	 */
	public void openChatWindow() {
		SwingUtilities.invokeLater(() -> {
			window = new Window();
			window.setVisible(true);
		});
		
		clientConnection = new GroupChatClient(config.getServerAddress(), Integer.parseInt(config.getServerPort()));
		clientConnection.start();
	}
	
	
	/**
	 * Get the user that the user is signed in as.
	 * @return
	 */
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	/**
	 * Set the user.
	 * @param user the user to set the user to.
	 */
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
	
	/**
	 * Get the main instance of this class.
	 * @return the instance of this class.
	 */
	public static GroupChat getInstance() {
		return instance;
	}
	
	/**
	 * The the GroupChatClient class that the connection to the server is managed by.
	 * @return the instance of GroupChatClient
	 */
	public static GroupChatClient getClientConnection() {
		return clientConnection;
	}
	
	/**
	 * Get the configuration.
	 * @return the configuration.
	 */
	public Configuration getConfig() {
		return this.config;
	}
}
