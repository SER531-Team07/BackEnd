package com.ser.swe;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobsCollection {
	
	@Autowired
	private Ontology ontology;
	
	List<Job> job = new ArrayList<>();


	public JobsCollection searchQuery() {
		return ontology.locationBasedSearch();
	}
	
	public void setJobs(List<Job> jobList) {
		job = jobList;
	}
}
