package com.siavash.faas.provider.api.web;

import com.siavash.faas.provider.model.*;
import com.siavash.faas.provider.service.impl.ProviderLookup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


	@PatchMapping(path = "/scale", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity scale(@RequestBody ScaleRequest request) {
		logger.info("scale request: {}", request);

		providerLookup.providerService(Provider.SWARM).scale(request);

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<InspectResponse> inspect(@PathVariable String name) {
		logger.info("inspect request: {}", name);

		InspectResponse response = providerLookup.providerService(Provider.SWARM).inspect(name);

		logger.info("inspect response: {}", response);

		return ResponseEntity.ok(response);
	}

}
