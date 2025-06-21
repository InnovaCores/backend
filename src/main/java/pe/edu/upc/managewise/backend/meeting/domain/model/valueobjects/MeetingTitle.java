package pe.edu.upc.managewise.backend.meeting.domain.model.valueobjects;



import jakarta.persistence.Embeddable;

@Embeddable
public record MeetingTitle(String title) {
    public MeetingTitle() {
        this(null);
    }

    public MeetingTitle {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
    }
}

