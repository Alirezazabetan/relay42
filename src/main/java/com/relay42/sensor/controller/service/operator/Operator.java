package com.relay42.sensor.controller.service.operator;

import com.relay.iot.consumer.simulator.app.model.event.Event;
import com.relay.iot.consumer.simulator.app.service.enumeration.Operation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface Operator {

	public Operation getOperation();

	public Mono<BigDecimal> calculate(Flux<Event> events);
	
	
}
