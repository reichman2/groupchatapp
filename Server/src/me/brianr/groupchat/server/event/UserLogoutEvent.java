package me.brianr.groupchat.server.event;

import me.brianr.groupchat.users.User;

/**
 * The event that's triggered when a user logs out.
 * <p><strong>This class is currently not implemented.</strong></p>
 * @author Brian Reich
 * @version 1.0
 */
public class UserLogoutEvent extends Event {
	
	private final User user;
	
	
	/**
	 * Construct a UserLogoutEvent.
	 * @param user the user that logged out.
	 */
	public UserLogoutEvent(final User user) {
		super();
		this.user = user;
	}
	
	/**
	 * Construct a UserLogoutEvent.
	 * @param user the user that logged out.
	 * @param isAsynchronous whether or not the event is asynchronous.
	 */
	public UserLogoutEvent(final User user, boolean isAsyncronous) {
		super(isAsyncronous);
		this.user = user;
	}
	
	
	/**
	 * Get the user that logged out.
	 * @return the user that logged out.
	 */
	public User getUser() {
		return this.user;
	}
	

	@Override
	public String getName() {
		return "UserLogoutEvent";
	}

}
