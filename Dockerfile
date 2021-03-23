FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} brotherhoodmutants.jar
ENTRYPOINT ["java","-jar","/brotherhoodmutants.jar"]