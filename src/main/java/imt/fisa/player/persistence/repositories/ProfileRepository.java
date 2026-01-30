package imt.fisa.player.persistence.repositories;

import imt.fisa.player.persistence.dto.ProfileEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository {
    Optional<ProfileEntity> findByIdentifiant(String identifiant);
    Optional<ProfileEntity> findByIdentifiantAndPassword(String identifiant, String password);
    Optional<ProfileEntity> findByToken(String token);
}
