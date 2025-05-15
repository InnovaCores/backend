package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.*;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.TaskItem;

import java.util.Optional;

public interface UserStoryCommandService {
    Long handle(CreateUserStoryCommand command);
    Optional<UserStory> handle(UpdateUserStoryCommand command);
    void handle(DeleteUserStoryCommand command);


    Long handle(CreateTaskITemByUserStoryIdCommand command);
    boolean handle(DeleteTaskCommand command);
    Optional<TaskItem> handle(UpdateTaskItemCommand command);
}
