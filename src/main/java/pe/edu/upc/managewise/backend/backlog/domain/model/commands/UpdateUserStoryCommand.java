package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;

public record UpdateUserStoryCommand(Long id, String title, String description, Long epicId, Long SprintId, Status status, Integer effort) {
}
