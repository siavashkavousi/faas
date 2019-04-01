package com.siavash.faas.provider.api.web;

import com.siavash.faas.provider.model.DeployRequest;
import com.siavash.faas.provider.model.Provider;
import com.siavash.faas.provider.model.RemoveRequest;
import com.siavash.faas.provider.service.impl.ProviderLookup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DeploymentResource {

	private final ProviderLookup providerLookup;

	public DeploymentResource(final ProviderLookup providerLookup) {
		this.providerLookup = providerLookup;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> deploy(@RequestBody DeployRequest request) {
		logger.info("deploy request: {}", request);

		String id = providerLookup.providerService(Provider.SWARM).deploy(request);

		return ResponseEntity.ok(id);
	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity remove(@RequestBody RemoveRequest request) {
		logger.info("remove request: {}", request);

		providerLookup.providerService(Provider.SWARM).remove(request);

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
