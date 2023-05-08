package com.example.welcome.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table; 
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "users")
public class User {
    @Id
    private String uid;
    private String name;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private Set<Event> events = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name ="user_interests",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests = new HashSet<>();
    
    /**
     * Empty constructor
     */
    public User(){}
    
    /**
     * Constructor with no interests
     * @param uid user id
     * @param name user name
     */
    public User(String uid, String name){
        this.uid = uid;
        this.name = name;
    }

    /**
     * Copy constructor
     * @param that a User
     */
    public User(User that){
        this.uid = that.uid;
        this.name = that.name;
        this.events = that.events;
    }

    /**
     * The constructor
     * @param uid user id
     * @param name user name
     * @param interests user's interests
     */
    public User (String uid, String name, Set<Interest> interests){
        super();
        this.interests = interests;
    }

    /**
     * @return long return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String[] return the interests
     */
    public Set<Interest> getInterests() {
        return interests;
    }

    /**
     * @param interests the interests to set
     */
    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }


    /**
     * @return List<Event> return the events
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    /**
     * @param Interest addInterest
     */
    public void addInterest(Interest interest) {
        interests.add(interest);
    }



    public void removeAllInterests() {
        interests.clear();
    }



}