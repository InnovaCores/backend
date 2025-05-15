package pe.edu.upc.managewise.backend.backlog.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.shared.domain.model.entities.AuditableModel;

@Getter
@Entity
public class TaskItem extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_story")
    private UserStory userStory;

    private Integer estimation;

    public TaskItem(){
    }

    public TaskItem(UserStory userStory, String title, String description, Integer estimation){
        this.title = title;
        this.description = description;
        this.status = Status.TO_DO; //-----
        this.userStory = userStory;
        this.estimation = estimation;
    }

    //falta adicionar status al update
    public void UpdateInformation(String title, String description, Status status, Integer estimation){
        this.title = title;
        this.description = description;
        this.estimation = estimation;
        this.status = status;
    }

    /*metodo para cambiar solo el status*/

}
