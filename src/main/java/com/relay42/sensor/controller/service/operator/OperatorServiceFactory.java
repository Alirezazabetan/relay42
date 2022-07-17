package com.relay42.sensor.controller.service.operator;

import com.relay.iot.consumer.simulator.app.service.enumeration.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class OperatorServiceFactory {

	private Map<Operation, Operator> operatorMap = new EnumMap<>(Operation.class);

	List<Operator> operators;

	@Autowired
	public OperatorServiceFactory(List<Operator> operators) {
		this.operators = operators;
	}

	@PostConstruct
	public void init() {
		operators.forEach(it -> operatorMap.put(it.getOperation(), it));
	}

	public Operator getInstance(Operation operation) {
		Operator operatorService = operatorMap.get(operation);
		if (operatorService == null) {
			throw new RuntimeException("Unknown service type: " + operation);
		}
		return operatorService;
	}

}
