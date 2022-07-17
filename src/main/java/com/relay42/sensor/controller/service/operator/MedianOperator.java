package com.relay42.sensor.controller.service.operator;

import com.relay.iot.consumer.simulator.app.model.event.Event;
import com.relay.iot.consumer.simulator.app.service.enumeration.Operation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MonoMedianBigDecimal;

import java.math.BigDecimal;

@Service
public class MedianOperator implements Operator {

	@Override
	public Operation getOperation() {
		return Operation.MEDIAN;
	}

	@Override
	public Mono<BigDecimal> calculate(Flux<Event> events) {
		MonoMedianBigDecimal<BigDecimal> monoMedianBigDecimal = new MonoMedianBigDecimal<BigDecimal>(
				events.map(Event::getValue), i -> i);
		return monoMedianBigDecimal;
	}

}
