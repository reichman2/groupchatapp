package me.brianr.groupchat.users;

/**
 * The state of users.
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
