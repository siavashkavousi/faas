package com.siavash.faas.watchdog.api.web;

import com.siavash.faas.watchdog.service.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProxyResource {

	private final ProxyService proxy;

	public ProxyResource(final ProxyService proxy) {
		this.proxy = proxy;
	}

	@PostMapping
	public ResponseEntity<String> exec(@RequestBody(required = false) String request) throws Exception {
		logger.info("received request in proxy endpoint with body: {}", request);

		String response = proxy.exec(request);

		logger.debug("returning response: {}", response);

		return ResponseEntity.ok(response);
	}

}
