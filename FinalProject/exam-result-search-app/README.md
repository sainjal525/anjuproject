## BATCH-3:
### Anjali reddy saddi
### charan mangala
### Paramesh


## Step-1: to create a maven project:
Run the following command in the directory where you want to create the project mvn archetype:generate -DgroupId=com.batch-8.project -DartifactId=exam-result-search-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

## Step-2: Importing the project and creating the modules in the Intellij:
Now import the maven project into the Intellij and create two modules: name the first module as "student-result", which will be our first Microservice now name the another module as "result-processor", which will be our second Microservice

## Step-3: Creating a docker-compose file:
create a file named as "docker-compose.yml" which will contain the services we will be using in our project, in our case it will have two services first one being a rabbitMq service (rabbitmq:3-management) second one being a database service which is MySql (mysql:8.0-oracle), this will be our database

## Step-4: Creating Microservice-1 (student-result):
give the "@SpringBootApplication" annotation to the spring app.

### creating student class (model):
Microservice-1 will be having a user controller which will be having access to only get request's. annotate the controller with "@RestController". Now create a model(Student) class which consists of total eight variables which are private.
Annotate the class with "@JsonIdentityInfo" annotation. now generate the following things in the "Student" class

a default constructor
a parameterized constructor
getters and setters for all the variables
a toString method
### creating configuration for REQUEST QUEUE (requestQConfig or responseQConfig):
give the properties like Queue name, Exchange name, Routing key name
Now create a new method with return type as "Queue" and return the Queue name inside the method and name the method as "queue".
Now create another new method with return type as "DirectExchange" and return the Exchange name inside the method and name the method as exchange.
Now create another new method with return type as "Binding" inside this method bind the Queue and Exchange with the Routing key and return it, name the method as "binding".
Now create another new method with return type as "MessageConvertor" and give the "Jackson2JsonMessageConverter" as convertor and return this, name this method as a "jsonMessageConverter".
Now create another method AmpqTemplate with ConnectionFactory as argument type and set the MessageConvertor as jsonMessageConverter.
Now give "@Bean" Annotations to all the methods and give the "@Configuration" Annotation to the class.

## Step-5: Creating Microservice-2 (result-processor):
Microservice-2 will be having a admin controller which will be having access to post, put, delete requests. 
As we are using MySql as our database we should annotate the student class with "@Entity" annotation, then our class will be considered as a table we have to give one variable as a primary key. 
As we are accessing the all the requests operations using student we will be giving the name variable with "@Id" annotation. now generate the getters, setters for all the variables and generate a default constructor, a parameterized constructor and a toString method.
As our Microservice-2 will be interacting with the database, we should have a repository(ResultsRepository) interface extending the JpaRepository, so that we can use all the methods available in the jpa repository to perform the CRUD operations on our database.
Annotate the ResultsRepository interface with a "@Repository" annotation, so that spring will consider it as a repository

## Step-6 Creating Queue between the two Microservices:
As our Microservices will be communicating with each other, each Microservice should have a sender and a receiver we will be using two Queues for our Microservices to communicate with each other

"request.queue" in this Queue "student-result" will act as sender which will be sending the student name and "result-processor" will act as receiver.
"response.queue" in this Queue "result-processor" will act as a sender which will be sending the student result of the student name that it has received and "student-result" will be acting as a receiver
"requestQConfig" class will be used as configuration class for the sending the student name (this class will be containing all the configurations(beans) which will be used during the run time) "requestQSender" class will be sending the student name to the "requestQConsumer".
"requestQConsumer" class will be annotated with "@Service" annotation which will be receiving the student name and will be fetching the student result and invokes the "responseQSender" "responseQSender" class will send the student result to the "responseQConsumer"

## Step-7 Providing the Authentication for the Mappings:
Using the "SpringSecurityBasicConfig" class which extends the "WebSecurityConfigurerAdapter" class. using this we create two roles 1.student role 2.admin role by using this class we will make the Microservice-1 to have access to only "GET" requests for the student role and we will grant the "POST, PUT, DELETE" Mapping requests to the admin role

