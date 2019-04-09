package com.siavash.faas.gateway.api.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/system")
public class SystemResource {

	@PostMapping(path = "/alert")
	public ResponseEntity alert(@RequestBody Object request) {
		logger.info("alert received with body: {}", request);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
