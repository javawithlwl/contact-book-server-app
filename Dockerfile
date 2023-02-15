FROM maven:3.8.6-openjdk-11-slim AS build
RUN mkdir /home/poc
copy . /home/poc
RUN cd /home/poc && mvn package
RUN cp /home/poc/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]