FROM eclipse-temurin:17-jdk-jammy
EXPOSE 8761
ADD target/lms-server.jar lms-server.jar
ENTRYPOINT ["java","-jar","/lms-server.jar"]