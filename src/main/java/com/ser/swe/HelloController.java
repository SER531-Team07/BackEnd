package com.ser.swe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@RestController
public class HelloController {
	
	@Autowired
	private JobsService jobsService;
	 
	@GetMapping("/hello")
	public String Hello() {
		return "SER 531 Project";
		
	}
	
	@CrossOrigin
	@GetMapping("/jobs")
	@JsonSerialize
	@JsonIgnoreProperties(ignoreUnknown = true)
	@ResponseBody
	public JobsCollection getJobs() {
		return jobsService.getJobCollection();
	}

}
