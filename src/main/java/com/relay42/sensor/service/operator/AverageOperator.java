package com.relay42.sensor.service.operator;

import com.relay42.sensor.service.dto.event.Event;
import com.relay42.sensor.service.enumeration.Operation;
import com.relay42.sensor.service.math.CustomMonoAverageBigDecimal;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class AverageOperator implements Operator {

	@Override
	public Operation getOperation() {
		return Operation.AVERAGE;
	}

	@Override
	public Mono<BigDecimal> calculate(Flux<Event> events) {
		CustomMonoAverageBigDecimal<BigDecimal> monoAvgBigDecimal = new CustomMonoAverageBigDecimal<BigDecimal>(
				events.map(Event::getValue), i -> i);
		return monoAvgBigDecimal;
	}

}
