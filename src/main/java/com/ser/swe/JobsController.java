package com.ser.swe;

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

	@CrossOrigin
	@GetMapping("/jobs")
	@JsonSerialize
	@JsonIgnoreProperties(ignoreUnknown = true)
	@ResponseBody
	public JobsCollection getJobs(@RequestParam(required = false) String city,
			@RequestParam(required = false) String company, @RequestParam(required = false) String title,
			@RequestParam(required = false) String industry, @RequestParam(required = false) String jobtype) {
		String nextLine = "\r\n";
		String queryStr = SparqlQuery.COMMON_PREFIX + nextLine;
		if (city == null && company == null && title == null && industry == null && jobtype == null) {
			queryStr += SparqlQuery.OPEN_BRACKET + nextLine + SparqlQuery.JOBS_SERVICE + nextLine
					+ SparqlQuery.LOCATION_SERVICE + nextLine +SparqlQuery.COMPANY_SERVICE + nextLine
					+ SparqlQuery.CLOSING_BRACKET;
		} else {
			queryStr += SparqlQuery.WHERE_CLAUSE + nextLine + SparqlQuery.LOCATION_SERVICE;
			if (city != null) {
				queryStr += String.format(SparqlQuery.CITY_FILTER, city) + nextLine;
			}
			queryStr += SparqlQuery.COMPANY_SERVICE + nextLine;
			if (company != null) {
				queryStr += String.format(SparqlQuery.COMPANY_FILTER, company) + nextLine;
			}
			queryStr += SparqlQuery.JOBS_SERVICE + nextLine;
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
		}

		System.out.println(queryStr);

		return jobsService.getJobCollection(queryStr);
	}

}
