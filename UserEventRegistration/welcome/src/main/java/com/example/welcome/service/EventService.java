// An interface to the business logic, living in the service sub-package.

package com.example.welcome.service;

import java.util.List;

import com.example.welcome.model.*;

import org.springframework.stereotype.Service;
@Service
public interface EventService
{
    /**
     * Add event to the database
     * @param event
     */
    void addEvent(Event event);

    /**
     * Get an event
     * @param eid the event ID
     * @return the event
     */
    Event getEvent(long eid);

    /**
     * Check whether an event exists
     * @param eid the event ID
     * @return whethr the event exists
     */
    boolean exists(long eid);

    /**
     * Get a list of all events
     * @return list of events
     */
    List<Event> getAllEvents();

    /**
     * Remove event from database
     * @param eid the event ID
     */
    void removeEvent(long eid);
}
