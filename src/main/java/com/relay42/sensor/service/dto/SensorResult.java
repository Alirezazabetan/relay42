package com.relay42.sensor.service.dto;

import com.relay.iot.consumer.simulator.app.service.enumeration.Operation;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SensorResult {

	private List<com.relay.iot.consumer.simulator.app.model.EventResponse> resultList;
	private int resultCount;
	private BigDecimal result;
	private Operation operation;

}
