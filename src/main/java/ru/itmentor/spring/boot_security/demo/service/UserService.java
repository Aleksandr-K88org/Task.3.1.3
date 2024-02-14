package ru.itmentor.spring.boot_security.demo.service;



import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    void save(User user);
    Set<User> findAll();
    Optional<User> findById(Long id);
    void updateUser(User user);
    void deleteById(Long id);
    Optional<User> findByName(String name);
}
