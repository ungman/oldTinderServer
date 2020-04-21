package io.github.ungman.repo;

import io.github.ungman.domain.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends CrudRepository<Profile, Long> {
}
