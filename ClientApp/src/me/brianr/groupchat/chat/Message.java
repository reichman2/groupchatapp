package me.brianr.groupchat.chat;

import java.io.Serializable;

import me.brianr.groupchat.users.Role;
import me.brianr.groupchat.users.User;

/**
 * <strong>Message.java</strong>
 * <p>Defines a message that can be sent.  A Message holds information such as the message text, the sender and the time stamp of the message.
 * @author Brian Reich
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
	 * Construct an instance of message.
	 * @param text the message's text. This is the actual message part.
	 * @param sender the sender of the message.
	 * @param timeStamp when the message is sent.
	 */
	public Message(final User sender, final String text, final long timeStamp) {
		this.sender = sender;
		this.text = text;
		this.timeStamp = timeStamp;
	}
	
	/**
	 * Construct an instance of message.
	 * @param text the message's text. This is the actual message part.
	 * @param sender the sender of the message.
	 * @param timeStamp when the message is sent.
	 */
	public Message(final String text, final User sender, final long timeStamp) {
		this.text = text;
		this.sender = sender;
		this.timeStamp = timeStamp;
	}
	
	
	/**
	 * Get the text of the message.
	 * @return message's text.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Get the user that the message was sent by.
	 * @return the user that sent the message.
	 */
	public User getSender() {
		return this.sender;
	}
	
	/**
	 * Get the timestamp recorded for this message.
	 * @return the timestamp of this message.
	 */
	public long getTimeStamp() {
		return this.timeStamp;
	}
	
	/**
	 * Replace special HTML characters with their HTML entity equivalents.
	 */
	public void htmlSpecialChars() {
		// TODO make check if ampersand is not being used as a HTML entity marker.
//		text.replaceAll("&", "&amp;");
//		text.replaceAll("\"", "&quot;");
//		text.replaceAll("'", "&#039;");
//		text.replaceAll("<", "&lt;");
//		text.replaceAll(">", "&gt;");
		
		text = htmlSpecialChars(text);
	}
	
	
	@Override
	public String toString() {
		String str;
		String color = "black";
//		switch (sender.getRole()) {
//			case SERVER:
//				color = "red";
//				break;
//			case ADMIN:
//				color = "#42f4c5";
//				break;
//			case MODERATOR:
//				color = "#5f41f4";
//				break;
//			case USER:
//				color = "#333333";
//				break;
//			case DEFAULT:
//				color = "black";
//				break;
//			case MUTED:
//				color = "rgba(0, 0, 0, 0)";
//				break;
//			case BANNED:
//				color = "rgba(0, 0, 0, 0)";
//				break;
//			default:
//				color = "black";
//				break;
//		}
		
		if (sender.getRole() != Role.ADMIN || sender.getRole() != Role.SERVER)
			htmlSpecialChars();
		
		str = "<span class=\"line\" style=\"font-family: Arial;\"><span style=\"color:" + sender.getRole().getColor() + ";\">" + sender.getRole().toString() + " <i>" + htmlSpecialChars(sender.getDisplayName()) + "</i></span>: " + htmlSpecialChars(text) + "</span>";
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
//				case '&':
//					sb.append("&amp;"); // &amp;
//					break;
				case '<':
					sb.append("&lt;"); // &lt;
					break;
				case '>':
					sb.append("&gt;"); // &gt;
					break;
				case '"':
					sb.append("&quot;"); // &quot;
					break;
				case ' ':
					sb.append("&nbsp;"); // &nbsp;
					break;
				case '\'':
					sb.append("&apos"); // &apos;
					break;
				case '\n':
					sb.append("<br />"); // <br />
					break;
				default:
					sb.append(c);
					break;
			}
		}
		
//		for (char c : textChars) {
//			switch(c) {
//				case '&':
//					sb.append("\u0026"); // &amp;
//					break;
//				case '<':
//					sb.append("\u003C"); // &lt;
//					break;
//				case '>':
//					sb.append("\u003E"); // &gt;
//					break;
//				case '"':
//					sb.append("\\u0022"); // &quot;
//					break;
//				case ' ':
//					sb.append("\u0020"); // &nbsp;
//					break;
//				case '\'':
//					sb.append("\u0027"); // &apos;
//					break;
//				case '\n':
//					sb.append("<br />"); // <br />
//					break;
//				default:
//					sb.append(c);
//					break;
//			}
//		}
		
		
		return sb.toString();
	}
	
	
}
