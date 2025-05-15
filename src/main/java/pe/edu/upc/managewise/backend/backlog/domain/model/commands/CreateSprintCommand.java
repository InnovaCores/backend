package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

import java.util.Date;

public record CreateSprintCommand(String title, String goal, Date endDate) {
}
