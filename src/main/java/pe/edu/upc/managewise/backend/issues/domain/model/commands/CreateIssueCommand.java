package pe.edu.upc.managewise.backend.issues.domain.model.commands;

import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssuePriorities;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssueStatuses;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.EventRecordItemResource;

import java.util.List;

public record CreateIssueCommand(String title, String sprintAssociate , String description, IssueStatuses status, IssuePriorities priority, String assignedTo, String madeBy, String createdIn, String resolutionDate, List<EventRecordItemResource> history) {
}
