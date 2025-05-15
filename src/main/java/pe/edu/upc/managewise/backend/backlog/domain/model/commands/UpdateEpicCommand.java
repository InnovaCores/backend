package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

public record UpdateEpicCommand(Long id, String title, String description) {
}
