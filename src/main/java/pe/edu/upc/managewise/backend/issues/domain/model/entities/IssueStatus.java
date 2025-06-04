package pe.edu.upc.managewise.backend.issues.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssueStatuses;

@Entity
@Table(name = "issue_statuses")
@NoArgsConstructor
@AllArgsConstructor
@Data
@With
public class IssueStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 22)
    private IssueStatuses name;

    public IssueStatus(IssueStatuses name) {
        this.name = name;
    }

    public String getStringName() {
        return name.name();
    }

    public static IssueStatus getDefaultEnrollmentStatus() {
        return new IssueStatus(IssueStatuses.TO_DO);
    }

    public static IssueStatus toEnrollmentStatusFromName(String name) {
        return new IssueStatus(IssueStatuses.valueOf(name));
    }
}