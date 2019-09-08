package me.brianr.groupchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import me.brianr.groupchat.chat.Message;
import me.brianr.groupchat.filemanager.FileManager;
import me.brianr.groupchat.filemanager.Logger;
import me.brianr.groupchat.users.Role;
import me.brianr.groupchat.users.User;

/**
 * A class that manages connections to the server, as well as any communications between the client and the server.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class GroupChatClient extends Thread {
	private volatile String serverAddress;
	private volatile int port;
	private volatile String localAddress;
	private volatile int localPort;
	
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	private volatile boolean ready;
	
	
	/**
	 * Constructs an instance of GroupChatClient and tests the connection to the server.
	 * @param serverAddress the address of the server to connect to.
	 * @param port the port of the server to connect to.
	 */
	public GroupChatClient(String serverAddress, int port) {
		this.serverAddress = serverAddress;
		this.port = port;
		
//		this.socket = null;
//		this.input = null;
//		this.output = null;
//		this.reader = null;
		
//		// Attempt to establish a connection to the server with the given address and port.
//		try {
//			socket = new Socket(serverAddress, port);
//			Logger.log("Connection Test Successful!");
//			
//			this.output = new ObjectOutputStream(socket.getOutputStream());
//			
//			
//		} catch (UnknownHostException e) {
//			JOptionPane.showMessageDialog(null, "Error: Unknown Host!", "Unknown Host", JOptionPane.ERROR_MESSAGE);
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "Exception occured while connecting to the server.", "Error", JOptionPane.ERROR_MESSAGE);
//			
//			e.printStackTrace();
//		} finally {
//			if (socket == null) {
//				return;
//			}
//			
//			try {
//				Logger.log("Attempting to disconnect...");
//				
//				output.close();
//				socket.close();
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, "An error occured when attempting to close the web socket.", "Error", JOptionPane.ERROR_MESSAGE);
//				
//				e.printStackTrace();
//			}
//		}
//		
//		this.socket = null;
//		this.input = null;
//		this.output = null;
//		this.reader = null;
		
	}
	
	
	/*
	 * Connect to the server and await instructions.
	 */
	@Override
	public void run() {
		try {
			socket = new Socket(serverAddress, port);
			Logger.log("Connected!");
			
			this.localAddress = socket.getLocalAddress().toString();
			this.localPort = socket.getLocalPort();
			
//			DataOutputStream uidSender = new DataOutputStream(socket.getOutputStream());
//			if (GroupChat.getInstance().userIsAnonymous) {
//				// id:name:displayName:role
//				User user = GroupChat.getInstance().getCurrentUser();
//				System.out.println(user.toString());
//				
//				String userString = user.getId() + ":" + user.getName() + ":" + user.getDisplayName().replaceAll(":", "") + ":" + user.getRole().name();
//				
//				System.out.println(userString);
//				
//				uidSender.flush();
//			} else {
//				uidSender.writeInt(GroupChat.getInstance().getCurrentUser().getId());
//				uidSender.flush();
//			}
			
			// Create the output stream.
			System.out.println("Assigning stuff");
			output = new ObjectOutputStream(socket.getOutputStream()); // Do this before doing input.  Otherwise, will not work.
			System.out.println("output assigned");
			
			// Create the input stream.
			System.out.println(socket.getInputStream());
			input = new ObjectInputStream(socket.getInputStream());
			System.out.println("input assigned");
			
//			reader = new BufferedReader(new InputStreamReader(System.in));	
			
			User tempUser = GroupChat.getInstance().getCurrentUser();
			if (GroupChat.getInstance().userIsAnonymous) {
				GroupChat.getInstance().setCurrentUser(new User(tempUser.getName() + ":" + socket.getLocalPort(), tempUser.getDisplayName(), tempUser.getId(), Role.DEFAULT));
			}
			
			
			// Send information about the user.
			System.out.println(GroupChat.getInstance().getCurrentUser());
			output.writeObject(GroupChat.getInstance().getCurrentUser());
			output.flush();
			
			System.out.println("All assigned.  Starting listen.");
			ready = true;
			while (ready) {
				try {
					Object receivedObject = input.readObject();
					
					if (receivedObject instanceof ServerInstruction) {
						ServerInstruction receivedInstruction = (ServerInstruction) receivedObject;
						// TODO server instructions.
					} else if (receivedObject instanceof Message) {
						Message receivedMessage = (Message) receivedObject;
						FileManager fm = new FileManager("files/room-00.yml");
						
						switch (receivedMessage.getSender().getRole()) {
							case SERVER:
							case ADMIN:
								break;
							default:
								receivedMessage.htmlSpecialChars();
								break;
						}
						
						fm.saveMessage(receivedMessage);
						GroupChat.window.updateMessages();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Error. Sent class could not be found.");
					e.printStackTrace();
				}
				
				GroupChat.window.updateMessages();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error.  IOException");
			e.printStackTrace();
		}
		
		return;
	}
	
	/**
	 * Safely close the connection to the server.
	 * @return whether or not the connection was closed successfully.
	 */
	public synchronized boolean close() {
		// Stop from waiting for messages.
		this.ready = false;

		try {
			// Close the input.
			if (input != null) {
				input.close();
			}
			
			// Close the output.
			if (output != null) {
				output.close();
			}
			
			// Close the socket.  Disconnect from the server.
			if (socket != null) {
				socket.close();
			}
			
			try {
				join(); // Wait for the thread to die.
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			return true;
		} catch (IOException e) {
			// e.printStackTrace();
		}
		
		// Thread did not close successfully.
		return false;
	}
	
	
	/**
	 * Get the address of the server that the client is connected to.
	 * @return the address of the server.
	 */
	public String getAddress() {
		return this.serverAddress;
	}
	
	/**
	 * Get the port of the server that the client is connected to.
	 * @return the port of the server.
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Write an object to the output and flush it.
	 * @param obj the object to send.
	 */
	public void sendObject(Object obj) {
		
	}
	
	/**
	 * Write a message to the output and flush it.
	 * @param msg the message to send.
	 */
	public void sendMessage(Message msg) {
		try {
//			output.writeBytes(msg.toString() + "\n\r");
//			output.flush();
			output.writeObject(msg);
			output.flush();
		} catch (IOException e) {
			System.out.println("Error: IOException");
			e.printStackTrace();
		}
		
		GroupChat.window.updateMessages();
	}
	
	/**
	 * Check if the server is ready to send and receive messages.
	 * @return whether or not the connection is ready for sending and receiving.
	 */
	public boolean isReady() {
		return this.ready;
	}
	
	/**
	 * Disconnect from the server and join the thread.
	 * @deprecated The method does not close the connection to the server safely.
	 */
	public synchronized void end() {
		try {
			this.ready = false;
			this.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Get the user's local address.
	 * @return the address.
	 */
	public String getLocalAddress() {
		return socket.getLocalAddress().toString();
	}
	
	/**
	 * Get the user's port.
	 * @return the port.
	 */
	public int getLocalPort() {
		return this.socket.getLocalPort();
	}
	
	
	// TESTER
	public static void main(String[] args) {		
		GroupChatClient gcc = new GroupChatClient("127.0.0.1", 2586);
		gcc.start();
		
		User tempUser = new User("brain", "TheBigBrain", Role.ADMIN);
		Date d = new Date();
		
		Message m = new Message(tempUser, "Banana", d.getTime());
		
		while (!gcc.isReady()) {} // THIS IS BAD.  DON'T EVER DO THIS.  EVER.																																																																									Banana
		
		gcc.sendMessage(m);
		
	}
}
