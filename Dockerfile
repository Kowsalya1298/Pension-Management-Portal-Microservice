FROM amazoncorretto:8
EXPOSE 9085
ADD target/*.jar Pension-Management-portal-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["sh","-c","java -jar /Pension-Management-portal-0.0.1-SNAPSHOT.jar"]
