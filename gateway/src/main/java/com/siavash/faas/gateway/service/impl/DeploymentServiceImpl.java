package com.siavash.faas.gateway.service.impl;

import com.siavash.faas.gateway.client.ProviderClient;
import com.siavash.faas.gateway.model.ScaleMode;
import com.siavash.faas.gateway.model.ScaleRequest;
import com.siavash.faas.gateway.service.DeploymentService;
import com.siavash.faas.gateway.service.MetricService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeploymentServiceImpl implements DeploymentService {

	private final MetricService metricService;
	private final ProviderClient client;

	public DeploymentServiceImpl(final MetricService metricService, final ProviderClient client) {
		this.metricService = metricService;
		this.client = client;
	}

	@Override
	public void scale(String funcName) {
		double fcps = 0;// = metricService.getFunctionInvocationTotal(funcName);
		logger.info("function: {} call per second: {}", funcName, fcps);

		client.inspect(funcName).doOnSuccess(inspect -> {
			logger.debug("function current service replicas: {}", inspect.getReplicas());
			long scaleFactor = (long) (fcps - inspect.getReplicas()) / 10;
			if (scaleFactor > 0) {
				client.scale(new ScaleRequest(funcName, ScaleMode.SPECIFIC, inspect.getReplicas() + scaleFactor)).subscribe(response -> logger.info("scale response is: {}", response));
			} else if (scaleFactor < 0) {
				client.scale(new ScaleRequest(funcName, ScaleMode.SPECIFIC, inspect.getReplicas() - scaleFactor)).subscribe(response -> logger.info("scale response is: {}", response));
			} else {
				logger.info("expected amount of service replicas");
			}
		}).subscribe(response -> {
			logger.info("ok");
		});
	}

}
