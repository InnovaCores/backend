package pe.edu.upc.managewise.backend.meeting.domain.model.commands;


public record UpdateMeetingCommand(Long meetingId, String title, String dateStr, String timeStr, String link) {
}
