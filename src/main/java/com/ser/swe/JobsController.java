package com.ser.swe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@RestController
public class JobsController {

	@Autowired
	private JobsService jobsService;

	@GetMapping("/")
	public String Hello() {
		return "SER 531 Project - Team 07 - Job Search Application";

	}

	@CrossOrigin(origins = "*")
	@GetMapping("/jobs")
	@JsonSerialize
	@JsonIgnoreProperties(ignoreUnknown = true)
	@ResponseBody
	public JobsCollection getJobs(@RequestParam(required = false) String city,
			@RequestParam(required = false) String company, @RequestParam(required = false) String title,
			@RequestParam(required = false) String industry, @RequestParam(required = false) String jobtype) {
		String nextLine = "\r\n";
		String queryStr = SparqlQuery.COMMON_PREFIX + nextLine;
		String jobServerQuery = null;
		
		if(city == null && company == null && title == null && industry == null && jobtype == null)
			jobServerQuery = String.format(SparqlQuery.JOBS_SERVICE, 100);
		else
			jobServerQuery = String.format(SparqlQuery.JOBS_SERVICE, 1000);

		if (city == null || company == null) {
			queryStr += SparqlQuery.OPEN_BRACKET + nextLine + jobServerQuery + nextLine
					+ SparqlQuery.LOCATION_SERVICE + nextLine + SparqlQuery.COMPANY_SERVICE + nextLine;
			if (city != null) {
				queryStr += String.format(SparqlQuery.CITY_FILTER, city) + nextLine;
			}
			if (company != null) {
				queryStr += String.format(SparqlQuery.COMPANY_FILTER, company) + nextLine;
			}
		} else {
			queryStr += SparqlQuery.WHERE_CLAUSE + nextLine + SparqlQuery.LOCATION_SERVICE + nextLine;
			if (city != null) {
				queryStr += String.format(SparqlQuery.CITY_FILTER, city) + nextLine;
			}
			queryStr += SparqlQuery.COMPANY_SERVICE + nextLine;
			if (company != null) {
				queryStr += String.format(SparqlQuery.COMPANY_FILTER, company) + nextLine;
			}
			queryStr += jobServerQuery + nextLine;
		}

		if (title != null) {
			queryStr += String.format(SparqlQuery.TITLE_FILTER, title) + nextLine;
		}
		if (industry != null) {
			queryStr += String.format(SparqlQuery.INDUSTRY_FILTER, industry) + nextLine;
		}
		if (jobtype != null) {
			queryStr += String.format(SparqlQuery.JOBTYPE_FILTER, jobtype) + nextLine;
		}
		queryStr += SparqlQuery.CLOSING_BRACKET;

		return jobsService.getJobCollection(queryStr);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/visualization/company")
	@JsonSerialize
	@JsonIgnoreProperties(ignoreUnknown = true)
	@ResponseBody
	public CompaniesCollection getCompanyVisualization(@RequestParam(required = false) String company) {
		String queryStr = String.format(SparqlQuery.COMPANY_VISUALIZATION_QUERY, company);
		return jobsService.getCompanyVisualization(queryStr);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/visualization/location")
	@JsonSerialize
	@JsonIgnoreProperties(ignoreUnknown = true)
	@ResponseBody
	public List<String> getLocationVisualization(@RequestParam(required = false) String city) {
		String queryStr = String.format(SparqlQuery.LOCATION_VISUALIZATION_QUERY, city);
		return jobsService.getLocationVisualization(queryStr);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/visualization/count")
	@JsonSerialize
	@JsonIgnoreProperties(ignoreUnknown = true)
	@ResponseBody
	public List<String> getOtherVisualization(@RequestParam(required = false) String type, String industry, String title) {
		String queryStr = null;
		if(type != null)
			queryStr = String.format(SparqlQuery.JOBTYPE_VISUALIZATION_QUERY, type);
		if(industry != null)
			queryStr = String.format(SparqlQuery.INDUSTRY_VISUALIZATION_QUERY, industry);
		if(title != null)
			queryStr = String.format(SparqlQuery.JOBTITLE_VISUALIZATION_QUERY, title);
		return jobsService.getCountVisualization(queryStr);
	}

}
