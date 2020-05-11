FROM openjdk:11-jdk-slim-buster
RUN mkdir -p /home/app
ADD . /home/app
RUN cd /home/app && ./gradlew build -x test
ENTRYPOINT java -jar /home/app/build/libs/wikisniffer-0.0.1-SNAPSHOT.jar
