package pe.edu.upc.managewise.backend.members.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.commands.CreateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.DeleteMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.UpdateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.services.MemberCommandService;
import pe.edu.upc.managewise.backend.members.infrastructure.persistence.jpa.repositories.MemberRepository;

import java.util.Optional;

@Service
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    public MemberCommandServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Long handle(CreateMemberCommand command) {
        var fullName = command.fullName();
        if (this.memberRepository.existsByFullName(fullName)) {
            throw new IllegalArgumentException("Member with full name " + fullName + " already exists");
        }
        var member = new Member(command);
        try {
            this.memberRepository.save(member);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving member: " + e.getMessage());
        }
        return member.getId();
    }

    @Override
    public Optional<Member> handle(UpdateMemberCommand command) {
        var memberId = command.memberId();
        var fullName = command.fullName();
        if (this.memberRepository.existsByFullName(fullName)) {
            throw new IllegalArgumentException("Member with full name " + fullName + " already exists");
        }
        // If the profile does not exist, throw an exception
        if (!this.memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("Profile with id " + memberId + " does not exist");
        }

        var memberToUpdate = this.memberRepository.findById(memberId).get();
        memberToUpdate.updateInformation(command.fullName(), command.role(), command.email(), command.streetAddress());

        try {
            var updatedMember = this.memberRepository.save(memberToUpdate);
            return Optional.of(updatedMember);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating member: " + e.getMessage());
        }
    }


    @Override
    public void handle(DeleteMemberCommand command) {
        // If the profile does not exist, throw an exception
        //condition if the id value exists or not to delete it
        if (!this.memberRepository.existsById(command.memberId())) {
            throw new IllegalArgumentException("Member with id " + command.memberId() + " does not exist");
        }
        //Save and Try-catch
        // Try to delete the profile, if an error occurs, throw an exception
        try {
            this.memberRepository.deleteById(command.memberId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting member: " + e.getMessage());
        }
    }
}
