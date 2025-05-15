package pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources;

import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.SprintStatus;

public record UpdateSprintResource (
        String title,
        String goal,
        SprintStatus status
) {
}
