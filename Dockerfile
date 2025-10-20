
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean bootJar --no-daemon


FROM eclipse-temurin:21-jre
WORKDIR /app
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75"
COPY --from=build /app/build/libs/*SNAPSHOT*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
