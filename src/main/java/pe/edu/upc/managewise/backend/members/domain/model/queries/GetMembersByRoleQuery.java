package pe.edu.upc.managewise.backend.members.domain.model.queries;


import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.ScrumRoles;

public record GetMembersByRoleQuery(ScrumRoles role) {}