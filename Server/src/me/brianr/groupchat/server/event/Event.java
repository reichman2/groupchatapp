package me.brianr.groupchat.server.event;

/**
 * An event.
 * <p>An event is called when it occurs and is sent to all registered 
 * listeners so they can do their thing.
 * </p>
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public abstract class Event {
	
	private boolean isCancelled;
	private boolean isAsynchronous;
	
	
	/**
	 * Construct a new event.
	 */
	public Event() {
		this.isAsynchronous = false;
		this.isCancelled = false;
	}
	
	/**
	 * Construct a new event.
	 * @param isAsynchronous set whether or not the event is asynchronous.
	 */
	public Event(boolean isAsynchronous) {
		this.isAsynchronous = isAsynchronous;
		this.isCancelled = false;
	}
	
	
	/**
	 * Get the name of the event.
	 * @return the name of the event.
	 */
	public abstract String getName();
	
	
	/**
	 * Set whether or not the event is cancelled.
	 * @param canceled set the canceled.
	 */
	public void setCancelled(boolean canceled) {
		this.isCancelled = canceled;
	}
	
	/**
	 * Get if the event is cancelled.
	 * @return whether or not the event is cancelled.
	 */
	public boolean isCancelled() {
		return this.isCancelled;
	}
	
}
