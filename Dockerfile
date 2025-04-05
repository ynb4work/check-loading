FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /app

# Копируем файлы зависимостей отдельно для кэширования
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Скачиваем зависимости
RUN ./mvnw dependency:go-offline

# Копируем исходный код
COPY src ./src

# Собираем приложение
RUN ./mvnw clean package -DskipTests

# Финальный образ
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Копируем только JAR
COPY --from=builder /app/target/*.jar app.jar

# Настройки для production
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

# Запуск с оптимальными параметрами JVM
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]