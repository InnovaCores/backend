package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

public record CreateTaskITemByUserStoryIdCommand(Long userStoryId, String title, String description, Integer estimation) {}