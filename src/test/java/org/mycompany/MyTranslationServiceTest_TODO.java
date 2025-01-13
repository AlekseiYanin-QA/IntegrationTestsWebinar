package org.mycompany;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyTranslationServiceTest_TODO {

    // Мокируем зависимость Google Translate
    @Mock
    private Translate googleTranslate;

    // Внедряем мок в тестируемый сервис
    @InjectMocks
    private MyTranslationService myTranslationService;

    /**
     * 1. Happy case test.
     * Когда метод `MyTranslationService::translateWithGoogle` вызывается с любым предложением и целевым языком "ru",
     * должен быть вызван метод `googleTranslate.translate()`, и возвращен результат `translation.getTranslatedText()`.
     * Других взаимодействий с `googleTranslate` быть не должно.
     */
    @Test
    void translateWithGoogle_anySentenceAndTargetLanguageIsRu_success() {
        // Arrange
        String sentence = "Hello, world!";
        String targetLanguage = "ru";
        String translatedText = "Привет, мир!";

        // Мокируем вызов translate и возвращаем ожидаемый результат
        Translation translation = mock(Translation.class);
        when(translation.getTranslatedText()).thenReturn(translatedText);
        when(googleTranslate.translate(eq(sentence), any())).thenReturn(translation);

        // Act
        String result = myTranslationService.translateWithGoogle(sentence, targetLanguage);

        // Assert
        assertEquals(translatedText, result);
        verify(googleTranslate, times(1)).translate(eq(sentence), any());
        verifyNoMoreInteractions(googleTranslate);
    }

    /**
     * 2. Unhappy case test when target language is not supported.
     * Когда метод `MyTranslationService::translateWithGoogle` вызывается с любым предложением и целевым языком, отличным от "ru",
     * должно быть выброшено исключение `IllegalArgumentException`. Метод `googleTranslate.translate()` не должен вызываться.
     */
    @Test
    void translateWithGoogle_anySentenceAndTargetLanguageIsNotRu_failure() {
        // Arrange
        String sentence = "Hello, world!";
        String targetLanguage = "es"; // Неподдерживаемый язык

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, // Ожидаемый тип исключения
                () -> myTranslationService.translateWithGoogle(sentence, targetLanguage) // Действие, которое должно вызвать исключение
        );

        // Проверяем сообщение исключения
        assertEquals("only translation to Russian is currently supported!", exception.getMessage());

        // Проверяем, что googleTranslate не вызывался
        verifyNoInteractions(googleTranslate);
    }

    /**
     * 3. Unhappy case test when Google Translate call throws exception.
      * Когда метод `MyTranslationService::translateWithGoogle` вызывается с любым предложением и целевым языком "ru",
     * и метод `googleTranslate.translate()` выбрасывает исключение, оно должно быть обернуто в `MyTranslationServiceException`.
     */
    @Test
    void translateWithGoogle_googleTranslateThrowsException_failure() {
        // Arrange
        String sentence = "Hello, world!";
        String targetLanguage = "ru";

        // Мокируем выброс исключения при вызове translate
        when(googleTranslate.translate(eq(sentence), any())).thenThrow(new RuntimeException("API error"));

        // Act & Assert
        MyTranslationServiceException exception = assertThrows(MyTranslationServiceException.class, () -> {
            myTranslationService.translateWithGoogle(sentence, targetLanguage);
        });

        assertEquals("Exception while calling Google Translate API", exception.getMessage());
        assertNotNull(exception.getCause()); // Проверяем, что причина исключения сохранена
        verify(googleTranslate, times(1)).translate(eq(sentence), any());
    }
}