package io.github.ungman.service;

import io.github.ungman.domain.Profile;
import io.github.ungman.repo.ProfileRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepo profileRepo;

    public Profile save(Profile profile) {
        if (profile != null) {
            profile = profileRepo.save(profile);
        }
        return profile;
    }

    public List<Profile> getList() {
        Iterable<Profile> profiles = profileRepo.findAll();
        List<Profile> collect = new ArrayList<>();
        Profile profile = new Profile();
        profile.setIdUser(-1L);
        collect.add(profile);
        if (profiles.iterator().hasNext()) {
            collect = (List<Profile>) profiles;
        }
        return collect;
    }

    public Profile getOne(Long idProfile) {
        return profileRepo.findById(idProfile).map(Profile::setEmptyShownId).orElse(null);
    }

    public Profile update(Profile profile) {
        return profileRepo.save(profile);
    }

    public void delete(Profile profile) {
        profileRepo.delete(profile);
    }

    public Profile showCurrentProfile(Long id) {
        Long idShown = getOne(id).getIdShown();
        Profile profile = getOne(idShown);
        return profile;
    }

    public Profile showNextProfile(Long id, boolean isAuth) {
        if (isAuth)
            return getNotShowedProfileAuthUser(id);
        return getNotShowedProfileNotAuthUser(id);
    }

    public Profile getNotShowedProfileAuthUser(Long id) {
        Profile profile = profileRepo.findById(id).get();
        String gender = profile.getGender();
        Long idShown = profile.getIdShown()==null?-1:profile.getIdShown();
        Profile profile1 = getList().stream()
                .filter(prf -> prf.getGender().compareTo(gender) != 0 && prf.getIdUser() > idShown)
                .findFirst().orElse(null);
        if (profile1 != null) {
            profile.setIdShown(profile1.getIdUser());
            save(profile);
        }else {
            profile1=new Profile();
            profile1.setIdUser(-1L);
        }
        return profile1;
    }

    public Profile getNotShowedProfileNotAuthUser(Long id) {
        List<Profile> listProfile = getList();
        Optional<Profile> optionalProfileNotShown = listProfile.stream()
                .filter(prf -> prf.getIdUser() > id)
                .findFirst();
        Profile profile = new Profile();
        profile.setIdUser(-1L);
        if (optionalProfileNotShown.isPresent())
            profile = optionalProfileNotShown.get();
        else if (listProfile.size() > 0)
            profile = listProfile.get(0);
        return profile;

    }

    public Profile getFullProfile(Long id) {
        return profileRepo.findById(id).orElse(null);
    }

    public void deleteById(Long idUser) {
        Profile profile=getOne(idUser);
        profileRepo.delete(profile);
    }

    public Profile editProfile(Profile profile) {
        return save(profile);
    }
}
