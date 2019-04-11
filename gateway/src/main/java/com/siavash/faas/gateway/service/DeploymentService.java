package com.siavash.faas.gateway.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface DeploymentService {

	Mono<ResponseEntity> scaleUp(String funcName);

	Mono<ResponseEntity> scaleSpecific(String funcName, long specific);

}
