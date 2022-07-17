package com.relay42.sensor.controller.service.operator;

import com.relay.iot.consumer.simulator.app.model.event.Event;
import com.relay.iot.consumer.simulator.app.service.enumeration.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

import java.math.BigDecimal;

@Service
@Slf4j
public class MinOperator implements Operator {

	@Override
	public Operation getOperation() {
		return Operation.MIN;
	}

	@Override
	public Mono<BigDecimal> calculate(Flux<Event> events) {
		log.info("calculate min");
		return MathFlux.min(events.map(Event::getValue));
	}


}
