package pe.edu.upc.managewise.backend.meeting.domain.services;

import pe.edu.upc.managewise.backend.meeting.domain.model.aggregates.Meeting;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.CreateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.DeleteMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.UpdateMeetingCommand;

import java.util.Optional;

public interface MeetingCommandService {
    Long handle(CreateMeetingCommand command);
    Optional<Meeting> handle(UpdateMeetingCommand command);
    void handle(DeleteMeetingCommand command);
}
