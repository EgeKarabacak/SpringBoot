package com.team20.repositories;

import java.util.Collection;
import java.util.List;

import com.team20.core.User;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    List<User> findByRolesIn(Collection<String> names);

    List<User> findByEnabled(boolean enabled);

    Long deleteByUsername(String username);
    
}
