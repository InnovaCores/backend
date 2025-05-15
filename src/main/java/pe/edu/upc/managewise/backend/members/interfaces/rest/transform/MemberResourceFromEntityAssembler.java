package pe.edu.upc.managewise.backend.members.interfaces.rest.transform;

import org.springframework.stereotype.Component;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.MemberResource;

@Component
public class MemberResourceFromEntityAssembler {
    public static MemberResource toResourceFromEntity(Member entity) {
        return new MemberResource(
                entity.getId(),
                entity.getFullName(),
                entity.getRole(),
                entity.getEmail(),
                entity.getStreetAddress()
        );
    }
}
