package imt.fisa.player.services;

import imt.fisa.player.controllers.httpdto.MonsterEntity;
import imt.fisa.player.exceptions.InternalServerErrorException;
import imt.fisa.player.exceptions.UnauthorizedException;
import imt.fisa.player.persistence.dto.Monstres;
import imt.fisa.player.persistence.dto.ProfileEntity;
import imt.fisa.player.persistence.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PlayerService {


    private final RestTemplate restTemplate;

    @Value("${auth.internal.secret}")
    private String internalSecret;

    @Value("${monstres.hostname}") String monstresHostname;
    @Value("${monstres.port}") String monstresPort;

    private ProfileRepository profileRepository;

    @Autowired
    public PlayerService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        this.restTemplate = new RestTemplate();
    }



    public ProfileEntity getProfile(String identifiant){
        return profileRepository.findByIdentifiant(identifiant)
                .orElseGet( // apparement il faut utiliser orElseGet au lieu de orElse car ce dernier exécute son contenu dans tous les cas.
                        () ->profileRepository.save(new ProfileEntity(identifiant))
                );
    }

    public void ajoutMonstre(ProfileEntity player, String idMonstre){
        System.out.println("ajoutMonstre: " + idMonstre + " to player: " + player.getIdentifiant());

        player.getMonstres().add(new Monstres(idMonstre));
        profileRepository.save(player);

        ProfileEntity dbPlayer = profileRepository.findByIdentifiant(player.getIdentifiant())
                .orElseThrow(() -> new InternalServerErrorException("Player not found after saving. This should not happen."));
        for(Monstres m : dbPlayer.getMonstres()){
            System.out.println("Player " + dbPlayer.getIdentifiant() + " has monster: " + m.getId());
        }

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



    public boolean playerHasThisMonster(ProfileEntity player, String idMonstre){
        System.out.println("PlayerService::playerHasThisMonster(" + player.getIdentifiant() + "," + idMonstre +  ")");
        return player.getMonstres().stream().anyMatch(monstre -> monstre.getId().equals(idMonstre));
    }


    public MonsterEntity getMonstreDetail(String idMonstre){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-INTERNAL-API-KEY", internalSecret);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try{
            ResponseEntity<MonsterEntity> response =
                    restTemplate.exchange(
                            "http://" + monstresHostname + ":" + monstresPort + "/get-monstre/{id}",
                            HttpMethod.GET,
                            requestEntity,
                            MonsterEntity.class,
                            idMonstre
                    );
            System.out.println(response.toString());

            return response.getBody();

        } catch (HttpClientErrorException.Unauthorized e) {
            throw new UnauthorizedException("Clé interne invalide.");
        } catch (NullPointerException e) {
            throw new InternalServerErrorException("Échec de la création du monstre.");
        }
    }



}
