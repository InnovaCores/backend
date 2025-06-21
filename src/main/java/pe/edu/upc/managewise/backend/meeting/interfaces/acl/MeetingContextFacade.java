package pe.edu.upc.managewise.backend.meeting.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.meeting.domain.model.aggregates.Meeting;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.CreateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.DeleteMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.UpdateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.queries.GetAllMeetingsQuery;
import pe.edu.upc.managewise.backend.meeting.domain.model.queries.GetMeetingByIdQuery;
import pe.edu.upc.managewise.backend.meeting.domain.model.queries.GetMeetingByTitleQuery;
import pe.edu.upc.managewise.backend.meeting.domain.services.MeetingCommandService;
import pe.edu.upc.managewise.backend.meeting.domain.services.MeetingQueryService;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources.MeetingResource;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.transform.MeetingResourceFromEntityAssembler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MeetingContextFacade {
    private final MeetingCommandService meetingCommandService;
    private final MeetingQueryService meetingQueryService;

    public MeetingContextFacade(MeetingCommandService meetingCommandService, MeetingQueryService meetingQueryService) {
        this.meetingCommandService = meetingCommandService;
        this.meetingQueryService = meetingQueryService;
    }

    // Buscar una reunión por ID y devolver como recurso
    public Optional<MeetingResource> fetchMeetingById(Long meetingId) {
        var getMeetingByIdQuery = new GetMeetingByIdQuery(meetingId);
        var optionalMeeting = meetingQueryService.handle(getMeetingByIdQuery);
        if (optionalMeeting.isEmpty()) {
            return Optional.empty();
        }
        var meetingResource = MeetingResourceFromEntityAssembler.toResourceFromEntity(optionalMeeting.get());
        return Optional.of(meetingResource);
    }

    public List<MeetingResource> fetchAllMeetings() {
        var getAllMeetingsQuery = new GetAllMeetingsQuery();
        List<Meeting> meetings = meetingQueryService.handle(getAllMeetingsQuery);

        return meetings.stream()
                .map(MeetingResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }


    // Buscar ID de una reunión usando el título
    public Long fetchMeetingIdByTitle(String title) {
        var getMeetingByTitleQuery = new GetMeetingByTitleQuery(title);
        var optionalMeeting = meetingQueryService.handle(getMeetingByTitleQuery);
        if (optionalMeeting.isEmpty()) {
            return 0L;
        }
        return optionalMeeting.get().getId();
    }

    // Verificar si existe una reunión con el título, excluyendo un ID específico
    public boolean existsMeetingByTitleAndIdIsNot(String title, Long id) {
        var getMeetingByTitleQuery = new GetMeetingByTitleQuery(title);
        var optionalMeeting = meetingQueryService.handle(getMeetingByTitleQuery);
        if (optionalMeeting.isEmpty()) {
            return false;
        }
        return optionalMeeting.get().getId() != id;
    }

    // Crear una nueva reunión
    public Long createMeeting(String title, String date, String time, String link) {
        var createMeetingCommand = new CreateMeetingCommand(title, date, time, link);
        var meetingId = meetingCommandService.handle(createMeetingCommand);
        if (meetingId == null) {
            return 0L;
        }
        return meetingId;
    }

    // Actualizar una reunión existente
    public Long updateMeeting(Long meetingId, String title, String date, String time, String link) {
        var updateMeetingCommand = new UpdateMeetingCommand(meetingId, title, date, time, link);
        var optionalMeeting = meetingCommandService.handle(updateMeetingCommand);
        if (optionalMeeting.isEmpty()) {
            return 0L;
        }
        return optionalMeeting.get().getId();
    }

    // Eliminar una reunión
    public void deleteMeeting(Long meetingId) {
        var deleteMeetingCommand = new DeleteMeetingCommand(meetingId);
        meetingCommandService.handle(deleteMeetingCommand);
    }
}
