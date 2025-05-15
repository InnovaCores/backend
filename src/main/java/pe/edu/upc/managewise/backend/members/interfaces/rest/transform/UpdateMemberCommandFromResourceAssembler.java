package pe.edu.upc.managewise.backend.members.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.members.domain.model.commands.UpdateMemberCommand;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.MemberResource;

//Input
public record UpdateMemberCommandFromResourceAssembler() {
    public static UpdateMemberCommand toCommandFromResource(Long memberId, MemberResource resource){
        return new UpdateMemberCommand(memberId,resource.fullName(),resource.email(),resource.streetAddress(),resource.role());
    }
}
