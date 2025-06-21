// src/main/java/pe/edu/upc/managewise/backend/meeting/domain/model/valueobjects/MeetingDate.java
package pe.edu.upc.managewise.backend.meeting.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Embeddable
public record MeetingDate(LocalDate date) {

    public MeetingDate {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
    }

    // Método de fábrica para crear MeetingDate a partir de una cadena
    public static MeetingDate of(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr); // Utiliza el formato ISO 8601
            return new MeetingDate(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
    }

    @Override
    public String toString() {
        return date.toString(); // Formato ISO 8601
    }
}

