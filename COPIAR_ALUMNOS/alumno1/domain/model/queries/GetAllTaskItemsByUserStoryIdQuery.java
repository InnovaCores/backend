package pe.edu.upc.managewise.backend.backlog.domain.model.queries;

public class GetAllTaskItemsByUserStoryIdQuery{
    private final Long userStoryId;

    public GetAllTaskItemsByUserStoryIdQuery(Long userStoryId){
        this.userStoryId = userStoryId;}
    public Long getUserStoryId(){ return userStoryId;}
}
