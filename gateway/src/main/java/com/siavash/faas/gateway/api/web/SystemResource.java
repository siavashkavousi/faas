package com.siavash.faas.gateway.api.web;

import com.siavash.faas.gateway.model.AlertRequest;
import com.siavash.faas.gateway.service.DeploymentService;
import com.siavash.faas.gateway.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/system")
public class SystemResource {

	private final DeploymentService deploymentService;

	public SystemResource(final DeploymentService deploymentService) {
		this.deploymentService = deploymentService;
	}

	@PostMapping(path = "/alert", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Mono<ResponseEntity> alert(@RequestBody AlertRequest request) {
		logger.debug("alert received with body: {}", request);

		if (isApiHighInvocationRateAlert(request)) {
			return deploymentService.scaleUp(getFunctionName(request));
		} else if (isApiLowInvocationRateAlert(request)) {
			return deploymentService.scaleSpecific(getFunctionName(request), 1L);
		} else {
			logger.error("unhandled alert occurred: {}", request);
			return Mono.just(new ResponseEntity(HttpStatus.NO_CONTENT));
		}
	}

	private boolean isApiHighInvocationRateAlert(AlertRequest request) {
		return request.getAlerts().get(0).getLabels().containsValue(Constants.API_HIGH_INVOCATION_RATE);
	}

	private boolean isApiLowInvocationRateAlert(AlertRequest request) {
		return request.getAlerts().get(0).getLabels().containsValue(Constants.API_LOW_INVOCATION_RATE);
	}

	private String getFunctionName(AlertRequest request) {
		return request.getAlerts().get(0).getLabels().get(Constants.FUNCTION_NAME);
	}

}
