# Build .jar
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY . .

# Give permissions
RUN chmod +x mvnw

# Build Project
RUN ./mvnw -Dmaven.test.skip=true clean package

# Run .jar
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy the built .jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Active production profile
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
