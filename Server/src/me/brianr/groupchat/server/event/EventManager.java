package me.brianr.groupchat.server.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that handles event triggering.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class EventManager {
	// The list of registered listeners.
	private static List<Listener> registeredListeners = new ArrayList<>();
	
	
	/**
	 * Register a Listener as an event listener.
	 * <p>
	 * The method returns false if the listener is not added.
	 * A listener is not added because it is already in the list of registered listeners.
	 * </p>
	 * @param listener the listener to register.
	 * @return true if the method runs successfully and the listener is added.
	 */
	public static boolean registerListener(Listener listener) {
		if (!registeredListeners.contains(listener)) {
			registeredListeners.add(listener);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Unregister a Listener as an event listener.
	 * @param listener the listener to unregister.
	 * @return true if the listener was unregistered, otherwise false.
	 */
	public static boolean unregisterListener(Listener listener) {
		if (registeredListeners.contains(listener)) {
			registeredListeners.remove(listener);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Trigger an event when it occurs.
	 * <p>
	 * Call this method when the event occurs/it is initially handled. 
	 * Then support for other handlers can be added.
	 * </p>
	 * @param e the event that occurred and is being triggered.
	 */
	public static void trigger(Event e) {
		new Thread(() -> { // Thread-safe.
			for (Listener listener : registeredListeners) {
				// Make an array of all of the methods in the listener
				Method[] handlers = listener.getClass().getMethods();
				
				for (Method m : handlers) { // For each method m in handlers...
					EventHandler eventHandler = m.getAnnotation(EventHandler.class); // Register it as an event handler.
					
					if (eventHandler == null) // It will be null if it isn't annotated with '@EventHandler'
						continue; // if it's null, continue to the next loop.
					
					if (m.getParameters().length != 1) // If the amount of parameters is incorrect...
						continue; // continue to the next loop.
					
					try {
						m.invoke(e);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}).start();
	}
}
