FROM adoptopenjdk/openjdk11:alpine-jre

RUN mkdir -p /software

# copy config files (application.properties)
#ADD config /software/config

ADD awc-web/target/awc-web*.jar /software/awc-web.jar

WORKDIR /software
CMD java -Dserver.port=$PORT $JAVA_OPTS -jar awc-web.jar