package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.TaskItemResource;

import java.util.List;

public record CreateUserStoryCommand(
        String title,
        String description,
        Long epicId,
        Long sprintId,
        Integer effort,
        List<TaskItemResource> tasks)
{}
