package com.siavash.faas.gateway.service;

public interface MetricService {

	void incrementFunctionInvocationTotal(String name);

}
