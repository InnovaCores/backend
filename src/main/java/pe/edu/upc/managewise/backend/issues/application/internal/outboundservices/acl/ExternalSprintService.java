package pe.edu.upc.managewise.backend.issues.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.interfaces.acl.SprintsContextFacade;

import java.util.Optional;

@Service
public class ExternalSprintService {
    //Dependency Injection
    private final SprintsContextFacade sprintsContextFacade;

    public ExternalSprintService(SprintsContextFacade sprintsContextFacade) {
        this.sprintsContextFacade = sprintsContextFacade;
    }

    public Optional<Long> fetchSprintIdByTittle(String tittle) {
        Long sprintId = sprintsContextFacade.fetchSprintIdByTittle(tittle);
        if (sprintId.equals(0L)) {
            return Optional.empty();
        }
        return Optional.of(sprintId);
    }
}
