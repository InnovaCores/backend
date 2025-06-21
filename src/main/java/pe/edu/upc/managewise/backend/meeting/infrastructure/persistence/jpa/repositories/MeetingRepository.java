package pe.edu.upc.managewise.backend.meeting.infrastructure.persistence.jpa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.managewise.backend.meeting.domain.model.aggregates.Meeting;

import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    boolean existsByTitle(String title);
    boolean existsByTitleAndIdIsNot(String title, Long id);
    Optional<Meeting> findByTitle(String title);
}
