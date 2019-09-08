package me.brianr.groupchat.users;

public enum Role {
	SERVER("#bb36f4"), // Only the server can possess this role.
	ADMIN("#f44336"), // Only for special users.  Allows full access to all commands and bypass any and all filters.
	MODERATOR("#5f41f4"), // Moderates the chat as needed.  Access to moderation commands like mute and ban.
	VIP("#00cce2"), // For users who are a bit better than the average user.  Allows access to fun commands.
	USER("#333333"), // For users who are trusted users.  Allows access to basic commands.
	DEFAULT("#000000"), // For newly registered users.  Allows access to the most simple commands, if there even are any.
	UNREGISTERED("#777777"), // For users who join the chatroom unregistered.  No access to any commands.
	MUTED("rgba(0, 0, 0, 0)"), // For muted users.  Opacity: 0
	BANNED("rgba(0, 0, 0, 0)"); // Boo hoo Opacity: 0
	
	
	private String color;
	
	
	/**
	 * Construct a Role enum.
	 * @param color the CSS-compatable color of the chat of the user with the role.
	 */
	private Role(String color) {
		this.color = color;
	}
	
	
	/**
	 * Returns the CSS-compatable color specified at construction.
	 * @return the color.
	 */
	public String getColor() {
		return this.color;
	}
	
	@Override
	public String toString() {
		String str = "[" + name() + "]";
		return str;
	}
	
	
}
