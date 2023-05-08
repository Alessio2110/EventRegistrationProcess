
package com.example.welcome.service;

import com.example.welcome.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    User findByUid(String lang);

}