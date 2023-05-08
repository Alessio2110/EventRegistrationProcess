// An interface to the business logic, living in the service sub-package.

package com.example.welcome.service;

import java.util.List;
import com.example.welcome.model.*;

import org.springframework.stereotype.Service;
@Service
public interface UserService
{

    /**
     * Add user to database
     * @param user a new user
     */
    void addUser(User user);

    /**
     * Get a user for a given id
     * @param uid the user id
     * @return the user
     */
    User getUser(String uid);

    /**
     * Get all users
     * @return a list of all users
     */
    List<User> getAllUsers();

    // Removes the user from the database.
    /**
     * Remove a user given its id
     * @param uid the user id
     */
    void removeUser(long uid);
}
