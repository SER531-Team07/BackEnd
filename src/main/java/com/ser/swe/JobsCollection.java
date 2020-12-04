package com.ser.swe;

import java.util.ArrayList;
import java.util.List;


public class JobsCollection {
	
	List<Job> job = new ArrayList<>();

	@Override
	public String toString() {
		return "JobsCollection [job=" + job + "]";
	}
	
	public List<Job> getJob() {
		return job;
	}

	public void setJobs(List<Job> jobList) {
		job = jobList;
	}
}
