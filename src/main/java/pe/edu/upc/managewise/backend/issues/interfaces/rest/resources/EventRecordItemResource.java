package pe.edu.upc.managewise.backend.issues.interfaces.rest.resources;

public record EventRecordItemResource(
        Long id, // Add this field
        String createdDate,
        String madeBy,
        String eventName,
        String description
) {}
