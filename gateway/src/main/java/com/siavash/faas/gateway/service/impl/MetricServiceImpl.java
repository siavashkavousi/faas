package com.siavash.faas.gateway.service.impl;

import com.siavash.faas.gateway.service.MetricService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Service;

@Service
public class MetricServiceImpl implements MetricService {

	@Override
	public void incrementFunctionInvocationTotal(String name) {
		Counter counter = Metrics.counter(new StringBuilder("functions.invocation.total.").append(name).toString());
		counter.increment();
	}

}
