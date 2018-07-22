FROM openjdk:8u131-jre-alpine
MAINTAINER Maksim Kostromin <daggerok@gmail.com>
RUN apk --no-cache add curl
WORKDIR /opt/app
VOLUME ["/opt/app"]
CMD ["java", "-jar", "/opt/app/app.jar"]
EXPOSE 8080
HEALTHCHECK --timeout=1s --retries=44 CMD curl -f http://127.0.0.1:8080/ || exit 1
COPY ./build/libs/*.jar ./app.jar
