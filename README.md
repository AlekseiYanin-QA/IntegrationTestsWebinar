**Workshop for the Integration Tests Webinar.**

In this workshop, we have our own service which calls external dependency - Google Translation Service. The goal of the workshop is to create tests by mocking external dependency, making sure it gets called whenever expected, and that it is not - when it shouldn't be.

`MyTranslationServiceTest_TODO` has the test templates created. The task is to fill those templates with corresponding code.

Проект предоставляет сервис для перевода текста с использованием Google Translate API.

## Описание
Сервис поддерживает перевод текста на русский язык. В случае ошибок выбрасываются пользовательские исключения.

## Зависимости
- Java 21
- Google Cloud Translation API
- JUnit 5 (для тестирования)
- Mockito (для мокирования зависимостей)

## Запуск
1. Убедитесь, что у вас установлен Gradle и Java 21.
2. Скачайте проект.
3. Выполните команду для сборки:
   ```bash
   ./gradlew build
Запустите тесты:
```bash
  ./gradlew test
