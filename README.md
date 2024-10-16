# Fraud Detection Management API

Welcome to the Fraud Detection Management API! This project is a demo showing how to use Apache Camel with different
input sources. 

Namely:

- Apache Kafka
- REST API

It mimics a real world scenario where a backend application might receive finished payments through a Kafka topic and
initiated payments through a REST API.

## Getting Started

## Prerequisites

Make sure you have Docker and Docker Compose installed on your machine. 

## Starting the Containers

To start the required containers, simply run the following command:

```shell
docker-compose up -d
```

This will start all the necessary services in the background.

## Running the Application

Once the containers are up and running, you can start the application with the local profile either by using the command
below or starting the `FraudDetectionManagementApiApplication` class in your IDE. Make sure to use the local profile
when starting the application.

```shell
./gradlew bootRun --args='--spring.profiles.active=local'
```

## Try out the APIs

### Initiate a Payment

To initiate a payment, you can use the following curl command:

```shell
curl -X POST http://localhost:8080/payments/evaluations -H "Content-Type: application/json" -d @./request/payload/payment-evaluation.json
```

In case you use IntelliJ IDEA, you can use the provided HTTP request file in the `request` folder.

### Send the Payment Confirmation to the Kafka Topic

To send the payment confirmation to the Kafka topic, you can use the Kafka UI.

Steps: 
 - Open Kafka UI by visiting [http://localhost:8081](http://localhost:8081) in your browser.
 - Navigate to the `Topics` tab.
 - Click on the `payment-confirmation` topic.
 - Click on `Produce message`.
 - Paste the content of the `./request/payload/payment-confirmation.json` file in the `value` area.

### Check the Logs

While running the `FraudDetectionManagementApiApplication` you should be able to see the logs in the console.

## Stopping the Containers

To stop the containers, simply run the following command:

```shell
docker-compose down
```

Enjoy! 🚀
