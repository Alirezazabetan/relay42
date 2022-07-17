package com.relay42.sensor.service.dto.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventModel implements Event {

	private Long id;

	private BigDecimal value;

	private OffsetDateTime timestamp;

	private String type;

	private String name;

	private Long clusterId;

}