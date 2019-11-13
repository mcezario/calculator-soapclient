# calculator-soapclient
Project sample using spring-ws-core as SOAP client

## Premise

You must have docker and docker-compose installed.

If you have not installed, more details: https://docs.docker.com/install/#get-started.

## Installing stubmatic container

Perform container installation through docker. Run the command below:

`<repository> $` docker-compose -f stubmatic/docker-compose.yml up -d

## Running the application
1. Start Stubmatic. Run the command: ```docker start stubmatic```
2. Compile and Package: ```mvn clean package -DskipTests``` 
3. Start the application: ```java -jar target/calculator-soapclient-0.0.1-SNAPSHOT.jar``` or ```mvn spring-boot:run```
