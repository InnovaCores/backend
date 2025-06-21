package pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources;



public record CreateMeetingResource(String title, String dateStr, String timeStr, String link) {
}
