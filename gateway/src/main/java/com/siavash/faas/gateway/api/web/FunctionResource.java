package com.siavash.faas.gateway.api.web;

import com.siavash.faas.gateway.service.FunctionService;
import com.siavash.faas.gateway.service.MetricService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/functions")
public class FunctionResource {

	private final FunctionService functionService;
	private final MetricService metricService;

	public FunctionResource(final FunctionService functionService, final MetricService metricService) {
		this.functionService = functionService;
		this.metricService = metricService;
	}

	@PostMapping(path = "/{name}")
	public Mono<ResponseEntity<String>> invoke(@PathVariable String name, @RequestBody(required = false) String request) {
		logger.info("invoked function: {}", name);
		return metricService.trackFunctionExecutionDuration(name, () -> {
			Mono<ResponseEntity<String>> responseMono = functionService.invoke(name, request);
			return responseMono.doOnSuccess(response -> metricService.incrementFunctionInvocation(name, response.getStatusCodeValue()));
		});
	}

}
