package com.marciliojr.compraz.infra;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class ComprazUtils {


    public static LocalDate parseDate(String dateStr) {
        return Optional.ofNullable(dateStr)
                .map(String::trim)
                .filter(s -> !s.isEmpty() && !"null".equalsIgnoreCase(s))
                .map(s -> {
                    try {
                        return LocalDate.parse(s);
                    } catch (DateTimeParseException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de data inválido: " + s);
                    }
                })
                .orElse(null);
    }

    public static String sanitizeString(String value) {
        return Optional.ofNullable(value)
                .map(String::trim)
                .filter(s -> !s.isEmpty() && !"null".equalsIgnoreCase(s))
                .orElse(null);
    }


}
