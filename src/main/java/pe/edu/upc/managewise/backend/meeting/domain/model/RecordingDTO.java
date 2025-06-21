package pe.edu.upc.managewise.backend.meeting.domain.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordingDTO {
    private Long id;
    private Long meetingId;
    private String meetingTitle; // Para mostrar el título de la reunión asociada
    private String recordingLink;
    private String duration;
    private boolean publicAccess;
}
