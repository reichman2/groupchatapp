package me.brianr.groupchat.server.event;

import me.brianr.groupchat.users.User;

/**
 * The event that's triggered when a user logs in.
 * <p><strong>This class is currently not implemented.</strong></p>
 * @author Brian Reich
 * @version 1.0
 */
public class UserLoginEvent extends Event {
	
	private final User user;
	
	
	/**
	 * Construct a UserLoginEvent.
	 * @param user the user that logged in.
	 */
	public UserLoginEvent(final User user) {
		super();
		this.user = user;
	}
	
	/**
	 * Construct a UserLoginEvent.
	 * @param user the user that logged in.
	 * @param isAsynchronous whether or not the event is asynchronous.
	 */
	public UserLoginEvent(final User user, boolean isAsynchronous) {
		super(isAsynchronous);
		this.user = user;
	}
	

	@Override
	public String getName() {
		return "UserLoginEvent";
	}
	
	
	/**
	 * Get the user that logged in.
	 * @return the user that logged in.
	 */
	public User getUser() {
		return this.user;
	}

}
