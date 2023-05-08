// Implementation of the business logic, living in the service sub-package.
// Discoverable for auto-configuration, thanks to the @Component annotation.

package com.example.welcome.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.welcome.model.*;

@Component
public class InterestServiceImpl implements InterestService{
 
    @Autowired
    private InterestRepository db;

    /**
     * Constructor
     * @param db the interests database
     */
    public InterestServiceImpl(InterestRepository db) {
        this.db = db;
    }

    /**
     * @param interest the interest to add
     */
    public void addInterest(Interest interest) {
        db.save(interest);
    }
}