package pe.edu.upc.managewise.backend.meeting.interfaces.rest.transform;


import pe.edu.upc.managewise.backend.meeting.domain.model.commands.UpdateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources.MeetingResource;

public class UpdateMeetingCommandFromResourceAssembler {
    public static UpdateMeetingCommand toCommandFromResource(Long meetingId, MeetingResource resource) {
        return new UpdateMeetingCommand(meetingId, resource.title(), resource.dateStr(), resource.timeStr(), resource.link());
    }
}
