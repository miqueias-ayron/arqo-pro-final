# Etapa 1: compila o jar com Maven e JDK 25
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

# Baixa as dependências antes do código, para o cache do Docker reaproveitar essa camada
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q -DskipTests package

# Etapa 2: imagem final só com o JRE e o jar
FROM eclipse-temurin:25-jre
WORKDIR /app

# Roda sem privilégios de root
RUN groupadd --system app && useradd --system --gid app app
COPY --from=build /app/target/*.jar app.jar
USER app

# A porta vem da variável PORT (o Render define a sua); localmente fica 8081
ENV PORT=8081
EXPOSE 8081

# Limita o heap pela memória do container
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]
