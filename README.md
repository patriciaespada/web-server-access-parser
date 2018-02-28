# Parser for Web Server Access Log files


## Specifications

*   Maven
*   Java 8
*   Spring Boot 1.5.9
*	MySQL
*	Liquibase
*	Hibernate

## Init

The application runs over MySQL database. These are configurations defined to connect to the database:

> **address:** localhost:3306   
> **schema:** webserveraccessparser   
> **username:** root   
> **password:** password

If you have different configurations you can change the following properties file: `liquibase.properties` and `application.properies`.

Make sure you have MySQL running with the schema **webserveraccessparser** previously added. Then run the following command over the solution directory:
> mvn liquibase:update

## Run

To get the application up and running, you will need to:

1.  Compile the application (exec the command over the project root directory): `mvn clean package`
2.  Run the application:

	java -cp target/parser.jar com.ef.Parser --accesslog=access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
	The tool will find any IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00 (one hour) and print them to console AND also load them to another MySQL table with comments on why it's blocked.
	
	java -cp target/parser.jar com.ef.Parser --accesslog=access.log --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250
	The tool will find any IPs that made more than 250 requests starting from 2017-01-01.13:00:00 to 2017-01-02.13:00:00 (24 hours) and print them to console AND also load them to another MySQL table with comments on why it's blocked.
	
## SQL Test

* Write MySQL query to find IPs that made more than a certain number of requests for a given time period.

    SELECT ip , number_requests
	FROM (
		SELECT ip, COUNT(*) as number_requests
		FROM server_access 
		WHERE date >= '2017-01-01.13:00:00'
		AND date <= '2017-01-01.14:00:00'
		GROUP BY ip) results
	WHERE number_requests >= 100;

* Write MySQL query to find requests made by a given IP.

	SELECT * FROM server_access WHERE ip = '192.168.102.136';
	
