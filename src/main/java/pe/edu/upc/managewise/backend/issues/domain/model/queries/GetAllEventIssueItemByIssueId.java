package pe.edu.upc.managewise.backend.issues.domain.model.queries;

public class GetAllEventIssueItemByIssueId {
    private final Long issueId;

    public GetAllEventIssueItemByIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getIssueId() {
        return issueId;
    }
}