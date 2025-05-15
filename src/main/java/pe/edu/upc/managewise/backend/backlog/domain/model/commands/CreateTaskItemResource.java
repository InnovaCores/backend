package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

public record CreateTaskItemResource(String title, String description, Integer estimation) {
}
