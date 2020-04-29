# Quarkus + 12 factor Microservice Template
This is a reference to start a small Java REST app that adheres to the principles of the 12 Factor App.

### Quarkus
SUPERSONIC SUBATOMIC JAVA

A Kubernetes Native Java stack tailored for OpenJDK HotSpot and GraalVM, crafted from the best of breed Java libraries and standards.

http://quarkus.io

### 12 Factors
The Twelve-Factor app is a methodology for apps that use declarative formats, have a clean contract, are suitable for modern cloud platforms, minimize divergence, and can scale up without significant changes.

https://12factor.net/

## To use

### Building the application

Launch the Maven build on the checked out sources of this demo:
```
./mvnw install
```

### Running the application in dev mode
You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

### Live coding with Quarkus
The Maven Quarkus plugin provides a development mode that supports
live coding. To try this out:
```
./mvnw quarkus:dev
```
This command will leave Quarkus running in the foreground listening on port 8080.

1. Visit the default endpoint: [http://127.0.0.1:8080](http://127.0.0.1:8080).
    - Make a simple change to [src/main/resources/META-INF/resources/index.html](src/main/resources/META-INF/resources/index.html) file.
    - Refresh the browser to see the updated page.
2. Visit the `/hello` endpoint: [http://127.0.0.1:8080/hello](http://127.0.0.1:8080/hello)
    - Update the response in [src/main/java/com/v2com/iws10/axon/template/service/Base.java](src/main/java/com/v2com/iws10/axon/template/service/Base.java). Replace `hello` with `hello there` in the `hello()` method.
    - Refresh the browser. You should now see `hello there`.
    - Undo the change, so the method returns `hello` again.
    - Refresh the browser. You should now see `hello`.

### Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `quarkus-12factor-service-template-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/quarkus-12factor-service-template-1.0-SNAPSHOT-runner.jar`.

Have a look at how fast it boots, or measure the total native memory consumption.

## To create

### A base REST service: (branch feature/BaseRest)
```
mvn io.quarkus:quarkus-maven-plugin:1.2.0.Final:create \
    -DprojectGroupId=com.v2com.iws10.axon.template-service \
    -DprojectArtifactId=quarkus-12factor-service-template \
    -DclassName="com.v2com.iws10.axon.template.service.base" \
    -Dpath="api/base"
```
- Create and mantain a README.MD.
- Add a `Jenkinsfile`. (copy from this project)

### Health: (branch feature/Health)
Add dependency:
```
./mvnw quarkus:add-extension -Dextensions="health"
```

Add the checkpoints to each resource as see in [src/main/java/com/v2com/iws10/axon/template/service/PingResourceHealthCheck.java](src/main/java/com/v2com/iws10/axon/template/service/PingResourceHealthCheck.java).

Check the `/health/live` endpoint: [http://127.0.0.1:8080/health/live](http://127.0.0.1:8080/health/live) to get liveness status.

Add the checkpoints to each dependency as see in [src/main/java/com/v2com/iws10/axon/template/service/DatabaseConnectionHealthCheck.java](src/main/java/com/v2com/iws10/axon/template/service/DatabaseConnectionHealthCheck.java).

Check the `/health/ready` endpoint: [http://127.0.0.1:8080/health/ready](http://127.0.0.1:8080/health/ready) to get readiness status.

Check the `/health` endpoint: [http://127.0.0.1:8080/health](http://127.0.0.1:8080/health) to get all health information.

And finaly add the unit tests as see in [src/test/java/com/v2com/iws10/axon/template/service/baseTest.java](src/test/java/com/v2com/iws10/axon/template/service/BaseTest.java).
 
### Rest Client: (branch feature/RestClient)
Add dependencies:
```
./mvnw quarkus:add-extension -Dextensions="rest-client"
./mvnw quarkus:add-extension -Dextensions="resteasy-jsonb"
```

Implement the service interface as see in [src/main/java/com/v2com/iws10/axon/template/service/client/CountriesService.java](src/main/java/com/v2com/iws10/axon/template/service/client/CountriesService.java).

To this case we use the model as see in [src/main/java/com/v2com/iws10/axon/template/service/client/Country.java](src/main/java/com/v2com/iws10/axon/template/service/client/Country.java).

And the resource as see in [src/main/java/com/v2com/iws10/axon/template/service/CountriesResource.java](src/main/java/com/v2com/iws10/axon/template/service/CountriesResource.java);

Define properties url and scope in [src/main/resources/application.properties](src/main/resources/application.properties).
example:
```
country-api/mp-rest/url=https://restcountries.eu/rest
country-api/mp-rest/scope=javax.inject.Singleton
```

This will consume the interface in [https://restcountries.eu/rest/v2/name/Brazil](https://restcountries.eu/rest/v2/name/Brazil) and you can see the result produced here [http://localhost:8080/country/name/Brazil](http://localhost:8080/country/name/Brazil).

### Log: (branch feature/Log)
Add the log configuration to [src/main/resources/application.properties](src/main/resources/application.properties).
```
quarkus.log.console.enable=true
quarkus.log.console.format=%d{HH:mm:ss} %-5p [%c{2.}] (%t) %s%e%n
quarkus.log.console.level=DEBUG
quarkus.log.console.color=true
```
The log level definition to org.apache.log4j.Level must follow this table:

| Level   | Description  | Use  | Value  |
|---|---|---|---|
| ALL  | All levels including custom levels.  | --  | Integer.MAX_VALUE  |
| TRACE  | Designates finer-grained informational events than the DEBUG.  | Use to show points of passage or inflections on code presenting aggregated data..  | 600 |
| DEBUG  | Designates fine-grained informational events that are most useful to debug an application.  |Use at every point of inflection in the system, *please!*  | 500 |
| INFO  | Designates informational messages that highlight the progress of the application at coarse-grained level.  | Use to register the passage of the system through a point and/or the important values​/status at that point.  | 400 |
| WARN  | Designates potentially harmful situations.  | *Always use* when a try..catch kills the error by not throwing an exception.  | 300 |
| ERROR  | Designates error events that might still allow the application to continue running.  | *Always use* when throwing an exception.  | 200 |
| FATAL  | Designates very severe error events that will presumably lead the application to abort.  | The version of log4j we use does not support this level.  | 100 |
| OFF  | The highest possible rank and is intended to turn off logging.  | --  |   0 |

### Checkstyle: (branch feature/Checkstyle)
Add checkstyle and adjuste the code.

Use the Google Java style(google_checks.xml)  [checkstyle.xml](checkstyle.xml).

Configure your IDE to 2 spaces indentation, set the use of * on a imported class or name to over 9999 and the order of imports to this:
```
import static all other imports
<blank line>
import all other imports
import java.*
import javax.*
import org.*
```

### Zookeeper: (branch feature/Zookeeper)
Add Apache Curator to manage zookeeper nodes and present some common uses:

Examples:
- Connection
- Configuration
- Typed Models

Recipes:
- Leader Election
- Lock Shared Resources
- Shared Counters
- Observers


