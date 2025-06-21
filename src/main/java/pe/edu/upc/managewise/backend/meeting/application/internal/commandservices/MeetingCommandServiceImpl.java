package pe.edu.upc.managewise.backend.meeting.application.internal.commandservices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.meeting.application.internal.outboundservices.acl.MeetingExternalMemberService;
import pe.edu.upc.managewise.backend.meeting.domain.model.aggregates.Meeting;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.CreateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.CreateRecordingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.DeleteMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.UpdateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.services.MeetingCommandService;
import pe.edu.upc.managewise.backend.meeting.domain.services.RecordingCommandService;
import pe.edu.upc.managewise.backend.meeting.infrastructure.persistence.jpa.repositories.MeetingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class MeetingCommandServiceImpl implements MeetingCommandService {

    private final MeetingRepository meetingRepository;
    private final RecordingCommandService recordingCommandService; // Declarar la variable aquí
  private final MeetingExternalMemberService externalMemberService; // Servicio para obtener miembros

    @Autowired
    public MeetingCommandServiceImpl(MeetingRepository meetingRepository, RecordingCommandService recordingCommandService,MeetingExternalMemberService externalMemberService) {
        this.meetingRepository = meetingRepository;
        this.recordingCommandService = recordingCommandService; // Inicializar la variable aquí
      this.externalMemberService = externalMemberService; // Inicializar el servicio
    }

    @Override
    public Long handle(CreateMeetingCommand command) {
        var meetingTitle = command.title();
        if (this.meetingRepository.existsByTitle(meetingTitle)) {
            throw new IllegalArgumentException("Meeting with title " + meetingTitle + " already exists");
        }

      List<Long> memberIds = externalMemberService.fetchAllMemberIds();
      if (memberIds.isEmpty()) {
        throw new IllegalArgumentException("No members found");
      }
      // Seleccionar un host aleatorio de la lista de miembros
      Long hostId = memberIds.get(new Random().nextInt(memberIds.size()));

        var meeting = new Meeting(command);

      meeting.setHostId(hostId); // Establecer el host

      // Establecer la lista de miembros (todos los miembros)
      meeting.setMembers(memberIds);
      try {
            this.meetingRepository.save(meeting);

            // Crear la grabación automáticamente
            CreateRecordingCommand recordingCommand = new CreateRecordingCommand(
                    meeting.getId(),                      // ID de la reunión creada
                    generateRandomLink(),                // Generar un enlace aleatorio
                    generateRandomDuration(),             // Generar una duración aleatoria
                    generateRandomAccess()                // Generar acceso aleatorio (público o privado)
            );
            recordingCommandService.handle(recordingCommand); // Llamar al servicio de grabaciones

        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving meeting: " + e.getMessage());
        }
        return meeting.getId();
    }

    @Override
    public Optional<Meeting> handle(UpdateMeetingCommand command) {
        var meetingId = command.meetingId();
        var meetingTitle = command.title();

        if (this.meetingRepository.existsByTitleAndIdIsNot(meetingTitle, meetingId)) {
            throw new IllegalArgumentException("Meeting with title " + meetingTitle + " already exists");
        }

        // Check if the meeting exists
        if (!this.meetingRepository.existsById(meetingId)) {
            throw new IllegalArgumentException("Meeting with id " + meetingId + " does not exist");
        }

        var meetingToUpdate = this.meetingRepository.findById(meetingId).orElseThrow(() ->
                new IllegalArgumentException("Meeting with id " + meetingId + " does not exist"));
        meetingToUpdate.updateMeeting(command.title(), command.dateStr(), command.timeStr(), command.link());

        try {
            var updatedMeeting = this.meetingRepository.save(meetingToUpdate);
            return Optional.of(updatedMeeting);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating meeting: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteMeetingCommand command) {
        // Check if the meeting exists
        if (!this.meetingRepository.existsById(command.meetingId())) {
            throw new IllegalArgumentException("Meeting with id " + command.meetingId() + " does not exist");
        }

        // Try to delete the meeting
        try {
            this.meetingRepository.deleteById(command.meetingId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting meeting: " + e.getMessage());
        }
    }
    private String generateRandomLink() {
        // Lógica para generar un enlace aleatorio
        return "http://example.com/recording/" + System.currentTimeMillis(); // Ejemplo simple
    }

    private String generateRandomDuration() {
        // Lógica para generar una duración aleatoria (ejemplo: "00:30:00" para 30 minutos)
        int minutes = (int) (Math.random() * 60); // Genera un número aleatorio de minutos
        int seconds = (int) (Math.random() * 60); // Genera un número aleatorio de segundos
        return String.format("%02d:%02d:%02d", 0, minutes, seconds); // Formato HH:MM:SS
    }

    private boolean generateRandomAccess() {
        // Genera un valor booleano aleatorio
        return Math.random() < 0.5; // 50% de probabilidad de ser verdadero (público o privado)
    }

}
