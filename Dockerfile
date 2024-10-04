FROM maven:3.8.5-openjdk-17

WORKDIR /eureka

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean
RUN mvn package -DskipTests

FROM openjdk:17-jdk

COPY /target/eureka-server*.jar /eureka/launch-eureka.jar

ENTRYPOINT ["java","-jar","/eureka/launch-eureka.jar"]

EXPOSE 8761