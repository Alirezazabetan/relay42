package com.relay42.sensor.service.operator;

import com.relay42.sensor.service.enumeration.Operation;
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
