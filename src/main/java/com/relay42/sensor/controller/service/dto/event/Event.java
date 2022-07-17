package com.relay42.sensor.controller.service.dto.event;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface Event {

    Long getId();

    BigDecimal getValue();

    OffsetDateTime getTimestamp();

    String getType();

    String getName();

//    @Nullable
    Long getClusterId();
}