#ASSECOR GmbH Challenge 

This is the challenge project of Ali Hlayel, created by the requirements defined in challenge.md. It contains 1 microservice which is implemnted by me.
I used IntelliJ IDE for development. Infrastructure is created over Spring Boot, Hibernate, MySql database(dev purpose) and  RESTFul services.

#Person-Service
Person microservice is built as RESTFul API. it contains methods about person details: 
One is for retrieving all persons.
Second find person by giving Id.
Third one git a list of persons details with given color.
Forth one is to create a new person.
and last one is to import list of people details from a csv file.

# Test
The code is already tested using Junit test. I also used swagger API for testing the project at the run time process. 
you can test the 

#Required endpoints
as shown in the attached figure, i built 5  controllers endpoints. 
you can use swagger to test the microservice controller by entering this link:
http://localhost:8080/swagger-ui.html#

<img width="1427" alt="Screenshot 2020-09-20 at 20 49 41" src="https://user-images.githubusercontent.com/68303228/93755349-67839700-fc03-11ea-860a-128a65550fed.png">


<img width="1427" alt="Screenshot 2020-09-20 at 20 48 58" src="https://user-images.githubusercontent.com/68303228/93754996-d4e2f800-fc02-11ea-83ea-746ddd5bcd84.png">
