package com.mycompany.myapp.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


public class EventsForm {
	private String eventList;
	private String userId;
	private String flag;
	
	public EventsForm(){
		//eventList = new ArrayList<String>();
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	
	public String getEventList() {
		return eventList;
	}

	public void setEventList(String eventList) {
		this.eventList = eventList;
	}

}