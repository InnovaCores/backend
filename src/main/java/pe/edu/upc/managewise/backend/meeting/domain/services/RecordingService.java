package pe.edu.upc.managewise.backend.meeting.domain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.meeting.domain.model.aggregates.Meeting;
import pe.edu.upc.managewise.backend.meeting.domain.model.entities.Recording;
import pe.edu.upc.managewise.backend.meeting.infrastructure.persistence.jpa.repositories.RecordingRepository;

import java.util.List;

@Service
public class RecordingService {
    @Autowired
    private RecordingRepository recordingRepository;

    public List<Recording> getAllRecordings() {
        return recordingRepository.findAll();
    }

    public List<Recording> getRecordingsByMeeting(Meeting meeting) {
        return recordingRepository.findByMeeting(meeting);
    }
}
