package me.brianr.groupchat.users;

/**
 * The state of a user.
 * <p>This class doesn't do much right now. It can be ignored.</p>
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public enum UserState {
	ONLINE(),
	AWAY(),
	HIDDEN(),
	DND(),
	OFFLINE();
}
