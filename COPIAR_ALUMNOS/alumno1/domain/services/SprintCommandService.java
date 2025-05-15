package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Sprint;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateSprintCommand;

import java.util.Optional;

public interface SprintCommandService {
    Long handle(CreateSprintCommand command);
    Optional<Sprint> handle(UpdateSprintCommand command);
    void handle(DeleteSprintCommand command);
}
