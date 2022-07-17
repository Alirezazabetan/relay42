package com.relay42.sensor.controller.service.dto;

import com.relay42.sensor.controller.service.enumeration.Operation;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SensorResult {

	private List<EventResponse> resultList;
	private int resultCount;
	private BigDecimal result;
	private Operation operation;

}
