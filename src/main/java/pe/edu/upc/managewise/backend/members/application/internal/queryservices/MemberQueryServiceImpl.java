package pe.edu.upc.managewise.backend.members.application.internal.queryservices;

import org.springframework.stereotype.Service;
//import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Sprint;
//import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetSprintByTittleQuery;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetAllMembersQuery;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMemberByFullNameQuery;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMemberByIdQuery;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMembersByRoleQuery;
import pe.edu.upc.managewise.backend.members.domain.services.MemberQueryService;
import pe.edu.upc.managewise.backend.members.infrastructure.persistence.jpa.repositories.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberQueryServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> handle(GetAllMembersQuery query) {
        return this.memberRepository.findAll();
    }

    @Override
    public Optional<Member> handle(GetMemberByIdQuery query) {
        return this.memberRepository.findById(query.memberId());
    }

    @Override
    public Optional<Member> handle(GetMembersByRoleQuery query) {
        return this.memberRepository.findByRole(query.role());
    }

    @Override
    public Optional<Member> handle(GetMemberByFullNameQuery query) {
        return this.memberRepository.findByFullName(query.fullName());
    }
}