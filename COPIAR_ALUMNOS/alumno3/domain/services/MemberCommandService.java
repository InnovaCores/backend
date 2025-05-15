package pe.edu.upc.managewise.backend.members.domain.services;

import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.commands.CreateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.DeleteMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.UpdateMemberCommand;

import java.util.List;
import java.util.Optional;

public interface MemberCommandService {
    Long handle(CreateMemberCommand command);
    Optional<Member> handle(UpdateMemberCommand command);
    void handle(DeleteMemberCommand command);
}
