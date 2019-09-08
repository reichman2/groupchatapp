package me.brianr.groupchat.filemanager;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import me.brianr.groupchat.chat.Message;
import me.brianr.groupchat.users.Role;
import me.brianr.groupchat.users.User;


/**
 * Manages files.  Allows the user to read and save to files.
 * @author Brian R
 * @version 1.0
 * @since 1.0
 */
public class FileManager {
	
	private String filePath;
	
	
	/**
	 * Construct a FileManager to read and edit YAML files.
	 * @param filePath the path of the file to manage. Don't forget the file extension.
	 */
	public FileManager(String filePath) {
		this.filePath = filePath;
	}
	
	
	/**
	 * Get a list lines in the file.  Each element in the file represents one line in the file.
	 * @return the list of lines in the file.
	 */
	public List<String> readFile() {
		// Create a new buffered reader to read the file and set it to null for checking purposes and to be able to close it.
		BufferedReader reader = null;
		try {
			// Set the reader to read the file.
			reader = new BufferedReader(new FileReader(filePath));
			List<String> lines = new ArrayList<String>(); // A list to hold the lines in the file.  Each element is a line in the file.
			
			// Read the file line by line and add it to the list "lines"
			String ln;
			while ((ln = reader.readLine()) != null) {
				lines.add(ln);
			}
			
			// If successful, return the lines list.
			return lines;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			if (reader != null) { // Check to make sure the reader was opened.
				try {
					reader.close(); // If so, close it.
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Read a line in the file.  The first line is 0.
	 * @param line the line number to read.
	 * @return the String data from that line in the file.  Returns null if something doesn't go right.
	 */
	public String readLine(int lineNumber) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			
			return reader.lines().toArray()[lineNumber].toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null; // This is reached when something goes not correctly enough right way.
	}
	
	/**
	 * Get a list of messages from a file holding messages only.
	 * @return a list of messages from the FileManager's given file.
	 */
	public List<Message> getMessages() {
		// I made this method and got it right first try ;p
		
		List<Message> msgs = new ArrayList<Message>();
		Map<String, Object> mappedYml = null;
		Yaml yaml = new Yaml();
		
		FileInputStream ioStream = null;
		try {
			ioStream = new FileInputStream(filePath);
			mappedYml = (Map<String, Object>) yaml.load(ioStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ioStream != null) {
				try {
					ioStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
//		System.out.println(mappedYml);
		
		if (mappedYml != null) {
			List<Map<String, Object>> mapList = ((List<Map<String, Object>>) mappedYml.get("messages"));
			
			// Construct a message with the information given.
			for (Map<String, Object> m : mapList) {
				// Get the text of the message.
				String text = m.get("text").toString();
				long timeStamp = Long.parseLong(m.get("timeStamp").toString());
				
				Map<String, Object> senderMap = ((Map<String, Object>) m.get("sender"));
				
				// Get the information of the sender of the message.
				String senderName = senderMap.get("name").toString(); // Sender name
				String senderDispName = senderMap.get("displayName").toString(); // Sender displayName
				long id = Long.parseLong(senderMap.get("id").toString()); // sender id
				
				// Get the sender's role.
				Role role = Role.valueOf(senderMap.get("role").toString()); // Sender role.
				if (role == null) { // If the role is null (invalid) make the sender default.
					role = Role.DEFAULT;
				}
				
				// Get the sender's special color if they're special.
				try {
					Color color = Color.getColor(senderMap.get("color").toString());
					if (color == null) { // If the color is null (invalid) set the color to black.
						color = Color.BLACK;
					}
				} catch (Exception e) {
					// Its okay.  We don't really use the color field in the User class anyway.
				}
				
				// Construct the message and user.
				Message msg = new Message(text, new User(senderName, senderDispName, id, role), timeStamp);
				
				msgs.add(msg); // Add the message to the list of messages.
			}
		}
		
//		System.out.println(mappedYml);
//		System.out.println(mappedYml.size());
//		System.out.println(((Map) ((List)mappedYml.get("messages")).get(0)).get("text"));
		
		// Return the messages.
		return msgs;
	}
	
	/**
	 * Dumps all the messages in the list into the file.
	 * @param messages the list of messages to dump into the file.
	 */
	public void dumpMessages(List<Message> messages) {
		messages.forEach(this::saveMessage);
	}
	
	public void saveMessage(Message msg) {
		// Check if there is a file header at the top of the file.  If so, add it to the list of lines that make up the header of the file os they can be added back later.
		List<String> fileHeader = new LinkedList<String>();
		int lnNum = 0;
		String ln;
		while ((ln = readLine(lnNum)).startsWith("#")) {
			fileHeader.add(ln);
			lnNum++;
		}
		
		// Create a map to hold the information from the message.
		Map<String, Object> msgData = new LinkedHashMap<String, Object>(); // Use a LinkedHashMap in order to preserve the order of entries.
		Map<String, Object> senderData = new LinkedHashMap<String, Object>(); // A map to hold the sender data.  Will be added to msgData.
		msgData.put("text", msg.getText());
		
		senderData.put("name", msg.getSender().getName());
		senderData.put("displayName", msg.getSender().getDisplayName());
		senderData.put("id", msg.getSender().getId());
		senderData.put("role", msg.getSender().getRole().name());
		
		msgData.put("sender", senderData);
		msgData.put("timeStamp", msg.getTimeStamp());
		
		Yaml yaml = new Yaml();
//		System.out.println(yaml.dump(msgData));
//		String yamlMessage = yaml.dump(msgData);
				
		FileInputStream ioStream = null;
		FileWriter writer = null;
		try {
			ioStream = new FileInputStream(filePath);
			Map<String, Object> msgCabinet = (Map<String, Object>) yaml.load(ioStream);
			List<Map<String, Object>> messageList = (List<Map<String, Object>>) msgCabinet.get("messages");
			
//			System.out.println("msgCabinet:\n" + msgCabinet + "\n");
			messageList.add(msgData);
//			System.out.println("msgCabinet:\n" + msgCabinet);
			
			writer = new FileWriter(filePath);
			for (String str : fileHeader) {
				writer.append(str + "\n");
			}
			
			yaml.dump(msgCabinet, writer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ioStream != null) {
				try {
					ioStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Get a string value from a YAML file.
	 * @param path the path of the string value in the file.
	 * <p>The to access new maps further in the structure of the file, use a dot ('.').</p>
	 * @return
	 */
	public String getString(String path) {
		Yaml yaml = new Yaml();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			Map<String, Object> map = (Map<String, Object>) yaml.load(reader);
			String[] keys = path.split("\\.");
			
//			System.out.println(map);
			
			Map<String, Object> mapAtPath = null;
			for (int i = 0; i < keys.length; i++) {
				if (i != keys.length - 1)
					mapAtPath = (Map<String, Object>) map.get(keys[i]);
				
			}
			
			if (mapAtPath != null)
				return mapAtPath.get(keys[keys.length - 1]).toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	/**
	 * Set a string in the file at the given path.
	 * @param path the path (key) in the yaml file to set the string at.
	 * 		    <p> The to access new maps further in the structure of the file, use a dot.</p>
	 * @param value the value to associate the key with.
	 * @see FileManager#set(String, Object)
	 */
	public void setString(String path, String value) {
		set(path, value);
	}
	
	/**
	 * Set a value in the file at the given path.
	 * @param path the path or key in the yaml file to set the value to.
	 * 		  <p> The to access new maps further in the structure of the file, use a dot.</p>
	 * @param value the value to set.
	 */
	public void set(String path, Object value) {
		List<String> fileHeader = new LinkedList<String>();
		int lnNum = 0;
		String ln; // the current line
		while ((ln = readLine(lnNum)).startsWith("#")) {
			fileHeader.add(ln); // add the line to the list of headers.
			lnNum++; // continue reading the next line.
		}
		
		// Construct yaml to make the dumper dump prettily to the file.
		Yaml yaml = new Yaml(getDumperOptions());
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			
//			System.out.println(yaml.load(reader));
			
			Map<String, Object> map = (Map<String, Object>) yaml.load(reader);
			System.out.println(map);
			
			String[] keys = path.split("\\."); 
			
			System.out.println(Arrays.toString(keys));
			
			Map<String, Object> mapAtPath = null;
			for (int i = 0; i < keys.length; i++) {
				System.out.println(i);
				if (i != keys.length - 1)
					mapAtPath = (Map<String, Object>) map.get(keys[i]);
			}
			
			// Make sure that the path is valid.
			if (mapAtPath != null) {
				
				
				mapAtPath.put(keys[keys.length - 1], value);
			}
			
			writer = new BufferedWriter(new FileWriter(filePath));
			
			// Add the header back to the file.
			for (String str : fileHeader) {
				writer.append(str + "\n");
			}
			
			yaml.dump(map, writer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Get a long type value from a YAML file.
	 * @param path in the file, the path to the value.
	 * <p>The to access new maps further in the structure of the file, use a dot ('.').</p>
	 * @return the value.
	 */
	public long getLong(String path) {
		return Long.parseLong(getString(path));
	}
	
	/**
	 * Get a int type value from a YAML file.
	 * @param path in the file, the path to the value.
	 * <p>The to access new maps further in the structure of the file, use a dot ('.').</p>
	 * @return the value.
	 */
	public int getInt(String path) {
		return Integer.parseInt(getString(path));
	}
	
	/**
	 * Get a short type value from a YAML file.
	 * @param path in the file, the path to the value.
	 * <p>The to access new maps further in the structure of the file, use a dot ('.').</p>
	 * @return the value.
	 */
	public short getShort(String path) {
		return Short.parseShort(getString(path));
	}
	
	/**
	 * Get a boolean type value from a YAML file.
	 * @param path in the file, the path to the value.
	 * <p>The to access new maps further in the structure of the file, use a dot ('.').</p>
	 * @return the value.
	 */
	public boolean getBoolean(String path) {
		return Boolean.parseBoolean(getString(path));
	}
	
	/**
	 * Get a byte type value from a YAML file.
	 * @param path in the file, the path to the value.
	 * <p>The to access new maps further in the structure of the file, use a dot ('.').</p>
	 * @return the value.
	 */
	public byte getByte(String path) {
		return Byte.parseByte(getString(path));
	}
	
	/**
	 * Get a float type value from a YAML file.
	 * @param path in the file, the path to the value.
	 * <p>The to access new maps further in the structure of the file, use a dot ('.').</p>
	 * @return the value.
	 */
	public float getFloat(String path) {
		return Float.parseFloat(getString(path));
	}
	
	/**
	 * Get a char type value from a YAML file. If there is more than one character, the first character is returned.
	 * @param path in the file, the path to the value.
	 * <p>The to access new maps further in the structure of the file, use a dot ('.').</p>
	 * @return the value.
	 */
	public char getChar(String path) {
		char c = getString(path).charAt(0);
		return c;
	}
	
	/**
	 * Get a double type value from a YAML file.
	 * @param path in the file, the path to the value.
	 * <p>The to access new maps further in the structure of the file, use a dot ('.').</p>
	 * @return the value.
	 */
	public double getDouble(String path) {
		return Double.parseDouble(getString(path));
	}
	
	/**
	 * Get a map at the given path.
	 * <p>
	 * If you want a map of the whole file, leave the path string as blank.
	 * </p>
	 * @param path 
	 * @return 
	 */
	public Map<String, Object> getMap(String path) {
		Yaml yaml = new Yaml();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			Map<String, Object> map = (Map<String, Object>) yaml.load(reader);
			String[] keys = path.split("\\.");
			
			
			Map<String, Object> mapAtPath = null;
			for (int i = 0; i < keys.length; i++) {
				if (i != keys.length - 1)
					mapAtPath = (Map<String, Object>) map.get(keys[i]);
				
			}
			
			return mapAtPath;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	/**
	 * Get the dumper options that make the file look pretty.
	 * @return the dumper options.
	 */
	private DumperOptions getDumperOptions() {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
		options.setPrettyFlow(true);
		options.setIndent(2);
		
		return options;
	}
	
	
	// TESTER
	public static void main(String[] args) {
		FileManager fm = null;
		fm = new FileManager("files/room-00.yml");
		
		List<String> objs = fm.readFile();
		
		System.out.println(fm.getMessages());
		
		fm.saveMessage(new Message("baba", new User("namebbb", "dispNameggg", Role.DEFAULT), 21433241L));
		
		System.out.println(fm.readLine(0));
	}
}
