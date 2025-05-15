package pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources;

import java.util.Date;

public record CreateSprintResource(String title, String goal, Date endDate) {
}
