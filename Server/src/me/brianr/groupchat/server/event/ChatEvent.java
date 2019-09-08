package me.brianr.groupchat.server.event;

import me.brianr.groupchat.chat.Message;
import me.brianr.groupchat.users.User;

/**
 * The event that is called when a user chats.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class ChatEvent extends Event {
	
	private final User user;
	private final Message msg;
	
	
	/**
	 * Construct a new chat event.
	 * @param user the user who sent the message.
	 */
	public ChatEvent(final Message msg) {
		super();
		this.user = msg.getSender();
		this.msg = msg;
	}
	
	public ChatEvent(final Message msg, boolean isAsynchronous) {
		super(isAsynchronous);
		this.user = msg.getSender();
		this.msg = msg;
	}
	
	
	/**
	 * Get the user that sent the message.
	 * @return the user that sent the message.
	 */
	public User getUser() {
		return this.user;
	}
	
	/**
	 * Get the message that was sent.
	 * @return the message that was sent.
	 */
	public Message getMessage() { 
		return this.msg;
	}

	@Override
	public String getName() {
		return "ChatEvent";
	}
	
}
