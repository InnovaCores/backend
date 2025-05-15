package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.UpdateSprintResource;

public class UpdateSprintCommandFromResourceAssembler {
    public static UpdateSprintCommand toCommandFromResource(Long id, UpdateSprintResource resource){
        return new UpdateSprintCommand(id, resource.title(), resource.goal(), resource.status());
    }
}
