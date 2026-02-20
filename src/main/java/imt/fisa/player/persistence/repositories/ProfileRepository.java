package imt.fisa.player.persistence.repositories;

import imt.fisa.player.persistence.dto.ProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByIdentifiant(String identifiant);
}
