package com.relay42.sensor.controller.service.operator;

import com.relay.iot.consumer.simulator.app.model.event.Event;
import com.relay.iot.consumer.simulator.app.service.enumeration.Operation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

import java.math.BigDecimal;

@Service
public class MaxOperator implements Operator{

	@Override
	public Operation getOperation() {
		return Operation.MAX;
	}

	@Override
	public Mono<BigDecimal>  calculate(Flux<Event> events) {
		return MathFlux.max(events.map(Event::getValue));
	}

}
