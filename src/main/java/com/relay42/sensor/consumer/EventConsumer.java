package com.relay42.sensor.consumer;


import com.relay42.sensor.controller.errors.ConsumerExcpetion;
import com.relay42.sensor.service.EventStoreService;
import com.relay42.sensor.service.dto.event.EventModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class EventConsumer {

	private EventStoreService eventService;
	private List<PayloadReader> readers;

	public EventConsumer(EventStoreService eventService, List<PayloadReader> readers) {
		this.eventService = eventService;
		this.readers = readers;
	}

    @KafkaListener(topics = "${relay.topic.name}", groupId = "temp", containerFactory = "tempKafkaListenerContainerFactory")
	public void process(Message<?> message) {
		MessageHeaders headers = message.getHeaders();
		String contentType = getContentType(headers);
		Object payload = message.getPayload();
		log.info("Payload {}", payload);
		PayloadReader payloadReader = getPayloadReader(contentType,payload);
		EventModel event = payloadReader.read(payload);
		log.info("eventmodel {}", event.toString());
		eventService.save(event, headers);
	}

	private String getContentType(MessageHeaders headers) {
		return "" + headers.getOrDefault("contentType", "application/json");
	}

	private PayloadReader getPayloadReader(String contentType,Object payload){
		return readers.stream().filter(f -> f.support(contentType, payload)).findFirst()
				.orElseThrow(() -> new ConsumerExcpetion("no reader found for " + contentType));
	}

}
