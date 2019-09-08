package me.brianr.groupchat.filemanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class to check if a type of an object is a primitive type.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class WrapperChecker {
	
	/**
	 * This class should not be instantiated.
	 */
	private WrapperChecker() {}
	
	/**
	 * The list of wrapper types
	 */
	private static final List<Class<?>> wrapperTypes = new ArrayList<>(Arrays.asList(
		Boolean.class,
		Byte.class,
		Character.class,
		Double.class,
		Float.class,
		Integer.class,
		Long.class,
		Short.class,
		Void.class
	));
	
	
	/**
	 * Check wrapper type of a class.
	 * @param clazz the class to check the type of.
	 * @return true if the give class is a wrapper type.
	 */
	public static boolean isWrapperType(Class<?> clazz) {
		return wrapperTypes.contains(clazz);
	}

}
