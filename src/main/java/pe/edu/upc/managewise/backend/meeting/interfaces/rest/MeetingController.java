package pe.edu.upc.managewise.backend.meeting.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.DeleteMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.queries.GetAllMeetingsQuery;
import pe.edu.upc.managewise.backend.meeting.domain.model.queries.GetMeetingByIdQuery;
import pe.edu.upc.managewise.backend.meeting.domain.services.MeetingCommandService;
import pe.edu.upc.managewise.backend.meeting.domain.services.MeetingQueryService;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources.CreateMeetingResource;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources.MeetingResource;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.transform.CreateMeetingCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.transform.MeetingResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.transform.UpdateMeetingCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/meetings", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Meetings", description = "Endpoints para la gestión de reuniones")
public class MeetingController {


    private final MeetingQueryService meetingQueryService;
    private final MeetingCommandService meetingCommandService;

    public MeetingController( MeetingQueryService meetingQueryService, MeetingCommandService meetingCommandService) {

        this.meetingQueryService = meetingQueryService;
        this.meetingCommandService = meetingCommandService;
    }

    @PostMapping
    public ResponseEntity<MeetingResource> createMeeting(@RequestBody CreateMeetingResource resource) {
        var createMeetingCommand = CreateMeetingCommandFromResourceAssembler.toCommandFromResource(resource);
        var meetingId = this.meetingCommandService.handle(createMeetingCommand);

        if (meetingId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getMeetingByIdQuery = new GetMeetingByIdQuery(meetingId);
        var optionalMeeting = this.meetingQueryService.handle(getMeetingByIdQuery);

        var meetingResource = MeetingResourceFromEntityAssembler.toResourceFromEntity(optionalMeeting.orElse(null));
        return new ResponseEntity<>(meetingResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MeetingResource>> getAllMeetings() {
        var getAllMeetingsQuery = new GetAllMeetingsQuery();
        var meetings = this.meetingQueryService.handle(getAllMeetingsQuery);
        var meetingResources = meetings.stream()
                .map(MeetingResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(meetingResources);
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingResource> getMeetingById(@PathVariable Long meetingId) {
        var getMeetingByIdQuery = new GetMeetingByIdQuery(meetingId);
        var optionalMeeting = this.meetingQueryService.handle(getMeetingByIdQuery);
        if (optionalMeeting.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var meetingResource = MeetingResourceFromEntityAssembler.toResourceFromEntity(optionalMeeting.get());
        return ResponseEntity.ok(meetingResource);
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity<MeetingResource> updateMeeting(@PathVariable Long meetingId, @RequestBody MeetingResource resource) {
        var updateMeetingCommand = UpdateMeetingCommandFromResourceAssembler.toCommandFromResource(meetingId, resource);
        var optionalMeeting = this.meetingCommandService.handle(updateMeetingCommand);

        if (optionalMeeting.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var meetingResource = MeetingResourceFromEntityAssembler.toResourceFromEntity(optionalMeeting.get());
        return ResponseEntity.ok(meetingResource);
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long meetingId) {
        var deleteMeetingCommand = new DeleteMeetingCommand(meetingId);
        this.meetingCommandService.handle(deleteMeetingCommand);
        return ResponseEntity.noContent().build();
    }
}
