package me.brianr.groupchat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import me.brianr.groupchat.chat.Message;
import me.brianr.groupchat.chat.Room;
import me.brianr.groupchat.server.filemanager.Logger;
import me.brianr.groupchat.users.Role;
import me.brianr.groupchat.users.User;

/**
 * The main GroupChatServer class.
 * <p>Handles new connections of users.  This is also the main class.</p>
 * @author Brian R
 * @version 1.0
 * @since 1.0
 */
public class GroupChatServer {
//	public static final HashMap<Integer, Room> ROOMS = new HashMap<Integer, Room>();
	public static Map<Room, Map<User, ChatThread>> userMap = new HashMap<Room, Map<User, ChatThread>>();
	public static final int PORT = 2586; // My favorite number.
	public static Room mainRoom = new Room("Main Room", 0);
	public static final User serverUser = new User("SERVER", "SERVER", 0, Role.SERVER);
	
	private ServerSocket serverSocket;
	
	private int port;
	private boolean running = false;
	
	
	/**
	 * Builds a server and waits for people to join the server.
	 * @param port the port that the server should run on.
	 */
	public GroupChatServer(int port) {
		this.port = port;
		this.serverSocket = null;
		
		// New SQL Connection that can connect to the database running locally (localhost/127.0.0.1).
		
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		try {
			// Start the server.
			serverSocket = new ServerSocket(port);
			Logger.log("Started a server on the port " + port);
			
			Logger.log("Awaiting client connection.");
			
			running = true;
			while (running && !serverSocket.isClosed()) {
				Socket socket = null;
				try {
					// Wait for a client to request connection.
					socket = serverSocket.accept();
				} catch (IOException e) {
					// TODO add stuff here for when exception caught.
				}
				
				output = new ObjectOutputStream(socket.getOutputStream());
				System.out.println("Output Set");
				
				input = new ObjectInputStream(socket.getInputStream());
				System.out.println("Input Set");
				
				User user;
				try {
					user = (User) input.readObject();
					
					if (userMap.get(mainRoom).containsKey(user)) {
						// Two users that are equal cannot be connected at once.
						
						socket.close();
						continue;
					}
					
					if (user.getRole() != Role.DEFAULT) { // Only anonymous users have the default role.
						user = User.getUserFromDatabase(user.getId());
					}
					
					if (userMap.get(mainRoom).containsKey(user)) {
						// Two users that are equal cannot be connected at once.
						
						socket.close();
						continue;
					}
					
					ChatThread tempChatThread = new ChatThread(socket, input, output);
					tempChatThread.setThreadUser(user);
					tempChatThread.start();
					
					userMap.get(mainRoom).put(user, tempChatThread);
					Logger.log(((user.getRole() == Role.DEFAULT)? user.getDisplayName() : user.getName()) + " has connected!");
					Logger.log("User connected from: " + socket.getInetAddress() + ":" + socket.getPort());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error: IOException.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error: User could not be loaded.");
		}
	}
	
	
	/**
	 * Broadcast a message, visible to all users connected to the server (regardless of the room they're in).
	 * @param msg the message to broadcast to all users.
	 */
	public void broadcastMessage(Message msg) throws IOException {
		for (Map.Entry<User, ChatThread> entry : GroupChatServer.userMap.get(GroupChatServer.mainRoom).entrySet()) {
//			entry.getValue().output.writeObject(msg);
//			entry.getValue().output.flush();
//			currentRoom.addMessage(msg);
			entry.getValue().sendMessage(msg);
		}
	}
	
//	/**
//	 * Broadcast a message to all users in a specific room.
//	 * @param msg
//	 * @param room
//	 */
//	public void broadcastMessage(Message msg, int room) {
//		
//	}
	
	/**
	 * End the server.
	 * @return true if the server closes successfully, otherwise returns false.
	 */
	public boolean close() throws IOException {
		serverSocket.close();
		running = false;
		
		return true;
	}
//	
//	/**
//	 * Open the server
//	 * @return true if the server opens successfully, otherwise returns false.
//	 */
//	public boolean open() {
//		// TODO do this.
//		return running && !serverSocket.isClosed();
//	}
	
	
	/**
	 * Get the port the server is running on.
	 * @return the port that the server is running on.
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Get if the server is running.
	 * @return true if the server is running, otherwise returns false.
	 */
	public boolean isRunning() {
		return this.running;
	}
	
	
	// TESETER
	public static void main(String[] args) {
		userMap.put(mainRoom, new HashMap<User, ChatThread>());
		
		new GroupChatServer(PORT);
	}
}
