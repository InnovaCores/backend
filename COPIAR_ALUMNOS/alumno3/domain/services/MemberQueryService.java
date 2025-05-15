package pe.edu.upc.managewise.backend.members.domain.services;

import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetAllMembersQuery;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMemberByFullNameQuery;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMemberByIdQuery;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMembersByRoleQuery;

import java.util.List;
import java.util.Optional;

public interface MemberQueryService {
    List<Member> handle(GetAllMembersQuery query);
    Optional<Member> handle(GetMemberByIdQuery query);
    Optional<Member> handle(GetMembersByRoleQuery query);
    Optional<Member> handle(GetMemberByFullNameQuery query);
}