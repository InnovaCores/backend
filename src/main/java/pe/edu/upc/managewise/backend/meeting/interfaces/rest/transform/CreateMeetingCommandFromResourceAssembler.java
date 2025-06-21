package pe.edu.upc.managewise.backend.meeting.interfaces.rest.transform;


import pe.edu.upc.managewise.backend.meeting.domain.model.commands.CreateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources.CreateMeetingResource;

public class CreateMeetingCommandFromResourceAssembler {
    public static CreateMeetingCommand toCommandFromResource(CreateMeetingResource resource) {
        return new CreateMeetingCommand(resource.title(), resource.dateStr(), resource.timeStr(), resource.link());
    }
}
