package me.brianr.groupchat.chat;

import java.io.Serializable;

import me.brianr.groupchat.users.User;

/**
 * <strong>Message.java</strong>
 * <p>
 * Defines a message that can be sent.  A Message holds 
 * information such as the message text, the sender and the 
 * time stamp of the message.
 * </p>
 * @author reich
 * @version 1.0
 * @since 1.0
 */
public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2504362280723101337L;
	
	private String text;
	private final User sender;
	private final long timeStamp;
	
	
	/**
	 * Construct a message.
	 * @param sender the sender of the message.
	 * @param text the text of the message.
	 * @param timeStamp the timestamp of the message.
	 */
	public Message(final User sender, String text, final long timeStamp) {
		this.sender = sender;
		this.text = text;
		this.timeStamp = timeStamp;
	}
	
	/**
	 * Construct a message.
	 * @param text the text of the message.
	 * @param sender the sender of the message.
	 * @param timeStamp the timestamp of the message.
	 */
	public Message(final String text, User sender, final long timeStamp) {
		this.text = text;
		this.sender = sender;
		this.timeStamp = timeStamp;
	}
	
	
	/**
	 * Get the text of the message.
	 * @return the text.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Set the text of the message.
	 * @param text the text of the message.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Get the message sender.
	 * @return the message sender.
	 */
	public User getSender() {
		return this.sender;
	}
	
	/**
	 * Get the time the message was sent.
	 * @return the time the message was sent.
	 */
	public long getTimeStamp() {
		return this.timeStamp;
	}
	
	
	/**
	 * Replace special HTML characters with their HTML entity equivalents.
	 */
	public void htmlSpecialChars() {
		// TODO make check if ampersand is not being used as a HTML entity marker.
		text = htmlSpecialChars(text);
	}
	
	
	@Override
	public String toString() {
		String str;
		String color = "black";
		
		str = "<span style=\"color:" + sender.getRole().getColor() + ";\">" + sender.getRole().toString() + " <i>" + htmlSpecialChars(sender.getDisplayName()) + "</i></span>: " + htmlSpecialChars(text);
		return str;
	}
	
	
	/**
	 * Replace special HTML characters with their HTML entity equivalents.
	 * @return the formatted string.
	 */
	public static String htmlSpecialChars(String text) {
		// TODO make check if ampersand is not being used as a HTML entity marker.
		StringBuffer sb = new StringBuffer();
		char[] textChars = text.toCharArray();
		
		for (char c : textChars) {
			switch(c) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case ' ':
					sb.append("&nbsp;");
					break;
				case '\'':
					sb.append("&apos");
					break;
				case '\n':
					// No append here.
					break;
				default:
					sb.append(c);
					break;
			}
		}
		
		return sb.toString();
	}
	
}
