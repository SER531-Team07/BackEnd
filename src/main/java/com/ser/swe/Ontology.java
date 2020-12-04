package com.ser.swe;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class Ontology {

	private static final String PROPERTIES_FILE_NAME = "app.properties";
	private Logger log;
	private Properties properties;
	private String env = "";

	public Ontology() {
		log = LogManager.getLogger(this);
		properties = new Properties();
		readProperties();
		if (properties.getProperty("env").equalsIgnoreCase("DEV")) {
			env = "dev.";
		}
	}

	private void readProperties() {
		InputStream inputStream = null;
		try {
//			inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
//			if (inputStream != null) {
//				properties.load(inputStream);
//			} else {
//				throw new FileNotFoundException(
//						"Property file '" + PROPERTIES_FILE_NAME + "' not found in the classpath");
//			}
			properties.setProperty("env", "test");
			properties.setProperty("server.jobs", "http://ec2-18-223-22-133.us-east-2.compute.amazonaws.com:3030/Jobs");
			properties.setProperty("server.companies", "http://ec2-3-129-207-101.us-east-2.compute.amazonaws.com:3030/Companies");
			properties.setProperty("server.locations", "http://ec2-3-134-101-50.us-east-2.compute.amazonaws.com:3030/Locations");
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}
	
	public JobsCollection executeQuery(String queryStr) {
		QueryExecution qe = null;
		Query query = null;
		
		String jobsURL = properties.getProperty(env + "server.jobs");
		
		JobsCollection jobsCollection = new JobsCollection();
		
		List<Job> jobList = new ArrayList<>();

		try {
			query = QueryFactory.create(queryStr);

			qe = QueryExecutionFactory.sparqlService(jobsURL, query);

			ResultSet results = qe.execSelect();
			
			while (results.hasNext()) {
				QuerySolution row = results.next();
				Job job = new Job();
				
				String companyName = row.get("company_name") != null ? row.get("company_name").toString() : "N/A";
				String cityName = row.get("city_name") != null ? row.get("city_name").toString() : "N/A";
				String title = row.get("title") != null ? row.get("title").toString() : "N/A";
				String date = row.get("date") != null ? row.get("date").toString() : "N/A";
				String salary = row.get("salary") != null ? row.get("salary").toString() : "N/A";
				String link = row.get("link") != null ? row.get("link").toString() : "N/A";
				String type = row.get("type") != null ? row.get("type").toString() : "N/A";
				String industry = row.get("industry") != null ? row.get("industry").toString() : "N/A";
				
				job.setCityName(cityName);
				job.setCompanyName(companyName);
				job.setTitle(title);
				job.setDate(date);
				job.setSalary(salary);
				job.setLink(link);
				job.setType(type);
				job.setIndustry(industry);
				
				jobList.add(job);
				
			}
			jobsCollection.setJobs(jobList);
			
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (qe != null) {
				qe.close();
			}
		}
		return jobsCollection;
	}
	
	public CompaniesCollection executeCompanyVisualization(String queryStr) {
		QueryExecution qe = null;
		Query query = null;
		
		String jobsURL = properties.getProperty(env + "server.companies");
		
		CompaniesCollection companiesCollection = new CompaniesCollection();
		
		List<Company> companyList = new ArrayList<>();

		try {
			query = QueryFactory.create(queryStr);

			qe = QueryExecutionFactory.sparqlService(jobsURL, query);

			ResultSet results = qe.execSelect();
			
			while (results.hasNext()) {
				QuerySolution row = results.next();
				Company company = new Company();
				
				String companyName = row.get("company_name") != null ? row.get("company_name").toString() : "N/A";
				String ceoName = row.get("ceo_name") != null ? row.get("ceo_name").toString() : "N/A";
				String employees = row.get("employees") != null ? row.get("employees").toString() : "N/A";
				String count = row.get("count") != null ? row.get("count").toString() : "N/A";
				
				company.setCompanyName(companyName);
				company.setCeoName(ceoName);
				company.setEmployees(employees);
				company.setJobCount(count);
				
				companyList.add(company);
			}
			
			companiesCollection.setCompany(companyList);
			
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (qe != null) {
				qe.close();
			}
		}
		return companiesCollection;
	}
	
	public List<String> executeLocationVisualization(String queryStr) { 
		QueryExecution qe = null;
		Query query = null;
		
		String jobsURL = properties.getProperty(env + "server.locations");
		
		List<String> cities = new ArrayList<>();

		try {
			query = QueryFactory.create(queryStr);

			qe = QueryExecutionFactory.sparqlService(jobsURL, query);

			ResultSet results = qe.execSelect();
			
			while (results.hasNext()) {
				QuerySolution row = results.next();
				
				String cityName = row.get("cities") != null ? row.get("cities").toString() : "N/A";
				
				cities.add(cityName);
			}
			
			
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (qe != null) {
				qe.close();
			}
		}
		return cities;
	}
	
	public List<String> executeCountVisualization(String queryStr) {
		QueryExecution qe = null;
		Query query = null;
		
		String jobsURL = properties.getProperty(env + "server.jobs");
		
		List<String> count = new ArrayList<>();

		try {
			query = QueryFactory.create(queryStr);

			qe = QueryExecutionFactory.sparqlService(jobsURL, query);

			ResultSet results = qe.execSelect();
			
			while (results.hasNext()) {
				QuerySolution row = results.next();
				
				String cnt = row.get("count") != null ? row.get("count").toString() : "N/A";
				
				count.add(cnt);
			}
			
			
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (qe != null) {
				qe.close();
			}
		}
		return count;
	}
	
}
