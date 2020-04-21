package io.github.ungman.service;

import io.github.ungman.domain.Profile;
import io.github.ungman.domain.User;
import io.github.ungman.repo.ProfileRepo;
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
public class ProfileServiceTest {

    @InjectMocks
    ProfileService profileService;
    @Mock
    ProfileRepo profileRepo;

    @Test
    public void save() {
        Profile profile = profileFactory(1l, "123", "", "").get();
        Mockito.when(profileRepo.save(profile)).thenReturn(profile);
        Profile profile1 = profileService.save(profile);
        assertThat(profile1).isNotNull();
        assertThat(profile1.getName()).isEqualTo(profile.getName());
    }

    @Test
    public void getList() {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(profileFactory("1", "1", "").get());
        profiles.add(profileFactory("2", "1", "").get());
        profiles.add(profileFactory("3", "1", "").get());
        Mockito.when(profileRepo.findAll()).thenReturn(profiles);
        List<Profile> usersTestList = profileService.getList();
        assertThat(usersTestList.size()).isEqualTo(3);
        assertThat(profiles).isEqualTo(usersTestList);
        Mockito.verify(profileRepo, Mockito.times(1)).findAll();
    }

    @Test
    public void getOne() {
        Mockito.when(profileRepo.findById(1L)).thenReturn(profileFactory(1L, "1", "1", ""));
        Profile profile = profileService.getOne(1L);
        assertThat(profile).isNotNull();
        assertThat(profile.getIdUser()).isEqualTo(1L);
        Mockito.verify(profileRepo, Mockito.times(1)).findById(1L);
        Profile userNull = profileService.getOne(2L);
        assertThat(userNull).isNull();
        Mockito.verify(profileRepo, Mockito.times(1)).findById(2L);
    }

    @Test
    public void update() {
        Mockito.when(profileRepo.findById(1L)).thenReturn(profileFactory(1L, "1", "1", ""));
        Profile profile = profileService.getOne(1L);
        profile.setName("2");
        Mockito.when(profileRepo.save(profile)).thenReturn(profileFactory(1L, "2", "1", "").get());
        Profile user1 = profileService.update(profile);
        assertThat(user1).isNotNull();
        assertThat(user1.getIdUser()).isEqualTo(1L);
        assertThat(user1.getName()).isEqualTo("2");
        Mockito.verify(profileRepo, Mockito.times(1)).findById(1L);
        Mockito.verify(profileRepo, Mockito.times(1)).save(profile);
    }

    @Test
    public void delete() {
        Profile profile = new Profile();
        Mockito.doNothing().when(profileRepo).delete(profile);
        profileService.delete(profile);
        Mockito.verify(profileRepo, Mockito.times(1)).delete(profile);
    }

    @Test
    public void showCurrentProfile() {
        Mockito.when(profileRepo.findById(1L)).thenReturn(profileFactory(1L, "1", "1", "",2L));
        Mockito.when(profileRepo.findById(2L)).thenReturn(profileFactory(2L, "n", "m", "test",0L));
        Profile profile=profileService.showCurrentProfile(1L);
        assertThat(profile).isNotNull();
        assertThat(profile.getName()).isEqualTo("n");
    }

    @Test
    public void showNextProfile() {

        Optional<Profile> profile = profileFactory(1L, "n", "m", "n",3L);
        Mockito.when(profileRepo.findById(1L)).thenReturn(profile);
        Mockito.when(profileRepo.save(profile.get())).thenReturn(profile.get());

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profileFactory(2L,"1", "f", "").get());
        profiles.add(profileFactory(3L,"1", "f", "").get());
        profiles.add(profileFactory(4L,"1", "m", "").get());
        profiles.add(profileFactory(5L,"1", "f", "").get());
        Mockito.when(profileRepo.findAll()).thenReturn(profiles);
        Profile profileShow=profileService.showNextProfile(2L,false);
        Profile profileFirstShow=profileService.showNextProfile(5L,false);
        assertThat(profileShow.getIdUser()).isEqualTo(3L);
        assertThat(profileFirstShow.getIdUser()).isEqualTo(2L);

