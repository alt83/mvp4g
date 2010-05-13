/*
 * Copyright 2010 Pierre-Laurent Coirier
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mvp4g.client;

import com.mvp4g.client.annotation.XmlFilePath;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;

/**
 * This interface defines a module for Mvp4g. This interface is only used by the framework to
 * manipulate modules.
 * 
 * @author plcoirier
 * 
 */
@XmlFilePath( "mvp4g-conf.xml" )
public interface Mvp4gModule {

	/**
	 * Method called to create the module and fire the start event.
	 */
	public void createAndStartModule();

	/**
	 * @return start view of the module
	 */
	public Object getStartView();

	/**
	 * @return event bus of the module
	 */
	public EventBus getEventBus();

	/**
	 * Add a converter to the associate token
	 * 
	 * @param token
	 *            ascendant modules history name + event type
	 * @param hc
	 *            history converter to associate to the token
	 */
	public void addConverter( String eventType, String historyName, HistoryConverter<?> hc );

	/**
	 * Place an event and its associated object in the browser history
	 * 
	 * @param <T>
	 *            type of the object associated with the event
	 * @param event
	 *            event to store
	 * @param form
	 *            object associated with the event
	 */
	public void place( String event, String form );

	/**
	 * Deal the event receive from browse history change or pass it to a child module if needed.
	 * 
	 * @param eventType
	 *            token stored in the browse history (event type + ascendant modules history name)
	 * @param passer
	 *            passer to execute the event.
	 */
	public void dispatchHistoryEvent( String eventType, Mvp4gEventPasser passer );
	
	/**
	 * Clear the history token stored in the browse history url by adding a new empty token
	 */
	public void clearHistory();

}