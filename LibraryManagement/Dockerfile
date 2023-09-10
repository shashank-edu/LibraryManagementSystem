FROM openjdk:8
EXPOSE 9001
ENV MONGO_DB_USERNAME=admin \
    MONGO_DB_PWD=password
ADD target/lms-library-management.jar lms-library-management.jar
ENTRYPOINT ["java","-jar","/lms-library-management.jar"]