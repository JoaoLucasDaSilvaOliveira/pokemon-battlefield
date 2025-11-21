# --- Etapa 1: Build (Compilação) ---
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app

# Copia todo o projeto para dentro do container
COPY . .

# ALTERAÇÃO AQUI:
# Usamos -Dmaven.test.skip=true para pular a COMPILAÇÃO dos testes também.
RUN mvn clean package -Dmaven.test.skip=true

# --- Etapa 2: Run (Execução) ---
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

# Copia o .jar gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 (padrão do Spring)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]