## Step-8 Giving the application properties:
As in Microservice-1 we are using "rabbitMq" we will give the host, username, password details in the application.properties file give the "server.port=8081" (because of our project requirement) for Microservice-1 In Microservice-2 we are using "rabbitMq" and "MySql" we will the properties related to those services give the "server.port=8082" (because of our project requirement) for Microservice-2

## Step-9 Running the Application:
first run the "mvn clean install" command in the terminal (the path should be the root folder of the project)
now run the docker command "docker-compose -f docker-compose.yml up -d", which will automatically download the images mentioned in the file and runs docker container with those images
now open another terminal(the path should the root of your Microservice-1) and run the "mvn clean install" after the build is success run the "mvn spring-boot:run" command to run our Microservice-1
now open another terminal(the path should the root of your Microservice-2) and run the "mvn clean install" after the build is success run the "mvn spring-boot:run" command to run our Microservice-2
now after both the Microservices are up and running open the cygwin and perform the CURL commands which are mentioned in the Step-10

## Step-10 Curl Commands:

### FOR POST REQUEST:
curl --location --request POST 'localhost:8082/storeresult' \
--header 'Authorization: Basic c2Nob29sYWRtaW4xOnBhc3N3b3JkMTI0' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=50C81D1E1E683C25AB6AF7A2049C1F2B' \
--data-raw '{
"name": "shyam",
"role": "student",
"schoolName": "rbps",
"scienceMarks":"40",
"mathMarks": "45",
"socialScienceMarks": "40",
"artMarks": "45",
"sportMarks":"40"
}'


### FOR GET REQUEST:
curl --location --request GET 'localhost:8081/getresult?name=shyam' \
--header 'Authorization: Basic c3R1ZGVudDE6cGFzc3dvcmQxMjM=' \
--header 'Cookie: JSESSIONID=9C6DC4AA90CD16B099F8C2DF61D53200'

### FOR PUT REQUEST:
curl --location --request PUT 'localhost:8082/updateresult?name=shyam' \
--header 'Authorization: Basic c2Nob29sYWRtaW4xOnBhc3N3b3JkMTI0' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=EAB10EE437B74548C39E40C05F601C2C' \
--data-raw '{
"name": "shyam",
"role": "student",
"schoolName": "ABCD",
"scienceMarks":"40",
"mathMarks": "45",
"socialScienceMarks": "40",
"artMarks": "45",
"sportMarks":"40"
}'

### FOR DELETE REQUEST:
curl --location --request DELETE 'localhost:8082/deleteresult?name=shyam' \
--header 'Cookie: JSESSIONID=EAB10EE437B74548C39E40C05F601C2C'

## Step-11: Creating a Docker Image:

run this command to build a docker image "docker build -t shyamd7/miniproject:1.0.0 ." .
Now create a repository with a name "exam-result-search-app" in the docker hub.
Now run the "docker login" command and enter the login credentials, after getting login succeeded message move on to next step.
Now to push the image into the docker hub use the following command "docker push shyamd7/miniproject:1.0.0" .
Now use the following command to pull the docker image "docker pull shyamd7/miniproject:1.0.0" .

## MAVEN COMMANDS:

run the below command in the Terminal at the root path of the project
### mvn clean install
after getting the build is success message, 
open another terminal and make the student-result microservice as current directory and run the below command in that terminal
### mvn clean install
after getting the build is success message, 
run the below docker command to download the docker images of mysql, rabbitMq and run the docker container with those images.
### docker-compose -f docker-compose.yml up -d
after the both the containers are up and running, run the below command to run the student-result Microservice
### mvn spring-boot:run

after the Microservice-1 is up and running open another Terminal and run the below commands
open another terminal and make the result-processor microservice as current directory and run the below command in that terminal
### mvn clean install
after getting the build is success message,
run the below command to check weather both the containers are up and running or not
### docker ps
If the both the containers are up and running, run the below command to run the result-processor Microservice
### mvn spring-boot:run

Now your both the Microservices are up and running, you can test your application with the CURL commands given in the Step-10 (or) you can use the Postman
