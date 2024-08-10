package com.team2.resumeeditorproject.monitoring;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
public class HealthCheckController {

	@GetMapping
	public ResponseEntity<Void> healthCheck() {
		return ResponseEntity.ok().build();
	}
}
