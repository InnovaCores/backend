package pe.edu.upc.managewise.backend.meeting.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.members.interfaces.acl.MembersContextFacade;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingExternalMemberService {
  //Dependency Injection
  private final MembersContextFacade membersContextFacade;

  public MeetingExternalMemberService(MembersContextFacade membersContextFacade) {
    this.membersContextFacade = membersContextFacade;
  }

  // Obtener todos los miembros
  public List<Long> fetchAllMemberIds() {
    List<Long> memberIds = membersContextFacade.fetchAllMemberIds();
    return memberIds;
  }

  // Método para obtener un ID de miembro por nombre completo
  public Optional<Long> fetchMemberIdByFullName(String fullName) {
    Long memberId = membersContextFacade.fetchMemberIdByFullName(fullName);
    if (memberId.equals(0L)) {
      return Optional.empty();
    }
    return Optional.of(memberId);
  }
}

