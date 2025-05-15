package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.entities.TaskItem;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.TaskItemResource;

public class TaskItemResourceFromEntityAssembler {
    public static TaskItemResource toResourceFromEntity(TaskItem taskItem){
        return new TaskItemResource(
                taskItem.getId(),
                taskItem.getTitle(),
                taskItem.getDescription(),
                taskItem.getStatus(),
                taskItem.getEstimation());
    }
}
