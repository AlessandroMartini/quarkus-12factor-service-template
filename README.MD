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
cd service-template\
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
cd service-template\
./mvnw quarkus:dev
```
This command will leave Quarkus running in the foreground listening on port 8080.

1. Visit the default endpoint: [http://127.0.0.1:8080](http://127.0.0.1:8080).
    - Make a simple change to [src/main/resources/META-INF/resources/index.html](src/main/resources/META-INF/resources/index.html) file.
    - Refresh the browser to see the updated page.
2. Visit the `/hello` endpoint: [http://127.0.0.1:8080/hello](http://127.0.0.1:8080/hello)
    - Update the response in [base.java](base.java). Replace `hello` with `hello there` in the `hello()` method.
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

### A base REST service:
```
mvn io.quarkus:quarkus-maven-plugin:1.2.0.Final:create \
    -DprojectGroupId=com.v2com.iws10.axon.template-service \
    -DprojectArtifactId=quarkus-12factor-service-template \
    -DclassName="com.v2com.iws10.axon.template.service.base" \
    -Dpath="api/base"
```
- Create and mantain a README.MD.
- Add a `Jenkinsfile`. (copy from this project)





