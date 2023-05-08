// Implementation of the business logic, living in the service sub-package.
// Discoverable for auto-configuration, thanks to the @Component annotation.

package com.example.welcome.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.welcome.model.*;

@Component
public class EventServiceImpl implements EventService
{
    @Autowired
    private EventRepository db;
    
    /**
     * Constructor
     * @param db the events database
     */
    public EventServiceImpl(EventRepository db) {
        this.db = db;
    }
    
    /**
     * @param event the event to add to the database
     */
    public void addEvent(Event event) {
        db.save(event);
    }

    /**
     * Check whether an event with the event ID exists
     * @param eid the event ID
     * @return whether the event is stored in the database
     */
    public boolean exists(long eid){
        return db.findById(eid).isPresent();
    }

    /**
     * Get all events
     * @return all events
     */
    public List<Event> getAllEvents() {
        return db.findAll();
    }

    /**
     * Remove an event from the database
     * @param eid the event id
     */
    public void removeEvent(long eid) {
        db.delete(getEvent(eid));
    }

    /**
     * Get an event from the database
     * @param eid the event ID
     * @return Event 
     */
    public Event getEvent(long eid) {
        return db.findById(eid).get();
    }
}
