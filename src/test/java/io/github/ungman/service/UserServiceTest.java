package io.github.ungman.service;


import io.github.ungman.domain.User;
import io.github.ungman.repo.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepo userRepo;

    @Test
    public void save() {
       User user=userFactory(1L,"","").get();
       Mockito.when(userRepo.save(user)).thenReturn(user);
       User user1=userService.save(user);
       assertThat(user1).isNotNull();
       assertThat(user1.getUsername()).isEqualTo("");
       Mockito.verify(userRepo,Mockito.times(1)).save(user);
    }

    @Test
    public void getList() {
        List<User> users=new ArrayList<>();
        users.add(userFactory("1","1"));
        users.add(userFactory("2","1"));
        users.add(userFactory("3","1"));
        Mockito.when(userRepo.findAll()).thenReturn(users);
        List<User> usersTestList=userService.getList();
        assertThat(usersTestList.size()).isEqualTo(3);
        Mockito.verify(userRepo,Mockito.times(1)).findAll();
    }

    @Test
    public void getOne() {
        Mockito.when(userRepo.findById(1L)).thenReturn(userFactory(1L,"1","1"));
        User user=userService.getOne(1L);
        assertThat(user).isNotNull();
        assertThat(user.getIdUser()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("1");
        assertThat(user.getPassword()).isEqualTo("");
        Mockito.verify(userRepo,Mockito.times(1)).findById(1L);
        User userNull=userService.getOne(2L);
        assertThat(userNull).isNull();
        Mockito.verify(userRepo,Mockito.times(1)).findById(2L);

    }

    @Test
    public void update() {
        Mockito.when(userRepo.findById(1L)).thenReturn(userFactory(1L,"1","1"));
        User user=userService.getOne(1L);
        user.setUsername("2");
        Mockito.when(userRepo.save(user)).thenReturn(user);
        User user1=userService.update(user);
        assertThat(user1).isNotNull();
        assertThat(user1.getIdUser()).isEqualTo(1L);
        assertThat(user1.getUsername()).isEqualTo("2");
        Mockito.verify(userRepo,Mockito.times(1)).findById(1L);
        Mockito.verify(userRepo,Mockito.times(1)).save(user);

    }

    @Test
    public void delete() {
        User user = new User();
        Mockito.doNothing().when(userRepo).delete(user);
        userService.delete(user);
        Mockito.verify(userRepo,Mockito.times(1)).delete(user);
    }

    @Test
    public void getByUsername() {
        String login="1";
        Mockito.when(userRepo.findByUsername(login)).thenReturn(userFactory(1L,login,"1"));
        User user=userService.getByUsername(login);
        assertThat(user).isNotNull();
        assertThat(user.getIdUser()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo(login);
    }

    @Test
    public void deleteById() {
        Optional<User> user=userFactory(1L,"","");
        Mockito.when(userRepo.findById(1L)).thenReturn(user);
        Mockito.doNothing().when(userRepo).delete(user.get());
        userService.deleteById(1L);
        Mockito.verify(userRepo,Mockito.times(1)).delete(user.get());
    }

    @Test
    public void editUser() {
        Mockito.when(userRepo.findById(1L)).thenReturn(userFactory(1L,"1","1"));
        User user=userService.getOne(1L);
        user.setUsername("2");
        Mockito.when(userRepo.save(user)).thenReturn(user);
        User user1=userService.update(user);
        assertThat(user1).isNotNull();
        assertThat(user1.getIdUser()).isEqualTo(1L);
        assertThat(user1.getUsername()).isEqualTo("2");
        Mockito.verify(userRepo,Mockito.times(1)).findById(1L);
        Mockito.verify(userRepo,Mockito.times(1)).save(user);
    }

    private User userFactory(String username, String password){
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    private Optional<User> userFactory(Long id, String username, String password){
        User user=new User();
        user.setIdUser(id);
        user.setUsername(username);
        user.setPassword(password);
        return Optional.of(user);
    }
}