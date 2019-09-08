package me.brianr.groupchat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import me.brianr.groupchat.chat.ChatFilter;
import me.brianr.groupchat.chat.Message;
import me.brianr.groupchat.chat.Room;
import me.brianr.groupchat.server.event.ChatEvent;
import me.brianr.groupchat.server.event.Event;
import me.brianr.groupchat.server.event.EventManager;
import me.brianr.groupchat.users.Role;
import me.brianr.groupchat.users.User;

/**
 * A ChatThread is what the user communicates with.
 * <p>
 * ChatThread handles all of the communication between a specific user and the server.
 * This class is in charge of making sure messages get distributed to all users,
 * making sure that a user receives messages, and makes sure that a user stays connected.
 * </p>
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class ChatThread extends Thread {
	protected Socket socket;
	private User threadUser;
	private Room currentRoom;
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	
//	public ChatThread(Socket socket) {
//		this.socket = socket;
//		this.input = null;
//		this.output = null;
//		this.reader = null;
//		
//		this.currentRoom = GroupChatServer.mainRoom;
//	}
	
	/**
	 * Construct a chat thread.
	 * @param socket the socket of the server.
	 * @param input the ObjectInputStream that is used to receive input from the client.
	 * @param output the ObjectOutputStream that is used to send output to the client.
	 */
	public ChatThread(Socket socket, ObjectInputStream input, ObjectOutputStream output) {
		this.socket = socket;
		this.output = output;
		this.input = input;
		
		this.currentRoom = GroupChatServer.mainRoom;
	}
	
	
	@Override
	public void run() {
//		if (input != null || output != null) {
//			try {
//				output = new ObjectOutputStream(socket.getOutputStream());
//				input = new ObjectInputStream(socket.getInputStream());
//				reader = new BufferedReader(new InputStreamReader(input));
//			} catch (IOException e) {
//				e.printStackTrace();
//				return;
//			}
//		}
		
//		String ln;
		
		System.out.println(socket.isClosed());
		while (!socket.isClosed()) {
			try {
//				ln = reader.readLine();
//				System.out.println(ln);
//				if (ln != null && ln.equalsIgnoreCase("!exit")) {
//					socket.close();
//					return;
//				}
				
//				output.writeBytes(ln + "\n\r");
//				output.flush();
				
				Object receivedObject = input.readObject();
				if (receivedObject instanceof Message) {
					Message receivedMessage = (Message) receivedObject;
//					User messageSender = receivedMessage.getSender();
					
					if (!receivedMessage.getSender().equals(threadUser))
						continue;
					
					Event event = new ChatEvent(receivedMessage);
					EventManager.trigger(event);
					
					if (!event.isCancelled()) {
						if (threadUser.getRole() != Role.BANNED && threadUser.getRole() != Role.MUTED) {
							distributeMessage(receivedMessage);
						}
					}
				}
				
//				System.out.println(msg);
			} catch (IOException e) {
				// User disconnected.
				System.out.println(threadUser.getName() + " disconnected.");
				distributeMessage(new Message(GroupChatServer.serverUser, "<span style=\"color: #FFD400\">" + threadUser.getDisplayName() + " disconnected</span>", new Date().getTime()));
				try {
					socket.close();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				GroupChatServer.userMap.get(GroupChatServer.mainRoom).remove(threadUser);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		System.out.println("WHILE LOOP ENDED -- SOCKET CLOSED");
	}
	
	/**
	 * Distributes a message to all users in the room.
	 * @param msg the message to distribute.
	 * @return true if the distribution was successful, otherwise returns false.
	 */
	public boolean distributeMessage(Message msg) {
		
		if (msg.getSender().getRole() != Role.ADMIN || msg.getSender().getRole() != Role.SERVER) {
			msg.setText(ChatFilter.filter(msg.getText()));
//			System.out.println(msg.getText());
		}
		
		if (GroupChatServer.userMap.size() == 0)
			return false;
		
		System.out.println(GroupChatServer.userMap.get(GroupChatServer.mainRoom));
		
		for (Map.Entry<User, ChatThread> entry : GroupChatServer.userMap.get(GroupChatServer.mainRoom).entrySet()) {
			try {
//				entry.getValue().output.writeObject(msg);
//				entry.getValue().output.flush();
				entry.getValue().sendMessage(msg);
				currentRoom.addMessage(msg);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Send a message to the user connected to the chat thread.
	 * @param msg the message to send to the user connected to the chat thread.
	 * @throws IOException 
	 */
	public void sendMessage(Message msg) throws IOException {
		output.writeObject(msg);
		output.flush();
	}
	
	/**
	 * Get the user associated with this thread.
	 * @return the thread user.
	 */
	public User getThreadUser() {
		return this.threadUser;
	}
	
	/**
	 * Set the user associated with this thread.
	 * @param threadUser the user to set the thread user to.
	 */
	public void setThreadUser(User threadUser) {
		this.threadUser = threadUser;
	}
	
}
