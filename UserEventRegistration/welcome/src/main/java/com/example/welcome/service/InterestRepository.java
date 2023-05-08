
package com.example.welcome.service;

// import java.util.List;
import com.example.welcome.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends CrudRepository<Interest, String>{
}