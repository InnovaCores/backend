package pe.edu.upc.managewise.backend.issues.infrastructure.persistence.jpa.issues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.managewise.backend.issues.domain.model.aggregates.Issue;

import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long>{
    boolean existsByTitleAndIdIsNot(String title, Long id);
    boolean existsByTitleAndSprintAssociate(String title, String sprintAssociate);
    Optional<Issue> findByTitle(String title);
}

