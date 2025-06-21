package pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

public record MeetingResource(
        Long id,
        String title,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") String dateStr,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") String timeStr,
        String link,
        String accessCode,
        RecordingResource recording,
        Long host,
        List<Long> members
) {}
