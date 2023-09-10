FROM openjdk:8
EXPOSE 9004
ADD target/lms-api-gateway.jar lms-api-gateway.jar
ENTRYPOINT ["java","-jar","/lms-api-gateway.jar"]