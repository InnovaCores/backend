package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

public record DeleteTaskCommand(Long userStoryId, Long taskId) {
}
