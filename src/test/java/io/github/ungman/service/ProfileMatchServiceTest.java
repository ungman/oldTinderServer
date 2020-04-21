package io.github.ungman.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ungman.domain.ProfileMatch;
import io.github.ungman.repo.ProfileMatchRepo;
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
public class ProfileMatchServiceTest {

    @InjectMocks
    ProfileMatchService profileMatchService;
    @Mock
    ProfileMatchRepo profileMatchRepo;

    @Test
    public void getList() {
        List<ProfileMatch> profileMatches=new ArrayList<>();
        profileMatches.add(profileMatchFactory(1L,1L,2L).get());
        profileMatches.add(profileMatchFactory(2L,1L,3L).get());
        Mockito.when(profileMatchRepo.findAll()).thenReturn(profileMatches);
        List<ProfileMatch> profileMatchList=profileMatchService.getList(1L);
        assertThat(profileMatchList).isNotNull();
        assertThat(profileMatchList.size()).isEqualTo(0);

        profileMatches.add(profileMatchFactory(3L,2L,1L).get());
        profileMatches.add(profileMatchFactory(4L,3L,2L).get());
        profileMatchList=profileMatchService.getList(1L);
        assertThat(profileMatchList).isNotNull();
        assertThat(profileMatchList.size()).isNotEqualTo(0);
        boolean right=profileMatchList.stream().allMatch(profileMatch -> profileMatch.getIdFirstUser().equals(1L));
        assertThat(right).isTrue();
    }

    @Test
    public void getOne() {
        Mockito.when(profileMatchRepo.findById(1L)).thenReturn(profileMatchFactory(1L,1L,2L));
        ProfileMatch profileMatch = profileMatchService.getOne(1L);
        assertThat(profileMatch).isNotNull();
        assertThat(profileMatch.getId()).isEqualTo(1L);

        ProfileMatch profileMatchNull = profileMatchService.getOne(2L);
        assertThat(profileMatchNull).isNull();
    }

    @Test
    public void create() {
        ProfileMatch profileMatch=profileMatchFactory(1L,1L,2L).get();
        Mockito.when(profileMatchRepo.save(profileMatch)).thenReturn(profileMatch);
        ProfileMatch profileMatch1 = profileMatchService.create(profileMatchFactory(1L, 1L, 2L).get());
        assertThat(profileMatch1).isNotNull();
        assertThat(profileMatch.getId()).isEqualTo(profileMatch1.getId());
    }

    @Test
    public void update() {
        ProfileMatch profileMatch=profileMatchFactory(1L,1L,2L).get();
        Mockito.when(profileMatchRepo.save(profileMatch)).thenReturn(profileMatch);
        ProfileMatch updateMatch1 = profileMatchService.update(profileMatch);
        assertThat(updateMatch1).isNotNull();
        assertThat(updateMatch1).isEqualTo(profileMatch);
    }

    @Test
    public void delete() {
        ProfileMatch profileMatch=profileMatchFactory(1L,1L,2L).get();
        Mockito.doNothing().when(profileMatchRepo).delete(profileMatch);
        profileMatchService.delete(profileMatch);
        Mockito.verify(profileMatchRepo,Mockito.times(1)).delete(profileMatch);
    }

    @Test
    public void isMatchCurrent() {
        List<ProfileMatch> profileMatches=new ArrayList<>();
        profileMatches.add(profileMatchFactory(1L,1L,2L).get());
        profileMatches.add(profileMatchFactory(2L,1L,3L).get());
        profileMatches.add(profileMatchFactory(3L,2L,1L).get());
        profileMatches.add(profileMatchFactory(4L,3L,2L).get());
        Mockito.when(profileMatchRepo.findAll()).thenReturn(profileMatches);
        Boolean matchCurrent = profileMatchService.isMatchCurrent(1L, 2L);
        assertThat(matchCurrent).isTrue();

        Boolean matchCurrent1 = profileMatchService.isMatchCurrent(1L, 3L);
        assertThat(matchCurrent1).isFalse();

    }

    @Test
    public void getMatches() {
        List<ProfileMatch> profileMatches=new ArrayList<>();
        profileMatches.add(profileMatchFactory(1L,1L,2L).get());
        profileMatches.add(profileMatchFactory(2L,1L,3L).get());
        profileMatches.add(profileMatchFactory(3L,2L,1L).get());
        profileMatches.add(profileMatchFactory(4L,3L,1L).get());
        Mockito.when(profileMatchRepo.findAll()).thenReturn(profileMatches);
        List<ProfileMatch> profileMatches1=profileMatchService.getMatches(1L);
        assertThat(profileMatches1.size()).isNotEqualTo(0);

        List<ProfileMatch> profileMatches2=profileMatchService.getMatches(3L);
        assertThat(profileMatches2.size()).isNotEqualTo(0);
    }

    @Test
    public void createMatch() {
        ProfileMatch profileMatch=profileMatchFactory(1L,2L).get();
        Mockito.when(profileMatchRepo.save(profileMatch)).thenReturn(profileMatch);
        ProfileMatch match = profileMatchService.createMatch(1L, 2L);
        assertThat(match).isNotNull();
        assertThat(match).isEqualTo(profileMatch);
    }

    @Test
    public void deleteMatches() {
        List<ProfileMatch> profileMatches=new ArrayList<>();
        profileMatches.add(profileMatchFactory(1L,1L,2L).get());
        profileMatches.add(profileMatchFactory(2L,1L,3L).get());
        profileMatches.add(profileMatchFactory(3L,2L,1L).get());
        profileMatches.add(profileMatchFactory(4L,3L,1L).get());
        profileMatches.add(profileMatchFactory(5L,3L,2L).get());
        profileMatches.add(profileMatchFactory(5L,4L,6L).get());
        Mockito.when(profileMatchRepo.findAll()).thenReturn(profileMatches);
        Mockito.doAnswer(invocationOnMock -> {
            profileMatches.remove(invocationOnMock.getArgument(0));
            return null;
        }).when(profileMatchRepo).delete(Mockito.any(ProfileMatch.class));
        profileMatchService.deleteMatches(1L);
        Mockito.verify(profileMatchRepo,Mockito.times(4)).delete(Mockito.any(ProfileMatch.class));
    }

    private Optional<ProfileMatch> profileMatchFactory(Long id, Long sId, Long gId){
        ProfileMatch profileMatch=new ProfileMatch();
        profileMatch.setId(id);
        profileMatch.setIdFirstUser(sId);
        profileMatch.setIdSecondUser(gId);
        return Optional.of(profileMatch);
    }

    private Optional<ProfileMatch> profileMatchFactory(Long sId, Long gId){
        ProfileMatch profileMatch=new ProfileMatch();
        profileMatch.setIdFirstUser(sId);
        profileMatch.setIdSecondUser(gId);
        return Optional.of(profileMatch);
    }
}