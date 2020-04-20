package io.github.ungman.controller;

import io.github.ungman.controller.utils.RestAbstractController;
import io.github.ungman.domain.Profile;
import io.github.ungman.domain.User;
import io.github.ungman.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController implements RestAbstractController<User> {

    private final UserService userService;

    @Override
    public List<User> getList() {
        return userService.getList();
    }

    @Override
    public User getOne(Long id) {
        return userService.getOne(id);
    }

    @Override
    public User create(User obj) {
        return userService.save(obj);
    }

    @Override
    public User update(User obj) {
        return userService.update(obj);
    }

    @Override
    public void delete(User obj) {
        userService.delete(obj);
    }

    @DeleteMapping("{username}")
    public void delete(@PathVariable(name="username") String username){
        Long idUser=userService.getByUsername(username).getIdUser();
        userService.deleteById(idUser);
    }

    @PutMapping("{username}")
    public void editUser(@PathVariable(name = "username") String username,@RequestBody User user){
        Long idUser=userService.getByUsername(username).getIdUser();
        user.setIdUser(idUser);
        userService.editUser(user);
    }

}
