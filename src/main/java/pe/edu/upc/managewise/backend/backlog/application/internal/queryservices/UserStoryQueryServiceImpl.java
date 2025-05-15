package pe.edu.upc.managewise.backend.backlog.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.TaskItem;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllTaskItemsByUserStoryIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllUserStoriesQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetUserStoryByIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.services.UserStoryQueryService;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.UserStoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserStoryQueryServiceImpl implements UserStoryQueryService {
    private final UserStoryRepository userStoryRepository;

    public UserStoryQueryServiceImpl(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    @Override
    public List<UserStory> handle(GetAllUserStoriesQuery query){
        return this.userStoryRepository.findAll();
    }

    @Override
    public Optional<UserStory> handle(GetUserStoryByIdQuery query){
        return this.userStoryRepository.findById(query.userStoryId());
    }

    @Override
    public List<TaskItem> handle(GetAllTaskItemsByUserStoryIdQuery query){
        return List.of();
    }
}