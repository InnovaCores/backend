package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.TaskItem;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllTaskItemsByUserStoryIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllUserStoriesQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetUserStoryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface UserStoryQueryService {
    List<UserStory> handle(GetAllUserStoriesQuery query);
    Optional<UserStory> handle(GetUserStoryByIdQuery query);

    List<TaskItem> handle(GetAllTaskItemsByUserStoryIdQuery query);
}
