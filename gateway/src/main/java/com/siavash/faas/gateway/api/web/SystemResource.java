package com.siavash.faas.gateway.api.web;

import com.siavash.faas.gateway.model.AlertRequest;
import com.siavash.faas.gateway.service.DeploymentService;
import com.siavash.faas.gateway.util.Constants;
import lombok.extern.slf4j.Slf4j;
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
		logger.info("alert received with body: {}", request);

		return deploymentService.scale(getFunctionName(request));
	}

	private String getFunctionName(@RequestBody AlertRequest request) {
		return request.getAlerts().get(0).getLabels().get(Constants.FUNCTION_NAME);
	}

}
