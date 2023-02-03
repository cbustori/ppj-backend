FROM openjdk:8-jre
VOLUME /tmp
ADD ppj-apiserver/target/*.jar ppj-backend.jar
ENTRYPOINT ["java", "-Duser.timezone=CET", "-Dspring.profiles.active=dev","-jar","/ppj-backend.jar"]