package com.example.welcome.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private long eid;
    @Column
    private String description;
    @Column
    private String location;
    @Column
    private String date;
    @Column
    private String time;
    @Column
    private int duration;
    @Column
    private int capacity;
    @Column
    private int attending;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name ="registered_users",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    /**
     * Empty constructor
     */
    public Event(){}

    /**
     * Constructor
     * @param description
     * @param location
     * @param date
     * @param time
     * @param duration
     * @param capacity
     */
    public Event(
        String description, String location,
        String date, String time,
        int duration, int capacity) {
        this.description = description;
        this.location = location;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.capacity = capacity;
        this.attending = 0;
    }

    /**
     * Copy constructor
     * @param that an Event
     */
    public Event(Event that){
        this.description = that.description;
        this.location = that.location;
        this.date = that.date;
        this.time = that.time;
        this.duration = that.duration;
        this.capacity = that.capacity;
        this.attending = that.attending;
    }

    /**
     * @return long return the eid
     */
    public long getEid() {
        return eid;
    }

    /**
     * @param eid the eid to set
     */
    public void setEid(long eid) {
        this.eid = eid;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return String return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return Date return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return String return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return int return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return int return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @return int return the attending
     */
    public int getAttending() {
        return attending;
    }

    /**
     * @param attending the attending to set
     */
    public void setAttending(int attending) {
        this.attending = attending;
    }

    @Override
    public String toString(){
        return 
        "'eid':" + getEid() + "\n" +
        "'description': '" + getDescription() + "\n" +
        "'location: '" + getLocation() + "\n" +
        "'date': '" + getDate() + "\n" +
        "'time': '" + getTime() + "\n" +
        "'duration': '" + getDuration() + "\n" +
        "'capacity': '" + getCapacity() + "\n" +
        "'attending': '" + getAttending();
    }

    /**
     * @return List<User> return the list of users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * @param user the new user registered to the event
     */
    public void addAttendee(User user) {
        users.add(user);
        setAttending(getAttending() + 1);
    }

    /**
     * @param user the user who is no longer attending the event
     */
    public void removeAttendee(User user){
        users.remove(user);
        setAttending(getAttending() - 1);

    }

}
