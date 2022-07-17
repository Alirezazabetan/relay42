package com.relay42.sensor.consumer;


import com.relay42.sensor.service.dto.event.EventModel;

public interface PayloadReader {

	public boolean support(String contentType, Object payload);
	
	public EventModel read(Object payload);
	
}
