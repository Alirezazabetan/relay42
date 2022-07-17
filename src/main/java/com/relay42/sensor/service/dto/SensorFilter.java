package com.relay42.sensor.service.dto;

import com.relay42.sensor.service.enumeration.Operation;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SensorFilter implements Serializable {
	private Operation operation;
	private Date fromDateTime;
	private Date toDateTime;
	private String eventType;
	private Long clusterId;

}