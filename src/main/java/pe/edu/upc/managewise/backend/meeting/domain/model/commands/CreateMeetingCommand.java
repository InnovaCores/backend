package pe.edu.upc.managewise.backend.meeting.domain.model.commands;

public record CreateMeetingCommand(String title, String dateStr, String timeStr, String link) {
}
