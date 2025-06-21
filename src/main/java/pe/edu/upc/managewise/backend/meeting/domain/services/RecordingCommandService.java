package pe.edu.upc.managewise.backend.meeting.domain.services;


import pe.edu.upc.managewise.backend.meeting.domain.model.commands.CreateRecordingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.DeleteRecordingCommand;

public interface RecordingCommandService {
    Long handle(CreateRecordingCommand command);
    void handle(DeleteRecordingCommand command);
}


