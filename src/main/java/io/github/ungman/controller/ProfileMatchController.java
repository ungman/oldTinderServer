package io.github.ungman.controller;

import io.github.ungman.controller.utils.RestAbstractController;
import io.github.ungman.domain.Profile;
import io.github.ungman.domain.ProfileMatch;
import io.github.ungman.service.ProfileMatchService;
import io.github.ungman.service.ProfileService;
import io.github.ungman.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/profilematch/{id}")
public class ProfileMatchController implements RestAbstractController<ProfileMatch> {

    private  final ProfileMatchService profileMatchService;
    private  final UserService userService;
    private  final ProfileService profileService;

    @RequestMapping("list")
    public List<ProfileMatch> getList(@PathVariable Long id) {
        return profileMatchService.getList(id);
    }
    @Override
    public List<ProfileMatch> getList() {
        return null;
    }

    @Override
    public ProfileMatch getOne(Long id) {
        return profileMatchService.getOne(id);
    }

    @Override
    public ProfileMatch create(ProfileMatch obj) {
        return profileMatchService.create(obj);
    }

    @Override
    public ProfileMatch update(ProfileMatch obj) {
        return profileMatchService.update(obj);
    }

    @Override
    public void delete(ProfileMatch obj) {
        profileMatchService.delete(obj);
    }

    @PostMapping("/checkMatch")
    public boolean isMatch(@PathVariable(name = "id") String  username){
        Long idUser=userService.getByUsername(username).getIdUser();
        Long idLiked=profileService.getFullProfile(idUser).getIdShown();
        return profileMatchService.isMatchCurrent(idUser,idLiked);
    }

    @PostMapping("/set")
    public ProfileMatch setMatch(@PathVariable(name = "id") String  username){
        Long idUser=userService.getByUsername(username).getIdUser();
        Long idLiked=profileService.getFullProfile(idUser).getIdShown();
        return profileMatchService.createMatch(idUser,idLiked);
    }

    @GetMapping("/getMatches")
    public List<Profile> getListMatchesProfile(@PathVariable(name="id")String username){
        Long idUser=userService.getByUsername(username).getIdUser();
        return profileMatchService.getMatches(idUser).stream()
                .map(profileMatch -> profileService.getOne(profileMatch.getIdSecondUser()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/del")
    public void deleteMatch(@PathVariable(name = "id") String username){
        Long idUser=userService.getByUsername(username).getIdUser();
        profileMatchService.deleteMatches(idUser);
    }

}
