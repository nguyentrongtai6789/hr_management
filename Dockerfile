# ===== STAGE 1: BUILD =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
# cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B
# copy source
COPY src ./src
# build jar
RUN mvn clean package -DskipTests
# ===== STAGE 2: RUN =====
FROM eclipse-temurin:21-jre-jammy
# cài bash + tool debug cơ bản
RUN apt-get update && apt-get install -y \
    bash \
    curl \
    vim \
    net-tools \
    iputils-ping \
 && rm -rf /var/lib/apt/lists/*
# thêm alias ll
RUN echo "alias ll='ls -l'" >> /root/.bashrc
WORKDIR /app
# copy jar từ stage build
COPY --from=build /app/target/*.jar app.jar
# expose port
EXPOSE 8081
# chạy app
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]