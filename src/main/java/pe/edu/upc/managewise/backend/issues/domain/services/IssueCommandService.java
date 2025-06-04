package pe.edu.upc.managewise.backend.issues.domain.services;
import pe.edu.upc.managewise.backend.issues.domain.model.aggregates.Issue;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateEventByIssueIdCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.DeleteIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.UpdateIssueCommand;

import java.util.Optional;

public interface IssueCommandService {
    Long handle(CreateIssueCommand command);
    Optional<Issue> handle(UpdateIssueCommand command);
    void handle(DeleteIssueCommand command);
    Long handle(CreateEventByIssueIdCommand command);
}
