#User API

Built with Java/Apache Maven and Spring Boot framework v2.2.2.RELEASE - https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/
```
Apache Maven 3.5.0 (ff8f5e7444045639af65f6095c62210b5713f426; 2017-04-03T20:39:06+01:00)
Java version: 11.0.2, vendor: Oracle Corporation
```

Build:

* Ensure environment is set up as above, i.e. the above maven/java versions are reported when ```mvn -version``` is issued at command prompt.
* Open command prompt at the ```user-api``` directory and execute ```mvn clean install```.
* Execute ```java -jar web-api/target/web-api-1.0-SNAPSHOT.jar```.
* API documentation can then be browsed at http://localhost:8080/swagger-ui/index.html?url=/v3/api-docs

TODOs

* Configure downstream connection timeouts
