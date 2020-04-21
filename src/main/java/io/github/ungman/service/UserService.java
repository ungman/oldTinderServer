package io.github.ungman.service;

import io.github.ungman.domain.User;
import io.github.ungman.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepo userRepo;

    public User save(User user) {
        if (user != null) {
            user = userRepo.save(user);
            user.setRoles(Collections.singletonList("USER"));
            user.setPassword("");
        }
        return user;
    }

    public List<User> getList() {

        return ((List<User>) userRepo.findAll()).stream()
                .map(User::setEmptyPassword)
                .collect(Collectors.toList());
    }

    public User getOne(Long idUser) {
        return userRepo.findById(idUser).map(User::setEmptyPassword).orElse(null);
    }

    public User update(User user) {
        return userRepo.save(user);
    }

    public void delete(User user) {
        userRepo.delete(user);
    }

    public User getByUsername(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        return user.orElse(null);
    }

    public void deleteById(Long idUser) {
        User user = getOne(idUser);
        delete(user);
    }

    public User editUser(User user) {
        return update(user);
    }
}
