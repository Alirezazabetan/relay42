package com.relay42.sensor;

import com.relay42.sensor.service.operator.AverageOperator;
import com.relay42.sensor.service.operator.MaxOperator;
import com.relay42.sensor.service.operator.MedianOperator;
import com.relay42.sensor.service.operator.MinOperator;
import com.relay42.sensor.util.EventSub;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class OperationTest {

	@Test
	void testMaxOperation() {
		
		MaxOperator mo = new MaxOperator();
		EventSub eventSub = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return new BigDecimal(200);
			}
		};
		StepVerifier
			.create(mo.calculate(Flux.fromStream(Stream.of(eventSub, new EventSub(), new EventSub()))))
			.expectNext(new BigDecimal(200))
			.verifyComplete();
	}
	
	@Test
	void testMinOperation() {
		
		MinOperator mo = new MinOperator();
		EventSub eventSub = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ZERO;
			}
		};
		StepVerifier
			.create(mo.calculate(Flux.fromStream(Stream.of(eventSub, new EventSub(), new EventSub()))))
			.expectNext(BigDecimal.ZERO)
			.verifyComplete();
	}
	
	@Test
	void testAvgOperation() {
		
		var mo = new AverageOperator();
		
		StepVerifier
			.create(mo.calculate(Flux.fromStream(Stream.of(new EventSub(), new EventSub(), new EventSub()))))
			.expectNext(BigDecimal.TEN)
			.verifyComplete();
	}
	
	@Test
	void testMedianOperation() {
		
		var mo = new MedianOperator();
		EventSub eventSub1 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ONE;
			}
		};
		EventSub eventSub2 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ZERO;
			}
		};
		EventSub eventSub3 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.TEN;
			}
		};
		StepVerifier
			.create(mo.calculate(Flux.fromStream(Stream.of(eventSub1, eventSub2, eventSub3))))
			.expectNext(BigDecimal.ONE)
			.verifyComplete();
	}
	
	@Test
	void testMedianOddOperation() {
		
		var mo = new MedianOperator();
		EventSub eventSub1 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ONE;
			}
		};
		EventSub eventSub2 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ZERO;
			}
		};
		EventSub eventSub3 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.TEN;
			}
		};
		EventSub eventSub4 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return new BigDecimal(5);
			}
		};
		StepVerifier
			.create(mo.calculate(Flux.fromStream(Stream.of(eventSub1, eventSub2, eventSub3, eventSub4))))
			.expectNext(new BigDecimal(5.5))
			.verifyComplete();
	}

}
