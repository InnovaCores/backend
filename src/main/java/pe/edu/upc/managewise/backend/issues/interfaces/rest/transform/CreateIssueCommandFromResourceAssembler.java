package pe.edu.upc.managewise.backend.issues.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateIssueCommand;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.CreateIssueResource;

import java.util.Collections;
//Diferentes maneras de verlo:
//Esta creando un command a partir de un resource ,
//por lo que este resource es de entrada

//Entra un resource y lo convierte en command
//el command es de uso interno por lo que esto es un input(de entrada)

//Entra un JSON y lo convierto en un comman --> input
//Input
public class CreateIssueCommandFromResourceAssembler {
    public static CreateIssueCommand toCommandFromResource(CreateIssueResource resource) {
        return new CreateIssueCommand(
                resource.title(),
                resource.sprintAssociate(),
                resource.description(),
                resource.status(),
                resource.priority(),
                resource.assignedTo(),
                resource.madeBy(),
                resource.createdIn(),
                resource.resolutionDate(),
                Collections.emptyList()
        );
    }
}