package com.ser.swe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobsService {
	
	@Autowired
	private Ontology ontology;
	
	public JobsCollection getJobCollection(String queryStr) {
		return ontology.executeQuery(queryStr);
	}
}
