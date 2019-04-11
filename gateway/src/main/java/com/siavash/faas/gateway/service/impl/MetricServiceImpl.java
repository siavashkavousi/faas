package com.siavash.faas.gateway.service.impl;

import com.siavash.faas.gateway.service.MetricService;
import com.siavash.faas.gateway.util.Constants;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class MetricServiceImpl implements MetricService {

	private final MeterRegistry registry;

	public MetricServiceImpl(final MeterRegistry registry) {
		this.registry = registry;
	}

	@Override
	public void incrementFunctionInvocation(String name, Integer statusCode) {
		Counter counter = Counter.builder("function.invocation")
				.tag(Constants.FUNCTION_NAME, name)
				.tag(Constants.STATUS_CODE, String.valueOf(statusCode))
				.register(registry);
		counter.increment();
	}

	@Override
	public <T> T trackFunctionExecutionDuration(String name, Supplier<T> f) {
		LongTaskTimer timer = LongTaskTimer.builder("function.execution.duration")
				.tag(Constants.FUNCTION_NAME, name)
				.register(registry);
		return timer.record(f);
	}

}
