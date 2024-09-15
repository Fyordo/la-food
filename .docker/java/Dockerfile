# Используем официальный образ Maven для сборки приложения
FROM maven:3.8.6 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы конфигурации Maven
COPY pom.xml .

# Копируем исходный код проекта
COPY src /app/src

# Сборка приложения
RUN mvn clean package -DskipTests

# Используем официальный образ OpenJDK для выполнения приложения
FROM openjdk:17-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR файл из предыдущего этапа
COPY --from=build /app/target/*SNAPSHOT.jar la-food.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "la-food.jar"]
