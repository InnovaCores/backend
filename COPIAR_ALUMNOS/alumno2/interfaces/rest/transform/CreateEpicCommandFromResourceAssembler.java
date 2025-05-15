package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateEpicCommand;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.CreateEpicResource;

public class CreateEpicCommandFromResourceAssembler {
    public static CreateEpicCommand toCommandFromResource(CreateEpicResource resource){
        return new CreateEpicCommand(resource.title(), resource.description());
    }
}
