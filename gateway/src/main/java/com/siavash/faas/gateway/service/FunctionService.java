package com.siavash.faas.gateway.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface FunctionService {

	Mono<ResponseEntity<String>> invoke(String funcName, String request);

}
