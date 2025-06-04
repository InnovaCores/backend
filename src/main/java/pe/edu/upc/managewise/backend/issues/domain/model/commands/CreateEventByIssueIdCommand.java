package pe.edu.upc.managewise.backend.issues.domain.model.commands;

public record CreateEventByIssueIdCommand(Long issueId, String createdDate, String madeBy, String eventName, String description) {
}