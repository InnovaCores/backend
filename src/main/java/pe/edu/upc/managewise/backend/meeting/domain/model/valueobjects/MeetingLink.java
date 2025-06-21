package pe.edu.upc.managewise.backend.meeting.domain.model.valueobjects;


import jakarta.persistence.Embeddable;

@Embeddable
public record MeetingLink(String link) {
    public MeetingLink() {
        this(null);
    }

    public MeetingLink {
        if (link == null || link.isBlank()) {
            throw new IllegalArgumentException("Link cannot be null or blank");
        }
        // Aquí podrías agregar más validaciones para verificar si es un enlace válido.
    }
}
