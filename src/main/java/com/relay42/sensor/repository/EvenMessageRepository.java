package com.relay42.sensor.repository;

import com.relay42.sensor.domain.EvenMessage;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public interface EvenMessageRepository extends ReactiveCassandraRepository<EvenMessage, String> {
    Mono<EvenMessage> findAllByTimestampLessThanEqualAndTimestampGreaterThanEqual(Date endDate, Date startDate);
 
}