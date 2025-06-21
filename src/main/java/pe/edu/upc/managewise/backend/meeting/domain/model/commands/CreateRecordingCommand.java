package pe.edu.upc.managewise.backend.meeting.domain.model.commands;

public record CreateRecordingCommand(
        Long meetingId,
        String recordingLink,
        String duration,
        boolean publicAccess
) {}
