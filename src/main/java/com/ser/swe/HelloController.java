package com.ser.swe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@Autowired
	private JobsCollection jc;
	 
	@GetMapping("/hello")
	public String Hello() {
		return "SER 531 Project";
		
	}
	
	@CrossOrigin
	@GetMapping("/jobs")
	public JobsCollection getJobs() {
		return jc.searchQuery();
		
	}

}
