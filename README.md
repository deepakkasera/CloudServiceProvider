# Integrate Cloud Service Provider for our Project.

## Problem Statement
You are building a Web Project. As a part of this system, you need to integrate AWS and Google as cloud service providers in to your system.

## Assignment

Your task is to integrate both AWS and Google cloud services in a SOLID compliant manner with the create Connection functionality of our system.

#### Requirements

1. Make connection request functionality will get user id as input.
2. If an invalid user id is given to this functionality, we should get an error saying "User not found".
3. When a valid user id is given, you need to get the user details stored in the database and make a call to cloud Providers (AWS or Google) to call the Cloud API's to initiate the connection. Once the processing is completes, it will return us some details about the connection, out of all these details we need to extract the connection id and connection status and return it in the response.
4. We need to implement the cloud providers integration in such a way that it should be very easy for us to migrate from 1 cloud to another.

#### Instructions

* Refer the `createConnection` method inside `CloudController` class.
* Refer the libraries package to understand the AWS and Google libraries.
* Refer the `CreateConnectionRequestDto` and `CreateConnectionResponseDto` for understanding the expected input and output to the functionality.
* Refer the `CloudAdapter` interface and see how it can be used in this scenario.
* Refer the models package to understand the models.
* Implement the `CloudService` and `UserRepository` interfaces to achieve the above requirements.
* Implement an in memory database for storing the bill details.
* Do not modify existing methods and their parameters for interfaces, feel free to add more methods if required.
