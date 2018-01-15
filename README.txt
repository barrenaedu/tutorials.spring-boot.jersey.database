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

Information of headers
    @Context HttpHeaders headers

WADL Generation
    http://localhost:8080/rest/application.wadl

More info
	https://jersey.github.io/
	https://github.com/jersey/jersey
	https://en.wikipedia.org/wiki/Project_Jersey
	https://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services
	https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
	http://www.massapi.com/class/te/TestRestTemplate.html
	http://docs.spring.io/autorepo/docs/spring-android/1.0.x/reference/html/rest-template.html

Injectable Interfaces
    https://docs.oracle.com/javaee/7/api/javax/ws/rs/core/package-summary.html

WADL
    http://localhost:8080/rest/application.wadl


@Component Inside MessagesResource class
-----------------------------------------
https://stackoverflow.com/questions/38133680/why-do-we-need-component-spring-annotation-for-jersey-resource-in-spring-boot-s

Curl
  $ curl -i -k -H "Content-Type: application/json" -X POST -d '{"text":"Message One"}' http://localhost:8080/rest/messages
  $ curl -i -k -H "Content-Type: application/json" -X POST -d '{"text":"Message Two"}' http://localhost:8080/rest/messages
  $ curl -i -k -H "Content-Type: application/json" -X POST -d '{"text":"Message Three"}' http://localhost:8080/rest/messages

  $ curl -i -k -H "Accept: application/json" http://localhost:8080/rest/messages
  $ curl -i -k -H "Accept: application/json" http://localhost:8080/rest/messages/1/

  $ curl -i -k -H "Content-Type: application/json" -X PUT -d '{"text":"Message One Modified"}' http://localhost:8080/rest/messages/1

  $ curl -i -k -X DELETE http://localhost:8080/rest/messages/1/

