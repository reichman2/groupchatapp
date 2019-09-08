package me.brianr.groupchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import me.brianr.groupchat.chat.Message;

/**
 * A class to handle the sending of messages and commands by the server.
 * @author Brian R
 * @version 1.0
 * @since 1.1
 */
public class ServerMessageThread extends Thread {
	
	private GroupChatServer server;
	
	
	/**
	 * Construct a ServerMessageThread that allows the server to send messages to all users.
	 * @param server the instance of the server class.
	 */
	public ServerMessageThread(GroupChatServer server) {
		this.server = server;
	}
	
	@Override
	public void run() {		
		while (server.isRunning()) {
			try {
				String str = Reader.readLine("> ");
				if (str.startsWith("!")) {
					// TODO process command.
				} else {
					server.broadcastMessage(new Message(str, GroupChatServer.serverUser, new Date().getTime()));
				}
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
		
	}
	
	
	// TESTER
	public static void main(String[] args) {
		try {
			System.out.println(Reader.readLine("> "));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

/** 
 * Read input from console.
 * @author Brian R
 * @version 1.0
 * @since 1.0
 */
class Reader {
	
	/**
	 * Read a line from the console.
	 * @return
	 * @throws IOException
	 */
	public static String readLine() throws IOException {
		// TODO add close.
		InputStreamReader io = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(io);
		String str = reader.readLine();
		return str;
	}
	
	/**
	 * Read a line from the console, but first send a prompt.
	 * @param msg the prompt to send before requesting input.
	 * @return the input received.
	 * @throws IOException
	 */
	public static String readLine(String msg) throws IOException {
		System.out.print(msg);
		return readLine();
	}
}