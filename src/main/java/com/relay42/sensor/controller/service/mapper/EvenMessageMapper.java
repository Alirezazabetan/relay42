package com.relay42.sensor.controller.service.mapper;


import com.relay42.sensor.domain.EvenMessage;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link EvenMessage} and its DTO {@link EventResponse}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EvenMessageMapper extends EntityMapper<EventResponse, EvenMessage> {}