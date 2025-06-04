package pe.edu.upc.managewise.backend.issues.domain.model.commands;

public record CreateEventResource(String createdDate, String madeBy, String eventName, String description) {
}