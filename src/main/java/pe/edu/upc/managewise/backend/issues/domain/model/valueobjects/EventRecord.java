package pe.edu.upc.managewise.backend.issues.domain.model.valueobjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import pe.edu.upc.managewise.backend.issues.domain.model.aggregates.Issue;
import pe.edu.upc.managewise.backend.issues.domain.model.entities.EventRecordItem;

import java.util.ArrayList;
import java.util.List;

//Este seria mi history que es un conjunto de eventos del issue
@Embeddable
public class EventRecord {
    @OneToMany(mappedBy = "issue",cascade = CascadeType.ALL)
    private List<EventRecordItem> eventRecordItems;

    public EventRecord() {
        this.eventRecordItems = new ArrayList<>();
    }

    public void AddItem(Issue issue,  String createdDate, String madeBy, String eventName, String description) {
        //Add the new item before the next item
        System.out.println("Adding item to event issue");
        EventRecordItem eventRecordItem = new EventRecordItem( issue,   createdDate,  madeBy,  eventName,  description);
        eventRecordItems.add(eventRecordItem);
    }

    private EventRecordItem getEventIssueItemWithId(Long itemId) {
        return eventRecordItems.stream().filter(eventIssueItem -> eventIssueItem.getId().equals(itemId))
                .findFirst().orElse(null);
    }

    public boolean isEmpty() {
        return eventRecordItems.isEmpty();
    }

    public List<EventRecordItem> getEventRecordItems() {
        return eventRecordItems;
    }
}
