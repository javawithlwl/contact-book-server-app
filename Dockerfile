FROM maven:3.8.7-eclipse-temurin-19-alpine AS build
RUN mkdir /home/contactbook
copy . /home/contactbook
RUN cd /home/contactbook && mvn package
RUN cp /home/contactbook/target/*.jar contactbook.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/contactbook.jar"]