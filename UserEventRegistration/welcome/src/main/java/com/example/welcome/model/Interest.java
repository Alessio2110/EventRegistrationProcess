package com.example.welcome.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Interest {

    @Id
    private String interestName;

    @JsonIgnore
    @ManyToMany(mappedBy = "interests")
    private Set<User> users = new HashSet<>();
    
    /**
     * Empty Constructor
     */
    public Interest(){}

    /**
     * Constructor
     * @param interestName the name of the interest
     */
    public Interest(String interestName){
        this.interestName = interestName;
    }

    /**
     * @return  return the interest name
     */
    public String getInterestName() {
        return interestName;
    }

    /**
     * @param interestName the interestName to set
     */
    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    /**
     * @return Set<User> return the users interested in the interest
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * @param users the users who will be interested in this interest
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

}