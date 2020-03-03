package ir.imorate.sqlserver.AAA.service.implementation;

import ir.imorate.sqlserver.AAA.entity.User;
import ir.imorate.sqlserver.AAA.repository.UserRepository;
import ir.imorate.sqlserver.AAA.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Cant find user")));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
