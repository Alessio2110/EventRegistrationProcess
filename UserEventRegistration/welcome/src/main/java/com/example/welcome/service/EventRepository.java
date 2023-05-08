
package com.example.welcome.service;

import java.util.List;

// import java.util.List;

import com.example.welcome.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>{
    List<Event> findAll();
}