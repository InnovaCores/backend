package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Epic;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllEpicsQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetEpicByIdQuery;

import java.util.List;
import java.util.Optional;

public interface EpicQueryService {
    List<Epic> handle(GetAllEpicsQuery query);
    Optional<Epic> handle(GetEpicByIdQuery query);
}
