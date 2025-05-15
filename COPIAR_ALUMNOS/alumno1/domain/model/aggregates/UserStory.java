package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.TaskItem;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.TaskList;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
public class UserStory extends AuditableAbstractAggregateRoot<UserStory> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    /*
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "epic_id", referencedColumnName = "epicId")
    private Epic epic;
    */
    private Long epicId;

    /*
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sprint_id", referencedColumnName = "sprintId")
    private Sprint sprint;
    */
    private Long sprintId;

    //en un fururo cambiar a storypoints
    private Integer effort;

    @Getter
    @Embedded
    private TaskList taskList;

    public UserStory() {
        this.taskList = new TaskList();
        this.status = Status.TO_DO;
    }

    public UserStory(String title, String description, Long epicId, Long sprintId, Integer effort) {
        this.title = title;
        this.description = description;
        this.status = Status.TO_DO;
        this.epicId = epicId;
        this.sprintId = sprintId;
        this.effort = effort;
        this.taskList = new TaskList();
    }

    public UserStory(CreateUserStoryCommand command){
        this();
        this.title = command.title();
        this.description = command.description();
        this.status = Status.TO_DO;
        this.epicId = command.epicId();
        this.sprintId = command.sprintId();
        this.effort = command.effort();
        this.taskList = new TaskList();
    }

    /*de momento solo se puede actualizar el titulo y descripcion*/
    public UserStory updateInformation(String title, String description,
                                       Long epicId, Long sprintId, Status status, Integer effort) {
        this.title = title;
        this.description = description;
        this.epicId = epicId;
        this.sprintId = sprintId;
        this.status = status;
        this.effort = effort;
        return this;
    }

    public void addTaskToTaskList(String title, String description, Integer estimation){
        this.taskList.addToTaskList(this, title, description, estimation);
    }

    public void addTask(TaskItem taskItem){
        this.taskList.addTask(taskItem);
    }

}
