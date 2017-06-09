Examples
	http://howtodoinjava.com/jersey/jersey-restful-client-examples/#get-list

Jersey Reference
	http://docs.spring.io/autorepo/docs/spring-boot/1.5.3.RELEASE/reference/html/boot-features-developing-web-applications.html#boot-features-jersey

Testing
	http://docs.spring.io/autorepo/docs/spring-boot/1.5.3.RELEASE/reference/html/

DAO Recommended practices
	http://www.oracle.com/technetwork/java/dataaccessobject-138824.html

Rest returned codes
	http://www.restapitutorial.com/lessons/restquicktips.html
	http://www.restapitutorial.com/lessons/httpmethods.html

Json
	http://tutorials.jenkov.com/java-json/jackson-objectmapper.html

More info
	https://jersey.github.io/
	https://github.com/jersey/jersey
	https://en.wikipedia.org/wiki/Project_Jersey
	https://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services
	https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
	http://www.massapi.com/class/te/TestRestTemplate.html
	http://docs.spring.io/autorepo/docs/spring-android/1.0.x/reference/html/rest-template.html
	
	
<jersey.version>2.25.1</jersey.version>
	
	
================================================0

- Parameter annotations (P20)
- Scope of the root-resource classes (P29)

p35


mvn archetype:generate -DgroupId=spring.rest -DartifactId=spring.rest.simple-consumer -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

{accept=[application/json], user-agent=[Jersey/2.25 (HttpUrlConnection 1.8.0_131)], host=[localhost:8080], connection=[keep-alive]}
{accept=[application/xml], user-agent=[Jersey/2.25 (HttpUrlConnection 1.8.0_131)], host=[localhost:8080], connection=[keep-alive]}
{accept=[text/xml], user-agent=[Jersey/2.25 (HttpUrlConnection 1.8.0_131)], host=[localhost:8080], connection=[keep-alive]}


@Context HttpHeaders headers