        Profile showProfile1=profileService.showNextProfile(1L,true);
        assertThat(showProfile1.getIdUser()).isEqualTo(5L);
        Profile showProfile2=profileService.showNextProfile(1L,true);
        assertThat(showProfile2.getIdUser()).isEqualTo(-1L);


    }

    @Test
    public void getNotShowedProfileAuthUser() {
        Optional<Profile> profile = profileFactory(1L, "n", "m", "n",0L);
        Mockito.when(profileRepo.findById(1L)).thenReturn(profile);
        Mockito.when(profileRepo.save(profile.get())).thenReturn(profile.get());

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profileFactory(3L,"1", "f", "").get());
        profiles.add(profileFactory(4L,"1", "m", "").get());
        profiles.add(profileFactory(5L,"1", "f", "").get());
        Mockito.when(profileRepo.findAll()).thenReturn(profiles);

        Profile showProfile1=profileService.getNotShowedProfileAuthUser(1L);
        Profile showProfile2=profileService.getNotShowedProfileAuthUser(1L);
        Profile showProfileEmpty=profileService.getNotShowedProfileAuthUser(1L);
        assertThat(showProfile1).isNotEqualTo(showProfile2);
        assertThat(showProfileEmpty.getIdUser()).isEqualTo(-1L);
    }

    @Test
    public void getNotShowedProfileNotAuthUser() {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(profileFactory(2L,"1", "f", "").get());
        profiles.add(profileFactory(3L,"1", "f", "").get());
        profiles.add(profileFactory(4L,"1", "m", "").get());
        profiles.add(profileFactory(5L,"1", "f", "").get());
        Mockito.when(profileRepo.findAll()).thenReturn(profiles);
        Profile profileShow=profileService.getNotShowedProfileNotAuthUser(2L);
        Profile profileFirstShow=profileService.getNotShowedProfileNotAuthUser(5L);
        assertThat(profileShow.getIdUser()).isEqualTo(3L);
        assertThat(profileFirstShow.getIdUser()).isEqualTo(2L);
    }

    @Test
    public void getFullProfile() {
        Optional<Profile> profile = profileFactory(1L, "n", "m", "test");
        Mockito.when(profileRepo.findById(1L)).thenReturn(profile);

        Profile profile1=profileService.getFullProfile(1L);
        assertThat(profile1).isNotNull();
        assertThat(profile1.getIdUser()).isEqualTo(profile.get().getIdUser());
    }

    @Test
    public void deleteById() {
        Optional<Profile> profile = profileFactory(1L, "n", "m", "test");
        Mockito.when(profileRepo.findById(1L)).thenReturn(profile);
        Mockito.doNothing().when(profileRepo).delete(profile.get());
        profileService.deleteById(1L);
        Mockito.verify(profileRepo,Mockito.times(1)).delete(profile.get());
    }

    @Test
    public void editProfile() {
        Profile profile = profileFactory(1l, "123", "", "").get();
        Mockito.when(profileRepo.save(profile)).thenReturn(profile);
        Profile profile1 = profileService.editProfile(profile);
        assertThat(profile1).isNotNull();
        assertThat(profile1.getName()).isEqualTo(profile.getName());
    }

    private Optional<Profile> profileFactory(String name, String gender, String description) {
        Profile profile = new Profile();
        profile.setName(name);
        profile.setGender(gender);
        profile.setDescription(description);
        return Optional.of(profile);
    }

    private Optional<Profile> profileFactory(Long id, String name, String gender, String description) {
        Profile profile = new Profile();
        profile.setIdUser(id);
        profile.setName(name);
        profile.setGender(gender);
        profile.setDescription(description);
        return Optional.of(profile);
    }
    private Optional<Profile> profileFactory(Long id, String name, String gender, String description,Long idShown) {
        Profile profile = new Profile();
        profile.setIdUser(id);
        profile.setName(name);
        profile.setGender(gender);
        profile.setDescription(description);
        profile.setIdShown(idShown);
        return Optional.of(profile);
    }
}