// An interface to the business logic, living in the service sub-package.

package com.example.welcome.service;

import com.example.welcome.model.*;

import org.springframework.stereotype.Service;
@Service
public interface InterestService
{
    // Adds a welcome to the database, or overwrites an existing one.
    void addInterest(Interest interest);

}
