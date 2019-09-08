package me.brianr.groupchat;

/**
 * A class that carries out an instruction from the server.
 * <p><strong>(Not Implemented)</strong></p>
 * @author Brian Reich
 * @version 1.0
 */
public class ServerInstruction {
	
	/**
	 * Runs the method from given from the server.
	 * <p>Usage: ServerInstruction(ServerInstruction.RUN_METHOD, new String[] { "methodName", "methodArg0", "methodArg1" }</p>
	 */
	public static final int RUN_METHOD = 0;
	
	/**
	 * Get a user from the server
	 */
	public static final int GET_USER = 1;
	
	/**
	 * Get an address.
	 */
	public static final int GET_ADDRESS = 2;
	
	/**
	 * Log something.
	 */
	public static final int LOG = 3;
	
	/**
	 * Clear the chat.
	 */
	public static final int CLEAR_CHAT = 4;
	
	private int type;
	private String[] args;
	
	
	/**
	 * Construct an instruction.
	 * @param type the type of instruction.
	 * @param args the arguments of the instruction.
	 */
	public ServerInstruction(int type, String[] args) {
		this.type = type;
		this.args = args;
	}
}
