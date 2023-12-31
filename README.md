**Receipt-Processor-Challenge**

Take-home backend challenge for - https://github.com/fetch-rewards/receipt-processor-challenge

Refer to [docs](https://github.com/jweilhammer/receipt-processor-challenge/tree/main/docs) from the above for API SPEC and model definition.

**Design decisions**

This challenge was implemented as a REST API with Java 8, Spring Boot. To save the state across requests the ask was to use an in-memory solutions
A static HashMap acts as the in-memory structure.

The App adheres to the required design components based on the requirements. It has a controller and service class but doesn't use a repository as we default to an in-memory store.
Due to the lack of repository structure, there are no DTO classes. The request Objects are used for calculating the point and are saved in the Receipt Object.
Similarly, the rules to award points are written as static methods in the Command Class that could be wholly applied or applied on its own if required.
All the Constants are defined under the utils package. 

Lombok is used to do basic code generations.

All classes are unit-tested with JUnit + Spring Boot testing utilities. PowerMock is used to test the static dependency and features.


Implicitly and Explicitly following design patterns are used - 
**Singleton** - @controller, @service,etc define a singleton object.
**Builder** - @Builder is used to support the Builder pattern via Lombok

**Requirements**
* Java 8
* Docker
* IDE that can process Lombok - Might have to install plugins
* Maven or utilize the maven wrapper (mvnw, or mvnw.cmd for Windows)
* Docker Desktop

**Running Locally**
Default port for receipt-processor is 8080, This can be updated from application.properties file (server.port=*) 
- [http://localhost:8080](http://localhost:8080)

**To Build the docker image**

- ``docker build -t receipt-processor:latest .``

To run 

- ``docker run -d -p 8080:8080 receipt-processor:latest``

For** Running the test**
- `` docker exec <containerId> mvn test``

**Run Manually**
if All the requirements are installed 

- ``mvn package -DskipTests``
- ``java -jar target/receipt-processor-0.0.1-SNAPSOT.jar``







