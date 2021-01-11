FROM openjdk:11.0.9.1-jre-buster
RUN addgroup --system app && adduser --system app --ingroup app
USER app:app
VOLUME /var/lib/mysql

COPY /build/libs/user-account-service-0.0.1-SNAPSHOT.jar /usr/app/user-account-service.jar
WORKDIR /usr/app
ENTRYPOINT ["java","-jar","user-account-service.jar"]

# docker build -t user-account-service:0.0.1 .
# docker run --name=user-account-service -p 8080:8080 -d user-account-service:0.0.1-SNAPSHOT
