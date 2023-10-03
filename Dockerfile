FROM maven:3-openjdk-17 AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
RUN addgroup --system javauser && adduser --system --shell /usr/sbin/nologin --ingroup javauser javauser
COPY --from=builder  build/target/*.jar app.jar
RUN chown -R javauser:javauser .
USER javauser
HEALTHCHECK --interval=30s --timeout=3s --retries=1 CMD wget -qO- http://localhost:8080/actuator/health/ | grep UP || exit 1
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar

