package com.relay42.sensor.service;

import com.relay42.sensor.domain.EvenMessage;
import com.relay42.sensor.repository.EvenMessageRepository;
import com.relay42.sensor.service.dto.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class EventStoreService {

    private EvenMessageRepository evenMessageRepository;

    public EventStoreService(EvenMessageRepository evenMessageRepository) {
        this.evenMessageRepository = evenMessageRepository;
    }

    @Transactional
    public void save(Event event, MessageHeaders headers) {
        EvenMessage evenMessage = EvenMessage.builder()
                .uid(UUID.randomUUID().toString())
                .name(event.getName())
                .id(event.getId())
                .clusterId(event.getClusterId())
                .type(event.getType())
                .value(event.getValue())
                .timestampDate(new Date(event.getTimestamp().toInstant().toEpochMilli()))
                .groupId("" + headers.get("kafka_groupId"))
                .topic("" + headers.get("kafka_receivedTopic"))
                .build();
        evenMessageRepository.save(evenMessage).doOnSuccess((e) -> log.info("Event stored with uid: {}", e.getUid())).subscribe();

    }

}
