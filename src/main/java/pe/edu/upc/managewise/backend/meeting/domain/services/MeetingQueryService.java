package pe.edu.upc.managewise.backend.meeting.domain.services;


import pe.edu.upc.managewise.backend.meeting.domain.model.aggregates.Meeting;
import pe.edu.upc.managewise.backend.meeting.domain.model.queries.GetAllMeetingsQuery;
import pe.edu.upc.managewise.backend.meeting.domain.model.queries.GetMeetingByIdQuery;
import pe.edu.upc.managewise.backend.meeting.domain.model.queries.GetMeetingByTitleQuery;

import java.util.List;
import java.util.Optional;

public interface MeetingQueryService {
    List<Meeting> handle(GetAllMeetingsQuery query);
    Optional<Meeting> handle(GetMeetingByIdQuery query);
    Optional<Meeting> handle(GetMeetingByTitleQuery query);
}
