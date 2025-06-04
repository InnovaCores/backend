package pe.edu.upc.managewise.backend.issues.domain.model.commands;

import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssuePriorities;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssueStatuses;

public record UpdateIssueCommand(Long issueId, String title, String sprintAssociate , String description, IssueStatuses status, IssuePriorities priority, String assignedTo, String madeBy, String createdIn, String resolutionDate) {
}
