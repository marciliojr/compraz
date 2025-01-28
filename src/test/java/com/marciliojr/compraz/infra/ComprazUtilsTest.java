package com.marciliojr.compraz.infra;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ComprazUtilsTest {

    @Test
    void parseDate_DeveConverterStringParaLocalDate() {
        LocalDate data = ComprazUtils.parseDate("2024-01-15");
        assertNotNull(data);
        assertEquals(LocalDate.of(2024, 1, 15), data);
    }

    @Test
    void parseDate_DeveRetornarNuloParaStringVaziaOuNula() {
        assertNull(ComprazUtils.parseDate(null));
        assertNull(ComprazUtils.parseDate(""));
        assertNull(ComprazUtils.parseDate("null"));
    }

    @Test
    void parseDate_DeveLancarExcecaoParaFormatoInvalido() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ComprazUtils.parseDate("15-01-2024");
        });
        assertTrue(exception.getReason().contains("Formato de data inválido"));
    }

    @Test
    void sanitizeString_DeveRemoverEspacosExtras() {
        assertEquals("Texto", ComprazUtils.sanitizeString("  Texto  "));
    }

    @Test
    void sanitizeString_DeveRetornarNuloParaStringVaziaOuNula() {
        assertNull(ComprazUtils.sanitizeString(null));
        assertNull(ComprazUtils.sanitizeString(""));
        assertNull(ComprazUtils.sanitizeString("null"));
    }
}
