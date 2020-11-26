package com.ser.swe;

public class SparqlQueries {
		String COMMON_PREFIX = 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
					"PREFIX companies: <http://www.semanticweb.org/SER531/ontologies/Team-7/Companies#>\r\n" + 
					"PREFIX jobs: <http://www.semanticweb.org/SER531/ontologies/Team-7/Jobs#>\r\n" + 
					"PREFIX locations: <http://www.semanticweb.org/SER531/ontologies/Team-7/Locations#>\r\n" + 
					"\r\n";
		
		String JOBS_SERVICE = 
				"SERVICE <http://ec2-18-223-22-133.us-east-2.compute.amazonaws.com:3030/Jobs> {\r\n" + 
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
				"    	LIMIT 100\r\n" + 
				"              }";
		
		String LOCATION_SERVICE = 
				"SERVICE <http://ec2-3-134-101-50.us-east-2.compute.amazonaws.com:3030/Locations> {\r\n" + 
				"			?location locations:location_id ?city_id ;\r\n" + 
				"					  locations:location_name ?city_name .\r\n" + 
				"			}";
		
		String COMPANY_SERVICE = 
				"SERVICE <http://ec2-3-129-207-101.us-east-2.compute.amazonaws.com:3030/Companies> {\r\n" + 
				"			?company companies:company_id ?company_id ;\r\n" + 
				"				 companies:company_name ?company_name .\r\n" + 
				"			}";
		
		String CITY_FILTER = "FILTER(?city_name=%s)";
		
		String COMPANY_FILTER = "FILTER(?company_name=%s)";
		
		String JOBTYPE_FILTER = "FILTER(?type=%s)";
		
		String TITLE_FILTER = "FILTER(?title=%s)";
		
		String INDUSTRY_FILTER = "FILTER(?industry=%s)";
}
