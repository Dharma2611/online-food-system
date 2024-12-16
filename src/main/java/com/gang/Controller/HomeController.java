package com.gang.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	@GetMapping
	public ResponseEntity<String> homePage() {
		return new ResponseEntity<String>(  "this is home page", HttpStatus.OK);
	}

}
