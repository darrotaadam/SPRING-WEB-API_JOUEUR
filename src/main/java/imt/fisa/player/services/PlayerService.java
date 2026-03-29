package imt.fisa.player.services;

import imt.fisa.player.exceptions.InternalServerErrorException;
import imt.fisa.player.persistence.dto.Monstres;
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

    public void ajoutMonstre(ProfileEntity player, String idMonstre){
        player.getMonstres().add(new Monstres(idMonstre));
        profileRepository.save(player);
    }

    public void supprimeMonstre(ProfileEntity player, String idMonstre){
        player.getMonstres().removeIf(monstre -> monstre.getId().equals(idMonstre));
        profileRepository.save(player);
    }




    public void gainExperience(ProfileEntity player, int experienceGained){

        if(player.getLevel()<1){
            throw new InternalServerErrorException("Le niveau du joueur ne peut pas être inférieur à 1");
        }
        int initialExperience = player.getExperience();
        int remainingExperienceToGain = experienceGained;
        int gainedLevels = 0;
        int xpNeededForLevelUp =  (int) (50 * Math.pow(1.1, player.getLevel()-1)) - initialExperience;

        // en cas de dépassement de l'xp necessaire ==> on levelip tant qu'on peut
        while (remainingExperienceToGain >= xpNeededForLevelUp && player.getLevel()+gainedLevels<50){
            initialExperience = 0;
            remainingExperienceToGain -= xpNeededForLevelUp;
            gainedLevels++;

            xpNeededForLevelUp =  (int) (50 *  Math.pow(1.1, player.getLevel() + gainedLevels -1) );
        }

        player.setLevel( player.getLevel() + gainedLevels);

        player.setExperience( initialExperience + remainingExperienceToGain );
        profileRepository.save(player);
    }








    public void gainExperience(ProfileEntity player, int experienceGained,boolean debugMode){

        if(player.getLevel()<1){
            throw new InternalServerErrorException("Le niveau du joueur ne peut pas être inférieur à 1");
        }
        int initialExperience = player.getExperience();
        int remainingExperienceToGain = experienceGained;
        int gainedLevels = 0;
        int xpNeededForLevelUp =  (int) (50 * Math.pow(1.1, player.getLevel()-1)) - initialExperience;

        System.out.println("Player" + player.getIdentifiant() + " is at level " + player.getLevel());
        System.out.println("Player " + player.getIdentifiant() + " has " + initialExperience);
        System.out.println("Player " + player.getIdentifiant() + " will gain " + experienceGained);
        System.out.println("Player " + player.getIdentifiant() + " needs " + xpNeededForLevelUp + " to level up");
        System.out.println("Player " + player.getIdentifiant() + " has " + remainingExperienceToGain + " experience left to gain");
        // en cas de dépassement de l'xp necessaire ==> on levelip tant qu'on peut
        while (remainingExperienceToGain >= xpNeededForLevelUp && player.getLevel()+gainedLevels<50){
            initialExperience = 0;
            System.out.println("Player " + player.getIdentifiant() + " is at level " + (player.getLevel() + gainedLevels) + " and needs " + xpNeededForLevelUp + " to level up");
            remainingExperienceToGain -= xpNeededForLevelUp;
            gainedLevels++;
            System.out.println("Player " + player.getIdentifiant() + " leveled up to " + (player.getLevel() + gainedLevels) + " and has " + remainingExperienceToGain + " experience left to gain");

            xpNeededForLevelUp =  (int) (50 *  Math.pow(1.1, player.getLevel() + gainedLevels -1) );
        }

        player.setLevel( player.getLevel() + gainedLevels);

        System.out.println("Player " + player.getIdentifiant() + " is now at level " + player.getLevel());
        System.out.println("Player " + player.getIdentifiant() + " has " + remainingExperienceToGain + " experience left");
        player.setExperience( initialExperience + remainingExperienceToGain );
        //profileRepository.save(player);
    }


}
