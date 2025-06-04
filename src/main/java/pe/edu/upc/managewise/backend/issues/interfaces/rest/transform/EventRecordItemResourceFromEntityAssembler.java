package pe.edu.upc.managewise.backend.issues.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.issues.domain.model.entities.EventRecordItem;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.EventRecordItemResource;

public class EventRecordItemResourceFromEntityAssembler {
    public static EventRecordItemResource toResourceFromEntity(EventRecordItem eventRecordItem) {
        return new EventRecordItemResource(
                eventRecordItem.getId(), // Ensure the ID is included
                eventRecordItem.getCreatedDate(),
                eventRecordItem.getMadeBy(),
                eventRecordItem.getEventName(),
                eventRecordItem.getDescription()
        );
    }
}