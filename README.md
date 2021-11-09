# Create Testing Environment with Docker for API Testing

# User Story
As a Merchant

In order to revert a previous transaction

I need to be able to refund a charge operation by its id.

# Acceptance criteria
API should expose the following endpoint POST /operations/{id}/refund
The "id" should be a valid uuid v4 (ex. d1e90d8f-11f7-41e0-92ff-235e2a85ab3b) otherwise the response status is 400
Only one concurrent refund operation can be performed, so the resource should be blocked if another refund is being processed
If the resource is blocked, the response status is 423
A successful response status is 201

### Building application and running tests with Docker

$ git clone 
$ cd 
$ import project from intellij as a gradle project
$ gradle clean
$ gradle build
$ gradle task E2E
$ gradle allureReport
$ gradle allureServe

#### Docker Compose

`
$ docker-compose up -d
```
Docker Container should start successfully

You should be able to access http://localhost:8088/(a Spring whitelabel error is expected)

Execution is only possible when valid refund POST request is provided
 -- API expose POST /operations/{id}/refund
 -- Test for valid uuid
 -- Test for resource blocked in case of second refund with same id
 -- Test for success POST 

 # Report
 - Can be found Reports/ExtentReportResults.html
