package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Sprint;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllSprintsQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetSprintByIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetSprintByTittleQuery;

import java.util.List;
import java.util.Optional;

public interface SprintQueryService {
    Optional<Sprint> handle(GetSprintByIdQuery query);
    List<Sprint> handle(GetAllSprintsQuery query);;
    Optional<Sprint> handle(GetSprintByTittleQuery query);
}
