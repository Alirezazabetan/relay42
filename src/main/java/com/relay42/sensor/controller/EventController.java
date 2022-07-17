package com.relay42.sensor.controller;

import com.relay42.sensor.controller.errors.BadRequestException;
import com.relay42.sensor.controller.service.EventQueryService;
import com.relay42.sensor.controller.service.dto.SensorFilter;
import com.relay42.sensor.controller.service.dto.SensorResult;
import com.relay42.sensor.controller.service.enumeration.Operation;
import com.relay42.sensor.service.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/even-messages")
@Slf4j
public class EventController {


    private final EventQueryService eventService;

    public EventController(EventQueryService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{operation}")
    public Mono<SensorResult> get(@PathVariable("operation") Operation operation,
                                  @RequestParam("eventType") Optional<String> eventType,
                                  @RequestParam("clusterId") Optional<Long> clusterId,
                                  @RequestParam("fromDate") String fromDate,
                                  @RequestParam("toDate") String toDate) {
        log.info("Query on event message");
        Optional<Date> fromDateOpt = DateUtil.toDate(fromDate);
        Optional<Date> toDateOpt = DateUtil.toDate(toDate);
        if (fromDateOpt.isEmpty()) {
            return Mono.error(new BadRequestException("Time format for fromDate is wrong please put yyyy-mm-dd (ex. 2022-07-16)"));
        }
        if (toDateOpt.isEmpty()) {
            return Mono.error(new BadRequestException("Time format for toDate is wrong please put yyyy-mm-dd (ex. 2022-07-16)"));
        }
        SensorFilter sensorFilter = new SensorFilter(operation, fromDateOpt.get(), toDateOpt.get(), eventType.orElse(""), clusterId.orElse(null));
        Mono<SensorResult> result = eventService.querySensorData(sensorFilter);
        return result;
    }


}
