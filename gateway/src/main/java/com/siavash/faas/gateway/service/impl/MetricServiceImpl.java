package com.siavash.faas.gateway.service.impl;

import com.siavash.faas.gateway.service.MetricService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MetricServiceImpl implements MetricService {

	private final MeterRegistry registry;

	public MetricServiceImpl(final MeterRegistry registry) {
		this.registry = registry;
	}

	@Override
	public void incrementFunctionInvocationTotal(String name) {
		Counter counter = Counter.builder("function.invocation")
				.tag("function_name", name)
				.register(registry);
		counter.increment();
	}

}
