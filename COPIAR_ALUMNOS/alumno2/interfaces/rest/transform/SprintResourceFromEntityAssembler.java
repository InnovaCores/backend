package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Sprint;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.SprintResource;

public class SprintResourceFromEntityAssembler {
    public static SprintResource toResourceFromEntity(Sprint sprint) {
        return new SprintResource(sprint.getId(), sprint.getTitle(), sprint.getGoal(), sprint.getEndDate(), sprint.getStartDate(), sprint.getStatus());
    }
}
