package pe.edu.upc.managewise.backend.backlog.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.*;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.TaskItem;
import pe.edu.upc.managewise.backend.backlog.domain.services.UserStoryCommandService;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.EpicRepository;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.SprintRepository;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.UserStoryRepository;

import java.util.Optional;

@Service
public class UserStoryCommandServiceImpl implements UserStoryCommandService {
    private final UserStoryRepository userStoryRepository;
    private final SprintRepository sprintRepository;
    private final EpicRepository epicRepository;

    public UserStoryCommandServiceImpl(
            UserStoryRepository userStoryRepository,
            SprintRepository sprintRepository,
            EpicRepository epicRepository) {
        this.userStoryRepository = userStoryRepository;
        this.sprintRepository = sprintRepository;
        this.epicRepository = epicRepository;
    }

    @Override
    public Long handle(CreateUserStoryCommand command){
        var title = command.title();
        //epic y sprint id deben existir para poder crear correctamente
        if(this.userStoryRepository.existsByTitle(title)){
            throw new IllegalArgumentException("UserStory with " + title + " as title already exists");
        }
        if(!this.sprintRepository.existsById(command.sprintId()) && command.sprintId()!=0){
            throw new IllegalArgumentException("Sprint with id " + command.sprintId() + " does not exist");
        }
        if(!this.epicRepository.existsById(command.epicId()) && command.epicId()!=0){
            throw new IllegalArgumentException("Epic with id " + command.epicId() + " does not exist");
        }
        var userStory = new UserStory(command);
        try{
            this.userStoryRepository.save(userStory);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving userStory: " + e.getMessage());
        }
        return userStory.getId();
    }

    @Override
    public Optional<UserStory>handle(UpdateUserStoryCommand command){
        var userStoryId = command.id();
        var title = command.title();
        if (this.userStoryRepository.existsByTitleAndIdIsNot(title, userStoryId)){
            throw new IllegalArgumentException("UserStory with " + title + " as title already exists");
        }

        if (!this.userStoryRepository.existsById(userStoryId)){
            throw new IllegalArgumentException("UserStory with id " + userStoryId + " does not exist");
        }

        var userStoryTopUpdate = this.userStoryRepository.findById(userStoryId).get();
        userStoryTopUpdate.updateInformation(command.title(), command.description(),
                command.epicId(), command.SprintId(), command.status(), command.effort());

        try{
            var updatedUserStory = this.userStoryRepository.save(userStoryTopUpdate);
            return Optional.of(updatedUserStory);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating userStory: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteUserStoryCommand command){
        if(!this.userStoryRepository.existsById(command.userStoryId())){
            throw new IllegalArgumentException("UserStory with id " + command.userStoryId() + " does not exist");
        }

        try{
            this.userStoryRepository.deleteById(command.userStoryId());
        } catch (Exception e){
            throw new IllegalArgumentException("Error while deleting userStory: " + e.getMessage());
        }
    }

    @Override
    public Long handle(CreateTaskITemByUserStoryIdCommand command){
        var userStoryOptional = userStoryRepository.findById(command.userStoryId());
        if (userStoryOptional.isEmpty()){
            throw new IllegalArgumentException("UserStory with id " + command.userStoryId() + " does not exist");
        }
        var userStory = userStoryOptional.get();
        var taskItem = new TaskItem(userStory, command.title(), command.description(), command.estimation());
        userStory.getTaskList().addTask(taskItem);

       try{
            userStoryRepository.save(userStory);
            return userStory.getTaskList().getTasks().stream().filter(task -> task.getTitle().equals(command.title())).findFirst().get().getId();
        }catch (Exception e){
            throw new IllegalArgumentException("Error while adding task to userStory task list: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public boolean handle(DeleteTaskCommand command){
        var userStoryOptional = userStoryRepository.findById(command.userStoryId());
        if (userStoryOptional.isEmpty()){
            throw new IllegalArgumentException("UserStory with id " + command.userStoryId() + " does not exist");
        }

        var userStory = userStoryOptional.get();

        var task = userStory.getTaskList().getTasks().stream().
                filter(taskItem -> taskItem.getId().equals(command.taskId())).findFirst();

        if (task.isEmpty()){
            throw new IllegalArgumentException("Task with id " + command.taskId() + " does not exist");
        }

        userStory.getTaskList().getTasks().remove(task.get());

        try{
            userStoryRepository.save(userStory);
            return true;
        }catch (Exception e){
            throw new IllegalArgumentException("Error while deleting task from userStory task list: " + e.getMessage());
        }

    }


    @Override
    public Optional<TaskItem>handle(UpdateTaskItemCommand command){
        var userStoryId = command.userStoryId();

        if (!this.userStoryRepository.existsById(userStoryId)){
            throw new IllegalArgumentException("UserStory with id " + userStoryId + " does not exist");
        }

        var userStoryToUpdateTask = this.userStoryRepository.findById(userStoryId).get();
        userStoryToUpdateTask.getTaskList().updateTaskInformation(command.taskId(),
                command.title(), command.description(),
                command.status(), command.estimation());

        var taskUpdated = userStoryToUpdateTask.getTaskList().getTaskItemWithTaskId(command.taskId());

        try{
            userStoryRepository.save(userStoryToUpdateTask);
            return Optional.of(taskUpdated);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating userStory: " + e.getMessage());
        }
    }

}
