package com.ser.swe;

public class SparqlQuery {
	
	private SparqlQuery() {
		
	}

	public static final String QUERY_READ_ALL_JOBS = "SELECT ?subject ?predicate ?object\r\n" + 
			"WHERE {\r\n" + 
			"  ?subject ?predicate ?object\r\n" + 
			"}\r\n" + 
			"LIMIT 25";
	
	public static final String LOCATION_BASED_SEARCH = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
			"PREFIX companies: <http://www.semanticweb.org/SER531/ontologies/Team-7/Companies#>\r\n" + 
			"PREFIX jobs: <http://www.semanticweb.org/SER531/ontologies/Team-7/Jobs#>\r\n" + 
			"PREFIX locations: <http://www.semanticweb.org/SER531/ontologies/Team-7/Locations#>\r\n" + 
			"\r\n" + 
			"SELECT DISTINCT ?company_name ?city_name ?salary ?link ?title ?date ?type ?industry\r\n" + 
			"WHERE {	\r\n" + 
			"	{\r\n" + 
			"      SERVICE <http://ec2-3-134-101-50.us-east-2.compute.amazonaws.com:3030/Locations> {\r\n" + 
			"			?location locations:location_id ?city_id ;\r\n" + 
			"					  locations:location_name ?city_name .\r\n" + 
			"			}\r\n" + 
			"    FILTER(?city_name='San Jose')\r\n" + 
			"		SERVICE <http://ec2-3-129-207-101.us-east-2.compute.amazonaws.com:3030/Companies> {\r\n" + 
			"			?company companies:company_id ?company_id ;\r\n" + 
			"				 companies:company_name ?company_name .\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
//			"  FILTER(?company_name='Twimm')\r\n" + 
			"  \r\n" + 
			"        SERVICE <http://ec2-18-223-22-133.us-east-2.compute.amazonaws.com:3030/Jobs> {\r\n" + 
			"              SELECT ?company_id ?city_id ?salary ?link ?title ?date ?type ?industry	\r\n" + 
			"                WHERE {\r\n" + 
			"                  ?job jobs:posted_by ?company_id ;\r\n" + 
			"                           jobs:located_in ?city_id ;\r\n" + 
			"                           jobs:has_salary ?salary ;\r\n" + 
			"                           jobs:application_link ?link ;\r\n" + 
			"                           jobs:has_title ?title ;\r\n" + 
			"                           jobs:posted_on ?date ;\r\n" + 
			"                           jobs:type ?type ;\r\n" + 
			"                           jobs:belongs_to_industry ?industry .\r\n" + 
			"                  }\r\n" + 
			"              }\r\n" + 
			"		\r\n" + 
			"	}";

}
