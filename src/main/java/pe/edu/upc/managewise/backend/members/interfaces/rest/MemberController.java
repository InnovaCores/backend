package pe.edu.upc.managewise.backend.members.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.members.domain.model.commands.DeleteMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetAllMembersQuery;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMemberByIdQuery;
import pe.edu.upc.managewise.backend.members.domain.services.MemberCommandService;
import pe.edu.upc.managewise.backend.members.domain.services.MemberQueryService;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.CreateMemberResource;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.MemberResource;
import pe.edu.upc.managewise.backend.members.interfaces.rest.transform.CreateMemberCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.members.interfaces.rest.transform.MemberResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.members.interfaces.rest.transform.UpdateMemberCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/members", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Members", description = "Member Management Endpoints")
public class MemberController {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    public MemberController(MemberQueryService memberQueryService, MemberCommandService memberCommandService) {
        this.memberQueryService = memberQueryService;
        this.memberCommandService = memberCommandService;
    }

    @PostMapping
    public ResponseEntity<MemberResource> createMember(@RequestBody CreateMemberResource resource){
        var createMemberCommand = CreateMemberCommandFromResourceAssembler.toCommandFromResource(resource);
        var memberId = this.memberCommandService.handle(createMemberCommand);

        if(memberId.equals(0L)){
            return ResponseEntity.badRequest().build();
        }

        var getMemberByIdQuery = new GetMemberByIdQuery(memberId);
        var optionMember = this.memberQueryService.handle(getMemberByIdQuery);

        var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(optionMember.get());
        return new ResponseEntity<>(memberResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemberResource>> getAllMembers() {
        var getAllMembersQuery = new GetAllMembersQuery();
        var members = this.memberQueryService.handle(getAllMembersQuery);
        var memberResources = members.stream()
                .map(MemberResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberResources);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResource> getMemberById(@PathVariable Long memberId) {
        var getMemberByIdQuery = new GetMemberByIdQuery(memberId);
        var optionalMember = this.memberQueryService.handle(getMemberByIdQuery);
        if (optionalMember.isEmpty())
            return ResponseEntity.notFound().build();
        var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(optionalMember.get());
        return ResponseEntity.ok(memberResource);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResource> updateIssue(@PathVariable Long memberId, @RequestBody MemberResource resource) {
        //Crea el command y el handle
        var updateMemberCommand = UpdateMemberCommandFromResourceAssembler.toCommandFromResource(memberId, resource);
        var optionalMember = this.memberCommandService.handle(updateMemberCommand);
        //valida que el optional profile esta vacio o no
        if (optionalMember.isEmpty())
            return ResponseEntity.badRequest().build();
        //en caso que no este vacio
        //lo convierto a un profile resource
        var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(optionalMember.get());
        //lo devuelvo
        return ResponseEntity.ok(memberResource);
    }

    // Indicamos que este método responderá a solicitudes HTTP DELETE.
    // En este caso, se espera que la URL incluya un identificador (memberId) en el path.
    @DeleteMapping("/{memberId}")
    //recordemos quetodo controller es Response Entitiy
    //El delete va acompañado d eun identificador para encontrarloy eliminarlo
    //Cuando es delete no hay respuesta , es vacio o aveces un valor determinado
    //por eso tiene el ?
    public ResponseEntity<?> deleteIssue(@PathVariable Long memberId) {
        //Creamos el Command y el handler
        var deleteMemberCommand = new DeleteMemberCommand(memberId);
        this.memberCommandService.handle(deleteMemberCommand);
        // Respondemos con HTTP 204 No Content, que indica que la operación fue exitosa,
        // pero no se devuelve contenido en el cuerpo de la respuesta.
        // Si el recurso no se encuentra, esto también puede ser una respuesta válida,
        // siempre que se haya manejado adecuadamente en el servicio.
        return ResponseEntity.noContent().build();
    }
}
