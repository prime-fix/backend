# ---------------------------------------------
# BUILD STAGE — Java 25 JDK (from AWS ECR Public)
# ---------------------------------------------
FROM public.ecr.aws/docker/library/eclipse-temurin:25-jdk AS build

WORKDIR /app

# Copy project files
COPY . .

# Give execute permissions to Maven wrapper
RUN chmod +x mvnw

# Build the Spring Boot application WITHOUT tests
RUN ./mvnw -Dmaven.test.skip=true clean package


# ---------------------------------------------
# RUNTIME STAGE — Java 25 JRE (from AWS ECR Public)
# ---------------------------------------------
FROM public.ecr.aws/docker/library/eclipse-temurin:25-jre

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
