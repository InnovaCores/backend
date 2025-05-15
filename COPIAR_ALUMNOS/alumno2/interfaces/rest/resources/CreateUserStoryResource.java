package pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources;

public record CreateUserStoryResource(
        String title,
        String description,
        Long epicId,
        Long sprintId,
        Integer effort) {
}
