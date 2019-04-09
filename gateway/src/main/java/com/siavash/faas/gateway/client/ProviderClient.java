package com.siavash.faas.gateway.client;

import com.siavash.faas.gateway.model.InspectResponse;
import com.siavash.faas.gateway.model.ScaleRequest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ProviderClient {

	Mono<InspectResponse> inspect(String funcName);

	Mono<ResponseEntity> scale(ScaleRequest request);

}
