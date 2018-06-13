FROM openjdk:8
           MAINTAINER Fernand@ Javi Mario

           ADD target/eureka-0.0.1-SNAPSHOT.jar eureka-server.jar
           ENTRYPOINT ["java", "-jar", "eureka-server.jar"]

           EXPOSE 8761