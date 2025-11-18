# --- Etapa 1: Build (Compilação) ---
# Usa uma imagem com Maven e Java 21 para gerar o .jar
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app

# Copia todo o projeto para dentro do container
COPY . .

# Compila o projeto e gera o .jar (pulando os testes para agilizar e evitar erros de conexão no build)
RUN mvn clean package -DskipTests

# --- Etapa 2: Run (Execução) ---
# Usa uma imagem leve apenas com o Java 21 para rodar a aplicação
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

# Copia o .jar gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 (padrão do Spring)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]