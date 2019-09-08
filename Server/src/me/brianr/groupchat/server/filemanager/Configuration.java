package me.brianr.groupchat.server.filemanager;

import java.util.Map;

/**
 * Represents a configuration with all of the properties.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class Configuration {
	private Map<String, Object> properties;
	private final String path;
	
	/**
	 * Construct a new configuration.
	 * @param path the path of the config.yml file.
	 */
	public Configuration(String path) {
		this.path = path;
		FileManager fm = new FileManager(path);
		this.properties = fm.getMap("");
	}
	
	
	/**
	 * Get a map of the the configuration properties.
	 * @return a map of the the configuration properties.
	 */
	public Map<String, Object> getProperties() {
		return this.properties;
	}
	
	/**
	 * Get a specific configuration property.
	 * @return the configuration property.
	 */
	public String getProperty(String path) {
		FileManager fm = new FileManager(this.path);
		return fm.getString(path);
	}
}

