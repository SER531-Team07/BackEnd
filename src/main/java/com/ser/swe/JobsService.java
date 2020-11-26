package com.ser.swe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobsService {
	
	@Autowired
	private Ontology ontology;
	
	public JobsCollection getJobCollection() {
		return ontology.locationBasedSearch();
	}
}
