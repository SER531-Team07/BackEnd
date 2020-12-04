package com.ser.swe;

public class SparqlQuery {

	public static final String COMMON_PREFIX = 
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
				"PREFIX companies: <http://www.semanticweb.org/SER531/ontologies/Team-7/Companies#>\r\n" + 
				"PREFIX jobs: <http://www.semanticweb.org/SER531/ontologies/Team-7/Jobs#>\r\n" + 
				"PREFIX locations: <http://www.semanticweb.org/SER531/ontologies/Team-7/Locations#>\r\n" + 
				"\r\n" +
				"SELECT DISTINCT ?company_name ?city_name ?salary ?link ?title ?date ?type ?industry";
	
	public static final String OPEN_BRACKET = "{";
	
	public static final String CLOSING_BRACKET = "}";
	
	public static final String WHERE_CLAUSE = "WHERE {";
	
	public static final String JOBS_SERVICE = 
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
			"    	LIMIT %d\r\n" + 
			"              }";
	
	public static final String LOCATION_SERVICE = 
			"SERVICE <http://ec2-3-134-101-50.us-east-2.compute.amazonaws.com:3030/Locations> {\r\n" + 
			"			?location locations:location_id ?city_id ;\r\n" + 
			"					  locations:location_name ?city_name .\r\n" + 
			"			}";
	
	public static final String COMPANY_SERVICE = 
			"SERVICE <http://ec2-3-129-207-101.us-east-2.compute.amazonaws.com:3030/Companies> {\r\n" + 
			"			?company companies:company_id ?company_id ;\r\n" + 
			"				 companies:company_name ?company_name .\r\n" + 
			"			}";
	
	public static final String CITY_FILTER = "FILTER(regex(?city_name, '%s', 'i'))";
	
	public static final String COMPANY_FILTER = "FILTER(regex(?company_name, '%s', 'i'))";
	
	public static final String JOBTYPE_FILTER = "FILTER(regex(?type, '%s', 'i'))";
	
	public static final String TITLE_FILTER = "FILTER(regex(?title, '%s', 'i'))";
	
	public static final String INDUSTRY_FILTER = "FILTER(regex(?industry ,'%s', 'i'))";
	
	public static final String COMPANY_VISUALIZATION_QUERY = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
			"PREFIX companies: <http://www.semanticweb.org/SER531/ontologies/Team-7/Companies#>\r\n" + 
			"PREFIX jobs: <http://www.semanticweb.org/SER531/ontologies/Team-7/Jobs#>\r\n" + 
			"PREFIX locations: <http://www.semanticweb.org/SER531/ontologies/Team-7/Locations#>\r\n" + 
			"\r\n" + 
			"SELECT ?company_name ?ceo_name ?employees (STR(?count1) AS ?count) {\r\n" + 
			"  	SERVICE <http://ec2-3-129-207-101.us-east-2.compute.amazonaws.com:3030/Companies> {\r\n" + 
			"  	SELECT ?company_id ?company_name ?ceo_name ?employees\r\n" + 
			"	WHERE {\r\n" + 
			"      		?company companies:company_id ?company_id ;\r\n" + 
			"      				 companies:company_name ?company_name ;\r\n" + 
			"      				 companies:ceo_name ?ceo_name ;\r\n" + 
			"      				 companies:number_of_employees ?employees .\r\n" + 
			"      		FILTER (regex(?company_name, '%s', 'i'))\r\n" + 
			"    	}\r\n" + 
			"	}\r\n" + 
			"	SERVICE <http://ec2-18-223-22-133.us-east-2.compute.amazonaws.com:3030/Jobs> {\r\n" + 
			"    SELECT ?company_id (count(?job) as ?count1)\r\n" + 
			"    WHERE {\r\n" + 
			"         	?job jobs:posted_by ?company_id.\r\n" + 
			"       }\r\n" + 
			"    GROUP BY(?company_id)\r\n" + 
			"    }\r\n" + 
			"}\r\n";
	
	public static final String LOCATION_VISUALIZATION_QUERY = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
			"PREFIX locations: <http://www.semanticweb.org/SER531/ontologies/Team-7/Locations#>\r\n" + 
			"\r\n" + 
			"SELECT (STR(?cities1) AS ?cities) {\r\n" + 
			"  SERVICE <http://ec2-3-134-101-50.us-east-2.compute.amazonaws.com:3030/City> {\r\n" + 
			"        SELECT ?state WHERE {\r\n" + 
			"  		?state locations:has_city ?city . \r\n" + 
			"  		FILTER(regex(xsd:string(?city), '%s', 'i'))\r\n" + 
			"    }\r\n" + 
			"    }\r\n" + 
			"    SERVICE <http://ec2-3-134-101-50.us-east-2.compute.amazonaws.com:3030/City> {\r\n" + 
			"        SELECT ?state2 ?cities1 WHERE {\r\n" + 
			"  		?state2 locations:has_city ?cities1 . \r\n" + 
			"     }\r\n" + 
			"    \r\n" + 
			"  }\r\n" + 
			"  FILTER(?state2 = ?state)\r\n" + 
			"}\r\n";
	
	public static final String JOBTYPE_VISUALIZATION_QUERY = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
			"PREFIX jobs: <http://www.semanticweb.org/SER531/ontologies/Team-7/Jobs#>\r\n" + 
			"\r\n" + 
			"SELECT ?type (STR(?count1) AS ?count) {\r\n" + 
			"	SERVICE <http://ec2-18-223-22-133.us-east-2.compute.amazonaws.com:3030/Jobs> {\r\n" + 
			"		SELECT ?type (count(?job) as ?count1)\r\n" + 
			"		WHERE {\r\n" + 
			"				?job jobs:type ?type.\r\n" + 
			"				FILTER (?type = '%s')\r\n" + 
			"		   }\r\n" + 
			"		GROUP BY(?type)\r\n" + 
			"	}\r\n" + 
			"}\r\n";
	
	public static final String INDUSTRY_VISUALIZATION_QUERY = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
			"PREFIX jobs: <http://www.semanticweb.org/SER531/ontologies/Team-7/Jobs#>\r\n" + 
			"\r\n" + 
			"SELECT ?industry (STR(?count1) AS ?count) {\r\n" + 
			"	SERVICE <http://ec2-18-223-22-133.us-east-2.compute.amazonaws.com:3030/Jobs> {\r\n" + 
			"		SELECT ?industry (count(?job) as ?count1)\r\n" + 
			"		WHERE {\r\n" + 
			"				?job jobs:belongs_to_industry ?industry .\r\n" + 
			"      	FILTER (regex(?industry, '%s', 'i'))\r\n" + 
			"		}\r\n" + 
			"		GROUP BY(?industry)\r\n" + 
			"	}\r\n" + 
			"}\r\n";
	
	public static final String JOBTITLE_VISUALIZATION_QUERY = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
			"PREFIX jobs: <http://www.semanticweb.org/SER531/ontologies/Team-7/Jobs#>\r\n" + 
			"\r\n" + 
			"SELECT ?title (STR(?count1) AS ?count) {\r\n" + 
			"	SERVICE <http://ec2-18-223-22-133.us-east-2.compute.amazonaws.com:3030/Jobs> {\r\n" + 
			"		SELECT ?title (count(?job) as ?count1)\r\n" + 
			"		WHERE {\r\n" + 
			"				?job jobs:has_title ?title .\r\n" + 
			"      	FILTER (regex(?title, '%s', 'i'))\r\n" + 
			"		}\r\n" + 
			"		GROUP BY(?title)\r\n" + 
			"	}\r\n" + 
			"}\r\n"; 
	
}
