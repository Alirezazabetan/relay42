package com.relay42.sensor.service.operator;

import com.relay42.sensor.service.dto.event.Event;
import com.relay42.sensor.service.enumeration.Operation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface Operator {

	public Operation getOperation();

	public Mono<BigDecimal> calculate(Flux<Event> events);
	
	
}
