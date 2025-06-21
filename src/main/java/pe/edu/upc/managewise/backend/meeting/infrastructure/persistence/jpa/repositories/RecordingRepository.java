package pe.edu.upc.managewise.backend.meeting.infrastructure.persistence.jpa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.managewise.backend.meeting.domain.model.aggregates.Meeting;
import pe.edu.upc.managewise.backend.meeting.domain.model.entities.Recording;

import java.util.List;

public interface    RecordingRepository extends JpaRepository<Recording, Long> {
    List<Recording> findByMeeting(Meeting meeting); // Buscar grabaciones por reunión
}
