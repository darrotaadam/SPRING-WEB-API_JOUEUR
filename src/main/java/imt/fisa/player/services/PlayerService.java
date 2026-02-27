package imt.fisa.player.services;

import imt.fisa.player.persistence.dto.ProfileEntity;
import imt.fisa.player.persistence.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileEntity getProfile(String identifiant){
        return profileRepository.findByIdentifiant(identifiant)
                .orElse(
                        profileRepository.save(new ProfileEntity(identifiant))
                );
    }



    public int getMaxExperienceForLevel(int level){
        return (int) (50 * ( (double) level * 1.1)) ;
    }



}
