# Employee-Management

#### Application for managing Employee and his state based on predefined state transition rules as State machine.


## API Spec

### /employees

#### POST
##### Summary:

add new employee

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | CREATED |
| 400 | Bad Request |
| 500 | Internal Server Error |

### /employees/{id}

#### GET
##### Summary:

Get Employee

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Employee Id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | SUCCESS |
| 400 | Bad Request |
| 500 | Internal Server Error |

#### PUT
##### Summary:

Update status of an employee

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Employee Id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | SUCCESS |
| 400 | Bad Request |
| 500 | Internal Server Error |



## State Machine 

![state diagram](src/main/resources/img.png?raw=true "Statemachine diagram")

### requirement to set up the service

- Java 11
- maven
- postman to import emApi.yaml as postman collection 
    to test the application
- docker

### Step 1  *build the service using maven (packaging)*
```bash
    mvn clean package
```
### Step 2 - *run the service locally *
```bash
    java -jar ./target/employee-management-1.0.1-SNAPSHOT.jar
```
### Step 3  [optional] dockerized and run service as docker container
- build the docker image: 
```bash
    docker build .
```
- run the docker container:
```bash
    docker run $dockerContainerId
```
### Step 4  *test the service*
- import [emApi.yaml](OpenAPI/emApi.yaml) as postman collection
- change the baseUrl var on postman with your server url and running port number example
  http://localhost:8080
- test all endpoints

### Step 5  *review the service documentation*
- you can review api specs [emApi.yaml](OpenAPI/emApi.yaml)
  on swagger editor.
