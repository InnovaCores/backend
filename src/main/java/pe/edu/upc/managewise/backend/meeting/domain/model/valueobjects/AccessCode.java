package pe.edu.upc.managewise.backend.meeting.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;


@Embeddable
public record AccessCode(String accessCode) {

    public AccessCode() {
        this(UUID.randomUUID().toString());
    }

    public AccessCode {
        if (accessCode == null || accessCode.isBlank()) {
            throw new IllegalArgumentException("Access code cannot be null or blank");
        }
        if (accessCode.length() != 36) {
            throw new IllegalArgumentException("Access code must be 36 characters long");
        }
        if (!accessCode.matches("[a-f0-9]{8}-([a-f0-9]{4}-){3}[a-f0-9]{12}")) {
            throw new IllegalArgumentException("Access code must be a valid UUID format");
        }
    }
}

