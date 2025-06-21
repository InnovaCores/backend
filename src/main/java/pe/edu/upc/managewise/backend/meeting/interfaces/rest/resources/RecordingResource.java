package pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources;

public record RecordingResource(
        String recordingLink,
        String duration,
        boolean publicAccess
) {}
