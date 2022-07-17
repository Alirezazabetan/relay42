package com.relay42.sensor.service;

import com.relay42.sensor.domain.EvenMessage;
import com.relay42.sensor.service.mapper.EvenMessageMapper;
import com.relay42.sensor.service.operator.OperatorServiceFactory;
import com.relay42.sensor.service.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.util.List;


@Service
@Slf4j
public class EventQueryService {

	private ReactiveCassandraTemplate reactiveCassandraTemplate;

    private EvenMessageMapper evenMessageMapper;

	private OperatorServiceFactory operatorService;

	public EventQueryService(ReactiveCassandraTemplate reactiveCassandraTemplate, EvenMessageMapper evenMessageMapper, OperatorServiceFactory operatorService) {
		this.reactiveCassandraTemplate = reactiveCassandraTemplate;
		this.evenMessageMapper = evenMessageMapper;
		this.operatorService = operatorService;
	}

	@Transactional
	public Mono<SensorResult> querySensorData(SensorFilter filter) {
		log.info("Query on event message with filter: {}", filter);
		Query query = Query.empty();
		query.and(Criteria.where("timestampDate").gte(filter.getFromDateTime()));
		query.and(Criteria.where("timestampDate").lte(filter.getToDateTime()));
		if (StringUtil.isNotEmpty(filter.getEventType()))
			query.and(Criteria.where("type").is(filter.getEventType()));
		if (filter.getClusterId() != null)
			query.and(Criteria.where("clusterId").is(filter.getClusterId()));

		Flux<EvenMessage> events = reactiveCassandraTemplate.select(query, EvenMessage.class);
		Mono<BigDecimal> calculate = operatorService.getInstance(filter.getOperation()).calculate(Flux.from(events));

		return events.map(evenMessageMapper::toDto).collectList().flatMap(m -> {
			SensorResult sensorResult = new SensorResult();
			sensorResult.setResultList(m);
			sensorResult.setResultCount(m.size());
			Mono<SensorResult> just = Mono.just(sensorResult);
			Mono<Tuple2<SensorResult, BigDecimal>> zipWith = just.zipWith(calculate);
			return zipWith.map(t -> {
				List<EventResponse> resultList = t.getT1().getResultList();
				SensorResult sr = new SensorResult();
				sr.setResultCount(resultList.size());
				sr.setResult(t.getT2());
				sr.setOperation(filter.getOperation());
				return sr;
			});
		});
	}

}
