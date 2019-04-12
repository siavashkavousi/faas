package com.siavash.faas.gateway.service;

import java.util.concurrent.Callable;

public interface MetricService {

	void incrementFunctionInvocation(String name, Integer statusCode);

	<T> T trackFunctionExecutionDuration(String name, Callable<T> f) throws Exception;

}
