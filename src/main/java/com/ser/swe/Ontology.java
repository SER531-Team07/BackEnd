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
			inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException(
						"Property file '" + PROPERTIES_FILE_NAME + "' not found in the classpath");
			}
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

	public void readAllLocations() {
		QueryExecution qe = null;
		Query query = null;

		try {
			query = QueryFactory.create(SparqlQuery.QUERY_READ_ALL_JOBS);

			qe = QueryExecutionFactory.sparqlService(properties.getProperty(env + "server.jobs"), query);

			ResultSet results = qe.execSelect();

			while (results.hasNext()) {
				QuerySolution row = results.next();
				String subject = row.get("subject") != null ? row.get("subject").toString() : "NULL";
				String predicate = row.get("predicate") != null ? row.get("predicate").toString() : "NULL";
				String object = row.get("object") != null ? row.get("object").toString() : "NULL";
				log.info("-------------------------------------");
				log.info(subject);
				log.info(predicate);
				log.info(object);
				log.info("-------------------------------------");
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (qe != null) {
				qe.close();
			}
		}
	}

	public JobsCollection search() {
		QueryExecution qe = null;
		Query query = null;
		
		String jobsURL = properties.getProperty(env + "server.jobs");
		String locationsURL = properties.getProperty(env + "server.locations");
		String companiesURl = properties.getProperty(env + "server.companies");
		
		JobsCollection jobsCollection = new JobsCollection();
		
		List<Job> jobList = new ArrayList<>();

		try {
			query = QueryFactory.create(String.format(SparqlQuery.LOCATION_BASED_SEARCH, locationsURL, companiesURl, jobsURL));

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
	
	// Local testing
	public static void main(String[] args) {
		Ontology ontology = new Ontology();
		ontology.search();
	}
}
