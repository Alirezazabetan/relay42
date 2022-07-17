package com.relay42.sensor.domain;

import com.relay42.sensor.service.dto.event.Event;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("events")
public class EvenMessage implements Serializable, Event {

	@PrimaryKey
	private String uid = UUID.randomUUID().toString();

	private Long id;

	private BigDecimal value;

	private Date timestampDate;

	private String type;

	private String name;

	private Long clusterId;

	private long offset;

	private long partitionId;

	private String topic;

	private String groupId;

	@Transient
	@Override
	public OffsetDateTime getTimestamp() {
		if(getTimestampDate() == null)
			return null;
		return getTimestampDate().toInstant()
				  .atOffset(ZoneOffset.UTC);
	}

}
