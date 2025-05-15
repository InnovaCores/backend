package pe.edu.upc.managewise.backend.backlog.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Epic;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateEpicCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteEpicCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateEpicCommand;
import pe.edu.upc.managewise.backend.backlog.domain.services.EpicCommandService;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.EpicRepository;

import java.util.Optional;

@Service
public class EpicCommandServiceImpl implements EpicCommandService {
    private final EpicRepository epicRepository;

    public EpicCommandServiceImpl(EpicRepository epicRepository) {
        this.epicRepository = epicRepository;
    }

    @Override
    public Long handle(CreateEpicCommand command){
        var title = command.title();
        // Comprobamos título único para evitar duplicados y lanzamos excepción descriptiva en caso contrario
        if (this.epicRepository.existsByTitle(title)){
            throw new IllegalArgumentException("Epic with " + title + " as title already exists");
        }
        var epic = new Epic(command);
        try{
            this.epicRepository.save(epic);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving epic: " + e.getMessage());
        }
        return epic.getId();
    }

    @Override
    public Optional<Epic> handle(UpdateEpicCommand command){
        var epicId = command.id();
        var title = command.title();
        if(this.epicRepository.existsByTitleAndIdIsNot(title, epicId)){
            throw new IllegalArgumentException("Epic with " + title + " as title already exists");
        }

        if(!this.epicRepository.existsById(epicId)){
            throw new IllegalArgumentException("Epic does not exist");
        }

        var epicToUpdate = this.epicRepository.findById(epicId).get();
        epicToUpdate.updateInformation(command.title(), command.description());

        try{
            var updatedEpic = this.epicRepository.save(epicToUpdate);
            return Optional.of(updatedEpic);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating epic: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteEpicCommand command){
        if (!this.epicRepository.existsById(command.epicId())){
            throw new IllegalArgumentException("Epic with id " + command.epicId() + " does not exist");
        }

        try{
            this.epicRepository.deleteById(command.epicId());
        } catch (Exception e){
            throw new IllegalArgumentException("Error while deleting epic: " + e.getMessage());
        }
    }
}
