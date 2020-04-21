package io.github.ungman.repo;

import io.github.ungman.domain.ProfileMatch;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileMatchRepo extends CrudRepository<ProfileMatch, Long> {

    @Modifying
    @Query("DELETE FROM ProfileMatch prm WHERE prm.idFirstUser=:#{#id1} OR prm.idSecondUser=:#{#id1} ")
    void deleteProfileMatches(Long id1);

    void deleteByIdFirstUserOrIdSecondUser(Long id1, Long id2);
}
