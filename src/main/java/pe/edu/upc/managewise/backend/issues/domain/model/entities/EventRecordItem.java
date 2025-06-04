package pe.edu.upc.managewise.backend.issues.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.managewise.backend.issues.domain.model.aggregates.Issue;
import pe.edu.upc.managewise.backend.shared.domain.model.entities.AuditableModel;

@Getter
@Entity
@Table(name= "event_issue_items")
public class EventRecordItem extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "description", length = 70, nullable = false)
    private String description;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "created_date", length = 70, nullable = false)
    private String createdDate;

    @NotNull
    @NotBlank
    @Column(name = "made_by", length = 70, nullable = false)
    private String madeBy;

    @NotNull
    @NotBlank
    @Column(name = "event_name", length = 70, nullable = false)
    private String eventName;

    public EventRecordItem(Issue issue,  String createdDate, String madeBy, String eventName, String description) {
        this.issue = issue;
        this.createdDate = createdDate;
        this.madeBy = madeBy;
        this.eventName = eventName;
        this.description = description;
    }

    public EventRecordItem(){

    }

    public Long getId() {
        return id;
    }

    public Issue getIssue() {
        return issue;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDescription() {
        return description;
    }

}
