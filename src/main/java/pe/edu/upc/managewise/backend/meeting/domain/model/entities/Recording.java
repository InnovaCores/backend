// src/main/java/pe/edu/upc/center/platform/meeting/domain/model/entities/Recording.java
package pe.edu.upc.managewise.backend.meeting.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.managewise.backend.meeting.domain.model.aggregates.Meeting;

@Entity
@Table(name = "recordings")
public class Recording {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotNull
    @ManyToOne // Relación con Meeting
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting; // Cambiado para referencia a la entidad Meeting

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "recording_link", nullable = false)
    private String recordingLink;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "duration", nullable = false)
    private String duration;

    @Getter
    @Column(name = "public_access", nullable = false)
    private boolean publicAccess;

    // Constructor para crear un nuevo Recording
    public Recording(Meeting meeting, String recordingLink, String duration, boolean publicAccess) {
        this.meeting = meeting; // Asigna la reunión a la que pertenece
        this.recordingLink = recordingLink;
        this.duration = duration;
        this.publicAccess = publicAccess;
    }

    // Constructor vacío para JPA
    public Recording() {
    }

    // Método para actualizar la grabación
    public void updateRecording(String recordingLink, String duration, boolean publicAccess) {
        this.recordingLink = recordingLink;
        this.duration = duration;
        this.publicAccess = publicAccess;
    }

}
