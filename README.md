# Camunda Platform http connector usage example  

This example illustrates the invocation of a REST service from a service task in Camunda BPM using the http connector.
The example uses a Camunda [Spring Boot deployment](https://docs.camunda.org/manual/latest/user-guide/spring-boot-integration/) but should work in the same way on other deployments.
 
No Java code is required. If you are comfortable with some Java / Spring then the recommended approach is to use a Spring bean instead of the connector, 
as shown in the example [rest-service-task-spring](https://github.com/camunda-consulting/code/tree/master/snippets/rest-service-task-spring).
Spring beans are more flexible, e.g. allow to add custom error handling, and are easier testable.

The service invoked by the example is hosted at [reqres.in](https://reqres.in/). Many thanks to Ben Howdle for providing this developer playground.

The project includes two example process models, one with a GET and one with a POST.

### The GET example illustrates how to 
- configure the connector
- add the value of a process variable to the url
- read a specific field from the REST service's  JSON response using Camunda Spin
- print the email field extracted from the response to the console using a script on the end event
- test the process from jUnit using [Camunda BPM Assert](https://docs.camunda.org/manual/latest/user-guide/testing/#camunda-assertions)


### The POST  example illustrates how to
- configure the connector
- add an authorization header fro http basic authentication if needed
- add the value of a process variable to the message body (payload)
- save the response
- print the response to the console using a script on the end event
- test the process from jUnit using [Camunda BPM Assert](https://docs.camunda.org/manual/latest/user-guide/testing/#camunda-assertions)


Also see related [product documentation](https://docs.camunda.org/manual/latest/reference/connect/http-connector/)

## The important parts

### GET Example

On the service task set the implementation to "Connector". The set the connector properties (method, headers, url) on the appearing tab as shown.
A response of type JSON can be read and navigated using JavaScript or an expression (shown).   
[BPMN Process](src/main/resources/httpConnector.bpmn)

Connector configuration in CAMUNDA Modeler
![Connector configuration in CAMUNDA Modeler](resources/images/process.png)

The prepackaged distribution includes everything needed. 

If you are starting form a Spring Boot project, you need to include the dependencies for connect, http-client, Spin and Json: 
```xml
   <dependencies>
...
    <dependency>
            <groupId>org.camunda.bpm</groupId>
            <artifactId>camunda-engine-plugin-connect</artifactId>
        </dependency>
        <dependency>
            <groupId>org.camunda.connect</groupId>
            <artifactId>camunda-connect-http-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.camunda.bpm</groupId>
            <artifactId>camunda-engine-plugin-spin</artifactId>
        </dependency>
        <dependency>
            <groupId>org.camunda.spin</groupId>
            <artifactId>camunda-spin-dataformat-json-jackson</artifactId>
        </dependency>
...
    </dependencies>
```

### POST Example

Set the method value to POST instead and create an input parameter named  **payload** for the request body.
Add a header field **Authorization** if needed. The value for this filed needs to be base 64 encoded and can be created for instance using these commands:

Powershell: ```[System.Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes($user+":"+$pass))```  

Linux: ```echo “username:password” | base64```


Connector configuration for POST in CAMUNDA Modeler
![Connector configuration for POST in CAMUNDA Modeler](resources/images/POSTConfig.png)


### Unit Test
Run the [jUnit test](src/test/java/processtest/ProcessUnitTest.java) in your IDE or using:
```
mvn clean test
```

### Step through the process manually 
You can start the server using your IDE or 
```
mvn spring-boot:run
```
and step through the process manually. After the server has started you can access the Camunda tasklist via http://localhost:8080/app/tasklist.
Use the credentials *demo / demo* to login.


### Run and inspect with Tasklist and Cockpit
Use tasklist and cockpit as described in the product documentation for
- [Camunda Tasklist](https://docs.camunda.org/manual/latest/webapps/tasklist)
- [Camunda Cockpit](https://docs.camunda.org/manual/latest/webapps/cockpit)


## License
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
