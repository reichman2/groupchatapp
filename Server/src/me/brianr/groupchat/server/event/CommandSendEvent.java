package me.brianr.groupchat.server.event;

import me.brianr.groupchat.users.User;

/**
 * The event that is called when a command is sent.
 * <p><strong>This class is not implemented.</strong></p>
 * @author Brian Reich
 * @version 1.0
 */
public class CommandSendEvent extends Event {
	
	private final User user;
	
	
	/**
	 * Construct a new CommandSendEvent
	 * @param user the user who sent the command
	 */
	public CommandSendEvent(final User user) {
		super();
		this.user = user;
	}
	
	/**
	 * Construct a new CommandSendEvent
	 * @param user the user who sent the command
	 * @param isAsynchronous set whether or not the event is asynchronous.
	 */
	public CommandSendEvent(final User user, boolean isAsynchronous) {
		super(isAsynchronous);
		this.user = user;
	}
	
	
	/**
	 * Get the user that sent the command.
	 * @return the command sender.
	 */
	public User getUser() {
		return this.user;
	}
	

	@Override
	public String getName() {
		return "CommandSendEvent";
	}
	
}
