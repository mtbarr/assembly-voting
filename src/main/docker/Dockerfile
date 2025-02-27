# Stage 1: Build da aplicação com Gradle
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copiar apenas os arquivos de dependências primeiro para aproveitar o cache do Docker
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
RUN chmod +x ./gradlew
# Baixar todas as dependências para aproveitar o cache
RUN ./gradlew dependencies --no-daemon

# Então copiar o código-fonte
COPY src src

# Executar o build com otimizações
RUN ./gradlew build --no-daemon -x test

# Stage 2: Criar a imagem final apenas com o JRE e o JAR
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Configurar timezone
ENV TZ=America/Sao_Paulo

# Copiar apenas o JAR gerado do estágio anterior
COPY --from=builder /app/build/libs/assembly-voting-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Comando para iniciar a aplicação com otimizações de JVM
CMD ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]