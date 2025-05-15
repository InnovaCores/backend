package pe.edu.upc.managewise.backend.backlog.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteEpicCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllEpicsQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetEpicByIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.services.EpicCommandService;
import pe.edu.upc.managewise.backend.backlog.domain.services.EpicQueryService;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.CreateEpicResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.EpicResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.CreateEpicCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.EpicResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.UpdateEpicCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/epics", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Epics", description = "Epic Management Endpoints")
// Esta clase expone los endpoints REST del agregado Epic dentro del bounded context "backlog", siguiendo una arquitectura limpia donde el controlador actúa solo como orquestador: delega la lógica a los servicios de comando y consulta, y utiliza ensambladores para transformar las entidades del dominio en recursos de respuesta, manteniendo así un fuerte desacoplamiento entre capas.
public class EpicsController {
    private final EpicQueryService epicQueryService;
    private final EpicCommandService epicCommandService;

    public EpicsController(EpicQueryService epicQueryService, EpicCommandService epicCommandService) {
        this.epicQueryService = epicQueryService;
        this.epicCommandService = epicCommandService;
    }

    @PostMapping
    public ResponseEntity<EpicResource> createEpic(@RequestBody CreateEpicResource resource) {
        var createEpicCommand = CreateEpicCommandFromResourceAssembler.toCommandFromResource(resource);
        var epicId = this.epicCommandService.handle(createEpicCommand);

        if (epicId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getEpicByIdQuery = new GetEpicByIdQuery(epicId);
        var optionalEpic = this.epicQueryService.handle(getEpicByIdQuery);

        var epicResource = EpicResourceFromEntityAssembler.toResourceFromEntity(optionalEpic.get());
        return new ResponseEntity<>(epicResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EpicResource>> getAllEpics() {
        var getAllEpicsQuery = new GetAllEpicsQuery();
        var epics = this.epicQueryService.handle(getAllEpicsQuery);
        var epicResources = epics.stream()
                .map(EpicResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(epicResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpicResource> getEpicById(@PathVariable Long id) {
        var getEpicByIdQuery = new GetEpicByIdQuery(id);
        var optionalEpic = this.epicQueryService.handle(getEpicByIdQuery);
        if (optionalEpic.isEmpty())
            return ResponseEntity.badRequest().build();
        var epicResource = EpicResourceFromEntityAssembler.toResourceFromEntity(optionalEpic.get());
        return ResponseEntity.ok(epicResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpicResource> updateEpic(@PathVariable Long id, @RequestBody EpicResource resource) {
        var updateEpicCommand = UpdateEpicCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var optionalEpic = this.epicCommandService.handle(updateEpicCommand);

        if (optionalEpic.isEmpty())
            return ResponseEntity.badRequest().build();
        var epicResource = EpicResourceFromEntityAssembler.toResourceFromEntity(optionalEpic.get());
        return ResponseEntity.ok(epicResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEpic(@PathVariable Long id) {
        var deleteEpicCommand = new DeleteEpicCommand(id);
        this.epicCommandService.handle(deleteEpicCommand);
        return ResponseEntity.noContent().build();
    }
}
