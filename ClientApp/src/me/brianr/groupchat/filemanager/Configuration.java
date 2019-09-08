package me.brianr.groupchat.filemanager;

/**
 * A class for holding the properties needed to connect to the server.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class Configuration {
	private volatile String serverAddress;
	private volatile String serverPort;
	
	
	/**
	 * Construct a configuration file in the default way.
	 * <p>Sets properties to their default values.</p>
	 */
	public Configuration() {
		this.serverAddress = "127.0.0.1";
		this.serverPort = "2586";
	}
	
	/**
	 * Construct a configuration with the server address set and the default port.
	 * @param serverAddress the server address to connect to.
	 */
	public Configuration(String serverAddress) {
		this.serverAddress = serverAddress;
		this.serverPort = "2586";
	}
	
	/**
	 * Construct a configuration with the server address and the port.
	 * @param serverAddress the server address to connect to.
	 * @param serverPort the port that the server is running on.
	 */
	public Configuration(String serverAddress, String serverPort) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}
	
	
	/**
	 * Get the server address.
	 * @return the server address.
	 */
	public String getServerAddress() {
		return this.serverAddress;
	}
	
	/**
	 * Get the server port.
	 * @return the server port.
	 */
	public String getServerPort() {
		return this.serverPort;
	}
	
	
	/**
	 * Construct a configuration instance from a file.
	 * @param path the path of the file.
	 * @return the instance of the Configuration.
	 */
	public static Configuration constructFromYamlFile(String path) {
		FileManager fm = new FileManager(path);
		
		String serverAddress = fm.getString("server-properties.address");
		String serverPort = fm.getString("server-properties.port");
		
//		System.out.println(serverAddress + ":" + serverPort);
		
		return new Configuration(serverAddress, serverPort);
	}
}
