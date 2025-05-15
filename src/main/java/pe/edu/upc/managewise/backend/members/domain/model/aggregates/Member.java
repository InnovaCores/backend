package pe.edu.upc.managewise.backend.members.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.managewise.backend.members.domain.model.commands.CreateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.ScrumRoles;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.StreetAddress;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "members")
public class Member extends AuditableAbstractAggregateRoot<Member> {
    @Getter
    @NotNull
    @NotBlank
    @Column(name = "full_name", length = 70, nullable = false)
    private String fullName;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ScrumRoles role;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "email", column = @Column(name = "email", length = 100, nullable = false))
    })
    private EmailAddress email;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "streetAddress", column = @Column(name = "street_address", length = 100, nullable = false))
    })
    private StreetAddress streetAddress;

    public Member() {
        this.role = ScrumRoles.SCRUM_MASTER;
    }
    public Member(String fullName, ScrumRoles role, String email, String streetAddress) {

        this.fullName = fullName;
        this.role = role;
        this.email = new EmailAddress(email);
        this.streetAddress = new StreetAddress(streetAddress);
    }

    public Member(CreateMemberCommand command) {
        this.fullName = command.fullName();
        this.role = command.role();
        this.email = new EmailAddress(command.email());
        this.streetAddress = new StreetAddress(command.streetAddress());
    }

    public Member updateInformation(String fullName, ScrumRoles role, String email, String streetAddress) {
        this.fullName = fullName;
        this.role = role;
        this.email = new EmailAddress(email);
        this.streetAddress = new StreetAddress(streetAddress);
        return this;
    }

    public String getEmail() {
        return email.email();
    }

    public String getStreetAddress() {
        return streetAddress.streetAddress();
    }
}