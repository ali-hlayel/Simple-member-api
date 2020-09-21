# ASSECOR GmbH Challenge 

This is the challenge project of Ali Hlayel, created by the requirements defined in challenge.md. It contains 1 microservice which is implemnted by me.
I used IntelliJ IDE for development. Infrastructure is created over Spring Boot, Hibernate, h2 database(dev purpose) and  RESTFul services.

# Person-Service
Person microservice is built as RESTFul API. it contains methods about person details: 
One is for retrieving all persons.
Second find person by giving Id.
Third one git a list of persons details with given color.
Forth one is to create a new person.
and last one is to import list of people details from a csv file.

# Getting started
The person-service is built using docker. The root container contains the Dockerfile which java 11 container and copy the 
person-service jar files inside. Also the  docker-compose.yml describes the configuration of service. 
The components will be run in a local environment.

To run the service execute execute ./build-all.sh shell script in the root directory of project in the terminal.

# Test
The code is already tested using Junit test. I also used swagger API for testing the project at the run time process. 
you can test the service using swagger by entering the following link: 
http://localhost:8080/swagger-ui.html#

# Required endpoints
as shown in the attached figure, i built 5  controllers endpoints. 
The following pictures shows the test results for the service

<img width="1427" alt="Screenshot 2020-09-20 at 20 49 41" src="https://user-images.githubusercontent.com/68303228/93755349-67839700-fc03-11ea-860a-128a65550fed.png">


<img width="1427" alt="Screenshot 2020-09-20 at 20 48 58" src="https://user-images.githubusercontent.com/68303228/93754996-d4e2f800-fc02-11ea-83ea-746ddd5bcd84.png">

<img width="1427" alt="Screenshot 2020-09-20 at 20 51 21" src="https://user-images.githubusercontent.com/68303228/93755627-ebd61a00-fc03-11ea-8d71-7832bc189a33.png">
<img width="1427" alt="Screenshot 2020-09-20 at 20 52 11" src="https://user-images.githubusercontent.com/68303228/93755980-99e1c400-fc04-11ea-9add-e7ee06a5bbb0.png">

<img width="1427" alt="Screenshot 2020-09-20 at 20 51 41" src="https://user-images.githubusercontent.com/68303228/93755803-39528700-fc04-11ea-892e-de03c1287f57.png">

<img width="1427" alt="Screenshot 2020-09-20 at 20 50 49" src="https://user-images.githubusercontent.com/68303228/93755443-9863cc00-fc03-11ea-9be6-6c1cb8f3e79b.png">

