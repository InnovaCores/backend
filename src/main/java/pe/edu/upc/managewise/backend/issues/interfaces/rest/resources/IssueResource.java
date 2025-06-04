package pe.edu.upc.managewise.backend.issues.interfaces.rest.resources;

import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssuePriorities;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssueStatuses;

import java.util.List;

//This is my output JSON
public record IssueResource(Long id, String title, String sprintAssociate , String description, IssueStatuses status, IssuePriorities priority, String assignedTo, String madeBy, String createdIn, String resolutionDate, List<EventRecordItemResource> history) {
}
