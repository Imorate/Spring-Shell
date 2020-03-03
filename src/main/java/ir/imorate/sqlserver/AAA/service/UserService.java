package ir.imorate.sqlserver.AAA.service;

import ir.imorate.sqlserver.AAA.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();

    Optional<User> findByUsername(String username);

    User save(User user);

    void delete(User user);

    boolean existsByUsername(String username);
}
