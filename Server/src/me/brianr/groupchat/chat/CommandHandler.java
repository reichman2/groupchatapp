package me.brianr.groupchat.chat;

import me.brianr.groupchat.server.GroupChatServer;
import me.brianr.groupchat.server.event.EventHandler;
import me.brianr.groupchat.server.event.Listener;
import me.brianr.groupchat.users.User;

/**
 * A class to handle the execution of commands.
 * @author Brian Reich
 * @version 1.0
 */
public class CommandHandler implements Listener {
	
	private GroupChatServer server;
	
	
	/**
	 * Construct a command handler.
	 * @param server the instance of the GroupChatServer class.
	 */
	private CommandHandler(GroupChatServer server) {
		this.server = server;
	}
	
	
	/**
	 * 
	 * @param cmd
	 * @return
	 */
	@EventHandler
	public static boolean onCommand(Command cmd) {
		String name = cmd.getName();
		String[] args = cmd.getArgs();
		User sender = cmd.getSender();
		
		if (cmd.getName().equalsIgnoreCase("clear")) {
			switch (sender.getRole()) {
				case SERVER:
				case ADMIN:
				case MODERATOR:
				case VIP:
					
					break;
				default:
					
					break;
			}
		} else if (cmd.getName().equalsIgnoreCase("kick")) {
			
		} else if (cmd.getName().equalsIgnoreCase("ban")) {
			
		} else if (cmd.getName().equalsIgnoreCase("fakemsg")) {
			
		} else if (cmd.getName().equalsIgnoreCase("broadcast")) {
			
		} else if (cmd.getName().equalsIgnoreCase("pm")){
			
		} else {
			
		}
		
		return false;
	}
	
	
	private static void clearChat() {
		// TODO Clear the chat.
	}
	
	private static boolean kickUser(String name) {
		// TODO Kick a user
		return false;
	}
	
	private static boolean banUser(String name) {
		// TODO Ban a user.
		return false;
	}
	
	private static boolean fakeMessage(String name, String message) {
		// TODO Send a message pretending to be another user.
		return false;
	}
	
	private static boolean broadcast(String message) {
		// TODO Send a message as the server.
		return false;
	}
	
	private static boolean pm(Message msg, User recipient) {
		// TODO Send a private message to another user.
		return false;
	}
	
	
}
