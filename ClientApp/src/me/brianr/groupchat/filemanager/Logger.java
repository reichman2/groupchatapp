package me.brianr.groupchat.filemanager;

/**
 * Used to keep a log of all important and debug messages (not chat messages).
 * <p>
 * The original purpose of this class was to save important messages, but because this class, 
 * but this class ended up not getting used for that. <br />
 * <strong>Saving logs to files is not implemented.</strong>
 * </p>
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class Logger {
//	public static File logFile;
	
	// Static class should not be constructed.
	private Logger() {}
	
	
	/**
	 * Log a message.
	 * @param message the message to log.
	 */
	public static void log(String message) {
		System.out.println(message);
	}
	
	public static void warn(String message) {
		
	}
	
	public static void err(String message) {
		
	}
}
