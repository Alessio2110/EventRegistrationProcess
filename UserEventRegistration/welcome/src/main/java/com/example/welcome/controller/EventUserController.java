// The REST controller that handles HTTP requests.
// Lives in sub-package controller, marked with the @RestController annotation
// for auto-configuration; the @CrossOrigin annotation enables CORS.

package com.example.welcome.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
// import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import com.example.welcome.model.*;
import com.example.welcome.service.*;

@RestController
@CrossOrigin
public class EventUserController {
  // The WelcomeController depends on the EventService, UserService, InterestService
  private EventService es;
  private UserService us;
  private InterestService is;

  // The fact that the constructor for the EventUserController requires a
  // EventService, UserService, InterestService as arguments tells Spring to auto-configure each service
  // and pass them to the constructor. This is called "Dependency Injection",
  // and it (a) saves boilerplate code, and (b) makes it easy to swap
  // components.
  public EventUserController(EventService es, UserService us, InterestService is) {
    this.es = es;
    this.us = us;
    this.is = is;
  }

  /**
   * @return ok if the web service is running
   */
  @GetMapping("/works")
  public ResponseEntity<Void> isApplicationOnline() {
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  /**
   * @param eventId the id of the event to retrieve
   * @return the response status code + the event
   */
  @GetMapping("/events/{eventId}")
  public ResponseEntity<Event> getUserEvents(@PathVariable long eventId) {
    System.out.println("GET request at /events/" + eventId);
    
    if (!es.exists(eventId)) {
      System.out.println("No event found with eventID: " + eventId + ". Returning status 404 \n");
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    Event event = es.getEvent(eventId);
    System.out.println("Event found for eventID: " + eventId + ". Returning status 200 and the event itself \n");
    return new ResponseEntity<>(event, HttpStatus.OK);
  }

  /**
   * @param uid the id of the user
   * @return the list of events the user is currently registered for
   */
  @GetMapping("/users/{uid}")
  public ResponseEntity<List<Event>> getEvents(@PathVariable String uid) {
    System.out.println("GET request at /users/" + uid);

    User user = us.getUser(uid);
    if (user == null){
      System.out.println("No user found with userID: " + uid + ". Returning status 404 \n");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    List<Event> userEvents = new ArrayList<>(user.getEvents());
    System.out.println(userEvents.size() + "event found for userID: " + uid + ". Returning status 200 and the list of events. \n");
    return new ResponseEntity<>(userEvents, HttpStatus.OK);
  }

  /**
   * @return all the events
   */
  @GetMapping("/users")
  public ResponseEntity<List<Event>> getAllEvents() {
    List<Event> everyEvent = es.getAllEvents();
    System.out.println("GET request at /users. Returning all " + everyEvent.size() + " events. Status 200. \n");
    return new ResponseEntity<>(everyEvent, HttpStatus.OK);
  }

  /**
   * @param eid the Event ID
   * @return the attendee for such event id
   */
  @GetMapping("/admin/events/{eid}")
  public ResponseEntity<List<User>> listAttendees(@PathVariable long eid){
    System.out.println("GET request at /admin/events/" + eid);
    System.out.println("Trying to access list of attendees for event with id: " + eid);

    if (!es.exists(eid)){
      System.out.println("Event does not exist, returning status 400 \n");
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
      
    Event event = es.getEvent(eid);

    List<User> attendees = new ArrayList<>(event.getUsers());
    System.out.println("Returning " + attendees.size() + " attendees, status 200 \n");
    return new ResponseEntity<>(attendees, HttpStatus.OK);
  }

  /**
   * 
   * @param userId the attendee id
   * @return
   */
  @PutMapping("/users/{userId}")
  ResponseEntity<Void> updateUserInterests(@PathVariable String userId, @RequestBody User aUser) {
    System.out.println("PUT request, updating interests");

    Set<Interest> interests = aUser.getInterests();
    Consumer<Interest> lambdaexpression = interest -> {
      is.addInterest(interest);
      System.out.println("Adding interest '" + interest.getInterestName() +
          "' to user '" + aUser.getUid() + "' (" + aUser.getName() + ")");
    };

    interests.forEach(lambdaexpression);
    aUser.setInterests(interests);
    us.addUser(aUser);
    System.out.println("All " + interests.size() + " interests added for user " + userId + "\n");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // /**
  //  * Calculate most common interest in an event
  //  * @param eid
  //  */
  // public void calculateInterests(Long eid){
  //   Event e = es.getEvent(eid);
  //   Set<User> attendees = e.getUsers();
  //   HashMap<String, Integer> interestPopularity = new HashMap<>();

  //   Consumer<Interest> lambdaInterests = interest ->  {
  //     if(interestPopularity.containsKey(interest))
  //       interestPopularity.put(interest.getInterestName(), interestPopularity.get(interest.getInterestName()) + 1);
  //     else
  //       interestPopularity.put(interest.getInterestName(), 1);
  //   };

  //     for (User user: attendees){
  //       Set<Interest> interests = user.getInterests();
  //       interests.forEach(lambdaInterests);
  //     }
  // }

  /**
   * @param newUser the user attending the event
   * @param eid event id
   * @return status code and the user
   */
  @PostMapping("/events/{eid}")
  public ResponseEntity<User> newRegistration(@RequestBody User newUser, @PathVariable Long eid) {
    System.out.println("Post Request received at /events/" + eid);

    if (!es.exists(eid)) {
      System.out.println("No event exists with id" + eid + ". Returning 404 \n");
      return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    Event event = es.getEvent(eid);
    if (event.getCapacity() <= event.getAttending()){
      System.out.println("The event does not currently offer new registration, capacity is full. Returning 400 \n");
      return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }
      
    User existingUser = us.getUser(newUser.getUid());
    if (existingUser == null) {
      System.out.println("New user detected");
      Set<Interest> interests = newUser.getInterests();
      System.out.println("Number of interests: " + interests.size());
      
      Consumer<Interest> lambdaexpression = interest -> {
        is.addInterest(interest);
        System.out.println("Adding interest '" + interest.getInterestName() +
            "' to user '" + newUser.getUid() + "' (" + newUser.getName() + ")");
      };

      interests.forEach(lambdaexpression);
      us.addUser(newUser);
      event.addAttendee(newUser);
      es.addEvent(event);
      System.out.println("Registration succesfully added, status 201 \n");
      return new ResponseEntity<User>(HttpStatus.CREATED);
    }
    
    //If the user is already registered, return bad request
     if(event.getUsers().contains(existingUser)){
       System.out.println("User already exists, status 400 \n");
      return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
     }

    //If the user already exists add it to the event, don't update its interests.
      event.addAttendee(existingUser);
      es.addEvent(event);
      System.out.println("Registration succesful for existing user, status 201 \n");
      return new ResponseEntity<User>(HttpStatus.CREATED);
  }

  @DeleteMapping("/users/{userId}/events/{eventId}")
  public ResponseEntity<Void> deleteWelcome(@PathVariable String userId, @PathVariable Long eventId) {
    System.out.println("Delete operation received: /users/" +  userId + "/events/" + eventId);
    System.out.println("Delete operation: Cancelling registration for user '" + userId + "' for event '" + eventId + "'.");
    
    if (!es.exists(eventId)){
      System.out.println("The event with event id " + eventId +" does not exist. Return 404 \n");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    Event event = es.getEvent(eventId);
    User user = us.getUser(userId);
    if (user == null){
      System.out.println("The user with user id " + userId + " does not exist. Return 404 \n" );
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
      

    if (!event.getUsers().contains(user)){
      System.out.println("User was not registered for this event, bad request. \n");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    event.removeAttendee(user);
    es.addEvent(event);
    
    System.out.println("Registration succesfully cancelled, status 204 \n");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
