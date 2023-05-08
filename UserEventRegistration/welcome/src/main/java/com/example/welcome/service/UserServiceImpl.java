// Implementation of the business logic, living in the service sub-package.
// Discoverable for auto-configuration, thanks to the @Component annotation.

package com.example.welcome.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.welcome.model.*;

@Component
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository db;
    
    public UserServiceImpl(UserRepository db) {
        this.db = db;
    }

    @Override
    public void addUser(User user) {
        db.save(user);
    }

    @Override
    public User getUser(String eid) {
        return db.findByUid(eid);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        (db.findAll()).forEach(listUsers::add);;
        return listUsers;
    }

    @Override
    public void removeUser(long eid) {
        db.delete(db.findById(eid).get());        
    }

    
}
