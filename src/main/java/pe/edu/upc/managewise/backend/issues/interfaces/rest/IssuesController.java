package pe.edu.upc.managewise.backend.issues.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateEventByIssueIdCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateEventResource;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.DeleteIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.queries.GetAllIssuesQuery;
import pe.edu.upc.managewise.backend.issues.domain.model.queries.GetIssueByIdQuery;
import pe.edu.upc.managewise.backend.issues.domain.services.IssueCommandService;
import pe.edu.upc.managewise.backend.issues.domain.services.IssueQueryService;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.CreateIssueResource;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.EventRecordItemResource;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.IssueResource;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.transform.CreateIssueCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.transform.EventRecordItemResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.transform.IssueResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.transform.UpdateIssueCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="*",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/issues",produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Issues", description = "Issue Managment Endpoints")
public class IssuesController {
    private final IssueQueryService issueQueryService;
    private final IssueCommandService issueCommandService;

    public IssuesController(IssueQueryService issueQueryService, IssueCommandService issueCommandService) {
        this.issueQueryService = issueQueryService;
        this.issueCommandService = issueCommandService;
    }

    @PostMapping
    public ResponseEntity<IssueResource> createIssue(@RequestBody CreateIssueResource resource){
        var createIssueCommand = CreateIssueCommandFromResourceAssembler.toCommandFromResource(resource);
        var issueId = this.issueCommandService.handle(createIssueCommand);

        if(issueId.equals(0L)){
            return ResponseEntity.badRequest().build();
        }

        var getIssueByIdQuery = new GetIssueByIdQuery(issueId);
        var optionIssue = this.issueQueryService.handle(getIssueByIdQuery);

        var issueResource = IssueResourceFromEntityAssembler.toResourceFromEntity(optionIssue.get());
        return new ResponseEntity<>(issueResource, HttpStatus.CREATED);
    }

    @PostMapping("/{issueId}/events")
    public ResponseEntity<EventRecordItemResource> createEvent(@PathVariable Long issueId, @RequestBody CreateEventResource resource) {
        var createEventCommand = new CreateEventByIssueIdCommand(issueId, resource.createdDate(), resource.madeBy(), resource.eventName(), resource.description());
        var eventId = this.issueCommandService.handle(createEventCommand);

        if (eventId == null || eventId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getIssueByIdQuery = new GetIssueByIdQuery(issueId);
        var optionalIssue = this.issueQueryService.handle(getIssueByIdQuery);

        if (optionalIssue.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var createdEvent = optionalIssue.get().getEventRecord().getEventRecordItems().stream()
                .filter(event -> event.getId().equals(eventId))
                .findFirst()
                .orElse(null);

        if (createdEvent == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        var eventResource = EventRecordItemResourceFromEntityAssembler.toResourceFromEntity(createdEvent);
        return new ResponseEntity<>(eventResource, HttpStatus.CREATED);
    }

    @GetMapping("/{issueId}/events")
    public ResponseEntity<List<EventRecordItemResource>> getAllEventsByIssueId(@PathVariable Long issueId) {
        // Verificar si el issue existe
        var getIssueByIdQuery = new GetIssueByIdQuery(issueId);
        var optionalIssue = this.issueQueryService.handle(getIssueByIdQuery);
        if (optionalIssue.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var events = optionalIssue.get().getEventRecord().getEventRecordItems();
        var eventResources = events.stream()
                .map(EventRecordItemResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventResources);
    }

    //Get que te devuelve todo
    @GetMapping
    public ResponseEntity<List<IssueResource>> getAllIssues() {
        var getAllIssuesQuery = new GetAllIssuesQuery();
        var issues = this.issueQueryService.handle(getAllIssuesQuery);
        var issueResources = issues.stream()
                .map(IssueResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(issueResources);
    }

    //Get que te devuelve segun el id
    //Con GetMapping creamos el endpoint y establecemos el valor
    @GetMapping("/{issueId}")
    //Siempre ResponseEntity porque siempre la respuesta a el Front es response
    // le añado el valor de retorno dentro de <>
    //Debo indicarle a quien  le pertenece esta variable profileId --> @PathVariable
    public ResponseEntity<IssueResource> getIssueById(@PathVariable Long issueId) {
        //Creamos el query y ejecutamos el execute service
        var getIssueByIdQuery = new GetIssueByIdQuery(issueId);
        //nos devuelve el option
        //optional es un contenedor , dentro de este esta el issue
        var optionalIssue = this.issueQueryService.handle(getIssueByIdQuery);
        //validar si esta vacio
        if (optionalIssue.isEmpty())
            //responde un notfound request, con esto se devuelve el error de que no encontraste
            return ResponseEntity.notFound().build();
        //En caso si existe generamos el resource(JSON DE SALIDA)
        //Llamo al Assembler y lo convierte en un resource
        var issueResource = IssueResourceFromEntityAssembler.toResourceFromEntity(optionalIssue.get());
        //responder al endpoint con ok (200), con el JSON de salida
        return ResponseEntity.ok(issueResource);
    }

    //Para el put necesitamo un body el cual actaulizar
    //@PutMapping y establecemos si tendra alguna ruta en el path
    @PutMapping("/{issueId}")
    //Siempre ResponseEntity, devolvera ProfileResponse en <>
    // el updateProfile debera resivir dos paramteros
    // @PathVariable Long profileId, @RequestBody ProfileResource resource
    // el identificador y el otro que el el updateprofile donde entra el cuerpo a actualziar
    public ResponseEntity<IssueResource> updateIssue(@PathVariable Long issueId, @RequestBody IssueResource resource) {
        //Crea el command y el handle
        var updateIssueCommand = UpdateIssueCommandFromResourceAssembler.toCommandFromResource(issueId, resource);
        var optionalIssue = this.issueCommandService.handle(updateIssueCommand);
        //valida que el optional profile esta vacio o no
        if (optionalIssue.isEmpty())
            //bad request si no existe
            return ResponseEntity.badRequest().build();
        //en caso que no este vacio
        //lo convierto a un profile resource
        var issueResource = IssueResourceFromEntityAssembler.toResourceFromEntity(optionalIssue.get());
        //lo devuelvo
        return ResponseEntity.ok(issueResource);
    }

    //annotacion DeleteMapping
    // y establecemos si tendra alguna ruta en el path profileId
    @DeleteMapping("/{issueId}")
    //recordemos quetodo controller es Response Entitiy
    //El delete va acompañado d eun identificador para encontrarloy eliminarlo
    //Cuando es delete no hay respuesta , es vacio o aveces un valor determinado
    //por eso tiene el ?
        public ResponseEntity<?> deleteIssue(@PathVariable Long issueId) {
        //Creamos el Command y el handler
        var deleteIssueCommand = new DeleteIssueCommand(issueId);
        this.issueCommandService.handle(deleteIssueCommand);
        //devolvemos la respuesta
        //aca no debemos enviar mensaje de error,
        // en este caso seria solo el noContent de que no ecnontro el resource
        return ResponseEntity.noContent().build();
    }
}
