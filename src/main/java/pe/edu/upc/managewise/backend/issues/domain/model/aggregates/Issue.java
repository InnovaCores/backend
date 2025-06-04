package pe.edu.upc.managewise.backend.issues.domain.model.aggregates;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.EventRecord;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssuePriorities;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssueStatuses;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "issues")
public class Issue extends AuditableAbstractAggregateRoot<Issue> {
    @Getter
    @NotNull
    @NotBlank
    @Column(name = "title", length = 70, nullable = false)
    private String title;

    @Getter
    @Column(name = "sprint_associate", nullable = false)
    private String sprintAssociate;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "description", length = 300, nullable = false)
    private String description;

    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 12, nullable = false)
    private IssueStatuses status;


    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 10, nullable = false)
    private IssuePriorities priority;


    @Getter
    @NotNull
    @NotBlank
    @Column(name = "assigned_to", length = 70, nullable = false)
    private String assignedTo;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "made_by", length = 70, nullable = false)
    private String madeBy;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "created_in", length = 70, nullable = false)
    private String createdIn;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "resolution_date", length = 70, nullable = false)
    private String resolutionDate;

    @Embedded
    private EventRecord eventRecord;

    public Issue() {
        this.eventRecord = new EventRecord();
        this.priority = IssuePriorities.MEDIUM;
    }

    public Issue(String title, String sprintAssociate, String description, IssueStatuses status,IssuePriorities priority, String assignedTo, String madeBy, String createdIn, String resolutionDate) {
        this.title = title;
        this.sprintAssociate = sprintAssociate;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assignedTo = assignedTo;
        this.madeBy = madeBy;
        this.createdIn = createdIn;
        this.resolutionDate = resolutionDate;
        this.eventRecord = new EventRecord();
    }

  public Issue(CreateIssueCommand command) {
    this.title = command.title();
    this.sprintAssociate = command.sprintAssociate();
    this.description = command.description();
    this.status = command.status();
    this.priority= command.priority();
    this.assignedTo = command.assignedTo();
    this.madeBy = command.madeBy();
    this.createdIn = command.createdIn();
    this.resolutionDate = command.resolutionDate();
    this.eventRecord = new EventRecord();
  }

  public Issue updateInformation(String title, String sprintAssociate , String description, IssueStatuses status, IssuePriorities priority, String assignedTo, String madeBy, String createdIn, String resolutionDate) {
    this.title = title;
    this.sprintAssociate = sprintAssociate;
    this.description = description;
    this.status = status;
    this.priority= priority;
    this.assignedTo = assignedTo;
    this.madeBy = madeBy;
    this.createdIn = createdIn;
    this.resolutionDate = resolutionDate;
    return this;
  }

    public EventRecord getEventRecord() {
        return eventRecord;
    }
}