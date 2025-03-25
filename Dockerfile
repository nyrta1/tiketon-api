FROM eclipse-temurin:21-jdk as build
COPY . /app
WORKDIR /app
RUN chmod +x mvnw
RUN ./mvnw package -DskipTests
RUN mv -f target/*.jar app.jar

FROM eclipse-temurin:21-jre
COPY --from=build /app/app.jar .
RUN useradd runtime
USER runtime
EXPOSE ${SERVER_PORT}
ENTRYPOINT [ "java", "-Dserver.port=${SERVER_PORT}", "-jar", "app.jar" ]
