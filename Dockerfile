FROM adoptopenjdk/openjdk11:alpine-jre

RUN mkdir -p /software

ADD awc-web/target/awc-web*.jar /software/awc-web.jar

WORKDIR /software
CMD java -Dserver.port=$PORT $JAVA_OPTS -jar awc-web.jar