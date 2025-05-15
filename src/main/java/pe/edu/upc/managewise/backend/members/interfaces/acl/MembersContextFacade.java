package pe.edu.upc.managewise.backend.members.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetAllMembersQuery;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMemberByFullNameQuery;
import pe.edu.upc.managewise.backend.members.domain.services.MemberCommandService;
import pe.edu.upc.managewise.backend.members.domain.services.MemberQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembersContextFacade {

    private final MemberQueryService memberQueryService;

    public MembersContextFacade(MemberCommandService memberCommandService, MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    public Long fetchMemberIdByFullName(String fullName) {
        //1. Create Query and call handle
        var getMemberByFullNameQuery = new GetMemberByFullNameQuery(fullName);
        var optionalMember = memberQueryService.handle(getMemberByFullNameQuery);
        //2. validation
        //si no lo encuentro retorno 0Long
        if (optionalMember.isEmpty()) {
            return 0L;
        }
        //3. response
        //en caso si existe retorno el id
        return optionalMember.get().getId();
    }

    public List<Long> fetchAllMemberIds() {
        // Create a query to get all members
        GetAllMembersQuery query = new GetAllMembersQuery();

        // Use the handle method to get all members
        List<Member> members = memberQueryService.handle(query);

        // Extract and return only the IDs of the members
        return members.stream()
                .map(Member::getId) // Extract the ID of each member
                .collect(Collectors.toList()); // Return the list of IDs
    }
/*
    /*
     * Obtiene un miembro por su ID.
     *
     * @param memberId El ID del miembro.
     * @return Un miembro en formato recurso.

    public Optional<MemberResource> fetchMemberById(Long memberId) {
        var optionalMember = memberQueryService.findById(memberId);
        // Usar el método correcto 'toResource' en lugar de 'toResourceFromEntity'
        return optionalMember.map(MemberResourceFromEntityAssembler::toResource);
    }

  public List<Long> fetchAllMemberIds() {
    // Obtiene todos los miembros desde la base de datos o servicio
    List<Member> members = memberQueryService.findAll(); // Obtener todos los miembros

    // Extrae y retorna solo los IDs de los miembros
    return members.stream()
      .map(Member::getId) // Extrae el ID de cada miembro
      .collect(Collectors.toList()); // Devuelve la lista de IDs
  }*/



  /*
     * Obtiene el ID de un miembro basado en su nombre completo.
     *
     * @param fullName El nombre completo del miembro.
     * @return El ID del miembro.

    public Long fetchMemberIdByFullName(String fullName) {
        // Buscar todos los miembros
        List<Member> members = memberQueryService.findAll();

        // Filtrar por nombre completo, ignorando mayúsculas y minúsculas
        for (Member member : members) {
            if (member.getPersonName().getFullName().equalsIgnoreCase(fullName)) {
                return member.getId(); // Si encontramos una coincidencia, devolvemos el ID
            }
        }

        // Si no se encuentra el miembro, devolvemos 0L
        return 0L;
    }*/

    /*
     * Verifica si existe un miembro con un nombre completo y un ID distinto.
     *
     * @param fullName El nombre completo del miembro.
     * @param id El ID del miembro.
     * @return true si el miembro con ese nombre existe y tiene un ID distinto, false en caso contrario.

    public boolean existsMemberByFullNameAndIdIsNot(String fullName, Long id) {
        Optional<Member> existingMember = memberQueryService.findAll().stream()
                .filter(m -> m.getPersonName().getFullName().equalsIgnoreCase(fullName) && !m.getId().equals(id))
                .findFirst();
        return existingMember.isPresent();
    }*/


    /*
     * Crea un nuevo miembro.
     *
     * @param fullName El nombre completo del miembro.
     * @param role El rol del miembro.
     * @param address La dirección del miembro.
     * @return El ID del miembro creado.

    public Long createMember(String fullName, String role, String address, String email) {
        // Crear el nombre completo
        PersonName personName = new PersonName(fullName.split(" ")[0]);

        // Convertir el rol a ScrumRoles
        ScrumRoles scrumRole = ScrumRoles.valueOf(role.toUpperCase());

        // Parsear la dirección
        String[] addressParts = address.split(",");
        if (addressParts.length < 5) {
            throw new IllegalArgumentException("Dirección inválida");
        }

        // Crear la dirección
        StreetAddress streetAddress = new StreetAddress(
                addressParts[0].trim()
        );

        // Crear el correo
        EmailAddress emailAddress = new EmailAddress(email);

        // Suponiendo que tienes un método para generar el `memberId`
        Long memberId = generateMemberId(); // O bien, si es autogenerado por la base de datos

        // Crear el comando de creación
        var createMemberCommand = new CreateMemberCommand(memberId, personName, emailAddress, streetAddress, scrumRole);

        // Llamar al servicio para manejar el comando
        memberCommandService.handle(createMemberCommand);

        // Retornar el ID del miembro creado
        return memberId;
    }

    private Long generateMemberId() {
        // Lógica para generar el ID, si es necesario (esto depende de tu implementación)
        return 123L;  // Ejemplo de un ID generado
    }*/



    /*
     * Actualiza un miembro existente.
     *
     * @param memberId El ID del miembro a actualizar.
     * @param fullName El nombre completo actualizado.
     * @param role El rol actualizado.
     * @param address La dirección actualizada.
     * @return El ID del miembro actualizado.

    public Long updateMember(Long memberId, String fullName, String role, String address, String email) {
        // Crear los objetos necesarios para el comando
        PersonName personName = new PersonName(fullName.split(" ")[0]);
        ScrumRoles scrumRole = ScrumRoles.valueOf(role.toUpperCase());

        // Split address into parts (similar to how you handle it during creation)
        String[] addressParts = address.split(",");
        if (addressParts.length < 5) {
            throw new IllegalArgumentException("Dirección inválida");
        }

        // Crear la dirección
        StreetAddress streetAddress = new StreetAddress(
                addressParts[0].trim()
        );

        // Crear el correo
        EmailAddress emailAddress = new EmailAddress(email);

        // Crear el comando de actualización
        var updateMemberCommand = new UpdateMemberCommand(memberId, personName, emailAddress, streetAddress, scrumRole);

        // Llamar al servicio para manejar el comando
        var optionalMember = memberCommandService.handle(updateMemberCommand);

        // Retornar el ID del miembro actualizado
        return optionalMember.map(member -> member.getId()).orElse(0L);
    }*/


    /*
     * Elimina un miembro.
     *
     * @param memberId El ID del miembro a eliminar.

    public void deleteMember(Long memberId) {
        var deleteMemberCommand = new DeleteMemberCommand(memberId);
        memberCommandService.handle(deleteMemberCommand);
    }*/
}
