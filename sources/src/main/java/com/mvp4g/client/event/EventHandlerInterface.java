package com.mvp4g.client.event;

/**
 * Interface that defines an event handler.<br/>
 * <br/>
 * This interface provides getter and setter for the event bus.<br/>
 * <br/>
 * It is recommended to use directly <code>BaseEventHandler</code>.
 * 
 * 
 * @since 27.04.2010
 * @author Dan Persa
 */
public interface EventHandlerInterface<E extends EventBus> {

	/**
	 * Set an event bus to the event handler
	 * 
	 * @param eventBus
	 *            event bus to set
	 */
	public void setEventBus( E eventBus );

	/**
	 * Get the event bus associated with the event handler
	 * 
	 * @return eventBus manipulated by the event handler.
	 */
	public E getEventBus();

	/**
	 * Call by the event bus when the handler handles its first event.
	 * 
	 */
	public void bind();

	/**
	 * Indicates if the presenter is activated or not. If the event handler is activated and if the bind
	 * method hasn't been called yet, then the bind method should be called.
	 * 
	 * @return true if the presenter is activated, false otherwise
	 */
	public boolean isActivated();

	/**
	 * Set if the event handler is activated or not. By default, an event handler should be activated.
	 * 
	 * @param activated
	 *            new activation parameter
	 */
	public void setActivated( boolean activated );

}
