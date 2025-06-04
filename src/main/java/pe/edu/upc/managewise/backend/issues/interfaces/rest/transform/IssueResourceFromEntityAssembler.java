package pe.edu.upc.managewise.backend.issues.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.issues.domain.model.aggregates.Issue;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.EventRecordItemResource;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.IssueResource;

import java.util.List;
import java.util.stream.Collectors;

public class IssueResourceFromEntityAssembler {
    public static IssueResource toResourceFromEntity(Issue issue) {
        List<EventRecordItemResource> history = issue.getEventRecord().getEventRecordItems().stream()
                .map(eventRecordItem -> new EventRecordItemResource(
                        eventRecordItem.getId(), // Include the id here
                        eventRecordItem.getCreatedDate(),
                        eventRecordItem.getMadeBy(),
                        eventRecordItem.getEventName(),
                        eventRecordItem.getDescription()))
                .collect(Collectors.toList());

        return new IssueResource(
                issue.getId(),
                issue.getTitle(),
                issue.getSprintAssociate(),
                issue.getDescription(),
                issue.getStatus(),
                issue.getPriority(),
                issue.getAssignedTo(),
                issue.getMadeBy(),
                issue.getCreatedIn(),
                issue.getResolutionDate(),
                history
        );
    }
}