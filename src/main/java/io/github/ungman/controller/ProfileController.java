package io.github.ungman.controller;

import io.github.ungman.controller.utils.RestAbstractController;
import io.github.ungman.domain.Profile;
import io.github.ungman.service.ProfileService;
import io.github.ungman.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/profile")
public class ProfileController implements RestAbstractController<Profile> {

    private final ProfileService profileService;
    private final UserService userService;

    @Override
    public List<Profile> getList() {
        return profileService.getList();
    }

    @Override
    public Profile getOne(Long id) {
        return profileService.getOne(id);
    }

    @Override
    public Profile create(Profile obj) {
        return profileService.save(obj);
    }

    @Override
    public Profile update(Profile obj) {
        return profileService.update(obj);
    }
    @Override
    public void delete(Profile obj) {
        profileService.delete(obj);
    }

    @GetMapping("{username}/show/current/")
    public Profile showCurrent(@PathVariable String username){
        return profileService.showCurrentProfile(userService.getByUsername(username).getIdUser());
    }

    @GetMapping("show/next/{username}")
    public Profile shownNext(@PathVariable String username, Principal principal){
        boolean isAuth = null != principal;
        Long idUser=-1L;
        if(isAuth)
            idUser=userService.getByUsername(username).getIdUser();
        else
            idUser=Long.parseLong(username);

        return profileService.showNextProfile(idUser,isAuth);
    }

    @DeleteMapping("{username}")
    public void delete(@PathVariable(name = "username") String username){
        Long idUser=userService.getByUsername(username).getIdUser();
        profileService.deleteById(idUser);
    }

    @PutMapping("{username}")
    public void editProfile(@PathVariable(name = "username") String username,@RequestBody Profile profile){
        Long idUser=userService.getByUsername(username).getIdUser();
        profile.setIdUser(idUser);
        profileService.editProfile(profile);
    }
}
