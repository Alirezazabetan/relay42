package com.relay42.sensor.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventResponse {

	private String uid;

	private Long id;

	private BigDecimal value;

	private OffsetDateTime timestamp;

	private String type;

	private String name;

	private Long clusterId;

	private long offset;

	private long partitionId;

	private String topic;

	private String groupId;
	


}
