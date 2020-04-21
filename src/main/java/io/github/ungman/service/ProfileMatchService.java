package io.github.ungman.service;

import io.github.ungman.domain.ProfileMatch;
import io.github.ungman.repo.ProfileMatchRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProfileMatchService {

    private final ProfileMatchRepo profileMatchRepo;

    public List<ProfileMatch> getList(Long id) {
        List<ProfileMatch> profileMatches = ((List<ProfileMatch>) profileMatchRepo.findAll());
        List<ProfileMatch> listSetLike = profileMatches.stream()
                .filter(prm -> Objects.equals(prm.getIdFirstUser(), id))
                .collect(Collectors.toList());
        List<ProfileMatch> listGetLike = profileMatches.stream()
                .filter(prm -> Objects.equals(prm.getIdSecondUser(), id))
                .collect(Collectors.toList());
        return listSetLike.stream()
                .filter(prmSL -> listGetLike.stream()
                        .anyMatch(prmGL -> Objects.equals(prmSL.getIdSecondUser(), prmGL.getIdFirstUser())))
                .collect(Collectors.toList());
    }

    public ProfileMatch getOne(Long id) {
        return profileMatchRepo.findById(id).orElse(null);
    }

    public ProfileMatch create(ProfileMatch obj) {
        return profileMatchRepo.save(obj);
    }

    public ProfileMatch update(ProfileMatch obj) {
        return profileMatchRepo.save(obj);
    }

    public void delete(ProfileMatch obj) {
        profileMatchRepo.delete(obj);
    }

    public Boolean isMatchCurrent(Long idUser, Long idLiked) {
        return getList(idLiked).stream().anyMatch(profileMatch -> Objects.equals(profileMatch.getIdSecondUser(), idUser));
    }

    public List<ProfileMatch> getMatches(Long idUser) {
        return getList(idUser).stream()
                .filter(profileMatch -> isMatchCurrent(idUser, profileMatch.getIdSecondUser()))
                .collect(Collectors.toList());
    }

    public ProfileMatch createMatch(Long idUser, Long idLiked) {
        ProfileMatch profileMatch = new ProfileMatch();
        profileMatch.setIdFirstUser(idUser);
        profileMatch.setIdSecondUser(idLiked);
        return create(profileMatch);
    }

    public void deleteMatches(Long idUser) {
//        profileMatchRepo.deleteByIdFirstUserOrIdSecondUser(idUser,idUser);
        List<ProfileMatch> profileMatches = (List<ProfileMatch>) profileMatchRepo.findAll();
        profileMatches = profileMatches.stream()
                .filter(prm -> Objects.equals(prm.getIdSecondUser(), idUser) || Objects.equals(prm.getIdFirstUser(), idUser))
                .collect(Collectors.toList());
        while (profileMatches.size() >= 1) {
            profileMatchRepo.delete(profileMatches.get(0));
            profileMatches.remove(0);
        }
    }
}
