package pe.edu.upc.managewise.backend.issues.application.internal.queryservices;
import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.issues.domain.model.aggregates.Issue;
import pe.edu.upc.managewise.backend.issues.domain.model.entities.EventRecordItem;
import pe.edu.upc.managewise.backend.issues.domain.model.queries.GetAllEventIssueItemByIssueId;
import pe.edu.upc.managewise.backend.issues.domain.model.queries.GetAllIssuesQuery;
import pe.edu.upc.managewise.backend.issues.domain.model.queries.GetIssueByIdQuery;
import pe.edu.upc.managewise.backend.issues.domain.model.queries.GetIssueByTitleQuery;
import pe.edu.upc.managewise.backend.issues.domain.services.IssueQueryService;
import pe.edu.upc.managewise.backend.issues.infrastructure.persistence.jpa.issues.IssueRepository;

import java.util.List;
import java.util.Optional;
@Service
public class IssueQueryServiceImpl implements IssueQueryService {

    private final IssueRepository issueRepository;

    public IssueQueryServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public List<Issue> handle(GetAllIssuesQuery query) {
        return this.issueRepository.findAll();
    }

    @Override
    public Optional<Issue> handle(GetIssueByIdQuery query) {
        return this.issueRepository.findById(query.issueId());
    }

    @Override
    public Optional<Issue> handle(GetIssueByTitleQuery query) {
        return this.issueRepository.findByTitle(query.title());
    }

    @Override
    public List<EventRecordItem> handle(GetAllEventIssueItemByIssueId query) {
        // Implement the logic to retrieve all events by issueId
        // This is a placeholder implementation
        return List.of(); // Replace with actual implementation
    }
}
