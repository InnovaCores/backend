package pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.TaskItem;

import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
public class TaskList {
    @OneToMany(mappedBy = "userStory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskItem> tasks;

    public TaskList(){
        tasks = new ArrayList<>();
    }

    public void addToTaskList(UserStory _userStory, String title, String description, Integer estimation){
        if (_userStory == null) {
            return;
            //throw new IllegalArgumentException("User story cannot be null");
        }
        //Long userStoryId = _userStory.getId();
        TaskItem task = new TaskItem(_userStory, title, description, estimation);
        tasks.add(task);
    }

    public void removeFromTaskList(Long taskId){
        tasks.removeIf(task -> task.getId().equals(taskId));
    }

    public void updateTaskInformation(Long taskId, String title, String description, Status status, Integer estimation){
        tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .ifPresent(task -> task.UpdateInformation(title, description, status, estimation));
    }

    //getTaskItemWithTaskId
    public TaskItem getTaskItemWithTaskId(Long taskId){
        return tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElse(null);
    }

    public boolean isEmpty(){
        return tasks.isEmpty();
    }

    public void addTask(TaskItem task){
        tasks.add(task);
    }

    public void removeTask(Long taskId) {
        tasks.removeIf(task -> task.getId().equals(taskId));
    }

    /*
    public void updateTaskStatus(Long taskId, Status status){
        tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .ifPresent(task -> task.updateStatus(status));
    }
    */

}
