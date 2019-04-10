package com.siavash.faas.gateway.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface DeploymentService {

	Mono<ResponseEntity> scale(String funcName);

}
