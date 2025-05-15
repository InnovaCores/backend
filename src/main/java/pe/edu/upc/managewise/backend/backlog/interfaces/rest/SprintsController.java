package pe.edu.upc.managewise.backend.backlog.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllSprintsQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetSprintByIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.services.SprintCommandService;
import pe.edu.upc.managewise.backend.backlog.domain.services.SprintQueryService;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.CreateSprintResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.SprintResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.UpdateSprintResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.CreateSprintCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.SprintResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.UpdateSprintCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/sprints", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sprints", description = "Sprint Management Endpoints")
public class SprintsController {
    private final SprintQueryService sprintQueryService;
    private final SprintCommandService sprintCommandService;

    public SprintsController(SprintQueryService sprintQueryService, SprintCommandService sprintCommandService) {
        this.sprintQueryService = sprintQueryService;
        this.sprintCommandService = sprintCommandService;
    }

    @PostMapping
    public ResponseEntity<SprintResource> createSprint(@RequestBody CreateSprintResource resource) {
        var createSprintCommand = CreateSprintCommandFromResourceAssembler.toCommandFromResource(resource);
        var sprintId = this.sprintCommandService.handle(createSprintCommand);

        if (sprintId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getSprintByIdQuery = new GetSprintByIdQuery(sprintId);
        var optionalSprint = this.sprintQueryService.handle(getSprintByIdQuery);

        var sprintResource = SprintResourceFromEntityAssembler.toResourceFromEntity(optionalSprint.get());
        return new ResponseEntity<>(sprintResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SprintResource>> getAllSprints() {
        var getAllSprintsQuery = new GetAllSprintsQuery();
        var sprints = this.sprintQueryService.handle(getAllSprintsQuery);
        var sprintResources = sprints.stream()
                .map(SprintResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sprintResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintResource> getSprintById(@PathVariable Long id) {
        var getSprintByIdQuery = new GetSprintByIdQuery(id);
        var optionalSprint = this.sprintQueryService.handle(getSprintByIdQuery);
        if (optionalSprint.isEmpty())
            return ResponseEntity.badRequest().build();
        var sprintResource = SprintResourceFromEntityAssembler.toResourceFromEntity(optionalSprint.get());
        return ResponseEntity.ok(sprintResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SprintResource> updateSprint(@PathVariable Long id, @RequestBody UpdateSprintResource resource) {
        var updateSprintCommand = UpdateSprintCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var optionalSprint = this.sprintCommandService.handle(updateSprintCommand);

        if (optionalSprint.isEmpty())
            return ResponseEntity.badRequest().build();
        var sprintResource = SprintResourceFromEntityAssembler.toResourceFromEntity(optionalSprint.get());
        return ResponseEntity.ok(sprintResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSprint(@PathVariable Long id) {
        var deleteSprintCommand = new DeleteSprintCommand(id);
        this.sprintCommandService.handle(deleteSprintCommand);
        return ResponseEntity.noContent().build();
    }
}
