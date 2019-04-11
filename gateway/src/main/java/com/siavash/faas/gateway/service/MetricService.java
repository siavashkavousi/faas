package com.siavash.faas.gateway.service;

import java.util.function.Supplier;

public interface MetricService {

	void incrementFunctionInvocation(String name, Integer statusCode);

	<T> T trackFunctionExecutionDuration(String name, Supplier<T> f);

}
