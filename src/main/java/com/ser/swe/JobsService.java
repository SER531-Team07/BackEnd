package com.ser.swe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobsService {
	
	@Autowired
	private Ontology ontology;
	
	public JobsCollection getJobCollection(String queryStr) {
		return ontology.executeQuery(queryStr);
	}
	
	public CompaniesCollection getCompanyVisualization(String queryStr) {
		return ontology.executeCompanyVisualization(queryStr);
	}
	
	public List<String> getLocationVisualization(String queryStr) {
		return ontology.executeLocationVisualization(queryStr);
	}
	
	public List<String> getCountVisualization(String queryStr) {
		return ontology.executeCountVisualization(queryStr);
	}
}
