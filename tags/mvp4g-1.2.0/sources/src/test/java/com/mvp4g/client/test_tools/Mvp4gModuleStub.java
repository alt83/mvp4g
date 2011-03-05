package com.mvp4g.client.test_tools;

import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;

public class Mvp4gModuleStub implements Mvp4gModule {

	private EventBus eventBus;
	private String eventType;
	private Object form;
	private Mvp4gEventPasser passer;

	public Mvp4gModuleStub( EventBus eventBus ) {
		this.eventBus = eventBus;
	}

	public void addConverter( String token, HistoryConverter<?> hc ) {

	}

	public void createAndStartModule() {

	}

	public void dispatchHistoryEvent( String eventType, Mvp4gEventPasser passer ) {
		this.eventType = eventType;
		this.passer = passer;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public Object getStartView() {
		return null;
	}

	public void place( String token, String form ) {
		this.eventType = token;
		this.form = form;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @return the passer
	 */
	public Mvp4gEventPasser getPasser() {
		return passer;
	}

	/**
	 * @return the form
	 */
	public Object getForm() {
		return form;
	}

	public void addConverter( String eventType, String historyName, HistoryConverter<?> hc ) {
		// TODO Auto-generated method stub

	}

	public void clearHistory() {
		this.eventType = null;
		this.form = null;
	}

	public void onForward() {
		// TODO Auto-generated method stub

	}

	public void setParentModule( Mvp4gModule parentModule ) {
		// TODO Auto-generated method stub

	}

}