package com.cognizant.restClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.model.AuthRequest;
import com.cognizant.model.User;

@FeignClient(name = "authorizationService",url = "http://3.111.49.7:9090")
public interface AuthorizationClient {
	
	@PostMapping("/authenticate")
	public String login(@RequestBody AuthRequest authRequest) throws Exception; 
	
	@PostMapping("/register")
	public User register(@RequestBody User user) throws Exception; 


	@GetMapping("/authorize")
	public Boolean authorization(@RequestHeader("Authorization") String token);

}
