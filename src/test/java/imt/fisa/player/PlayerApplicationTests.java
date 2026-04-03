package imt.fisa.player;

import imt.fisa.player.persistence.dto.ProfileEntity;
import imt.fisa.player.persistence.repositories.ProfileRepository;
import imt.fisa.player.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


//import org.junit.jupiter.*;
@SpringBootTest
class PlayerApplicationTests {


    @Autowired
    private ProfileRepository profileRepository;

    @Autowired PlayerService playerService;


    @Test
    public void  testLevelUp(){
        ProfileEntity playerTest = new ProfileEntity("id-test");

        assertThat(playerTest.getLevel()).isEqualTo(1).withFailMessage("Le niveau initial doit être 1");

        assertThat(playerTest.getExperience()).isEqualTo(0).withFailMessage("L'expérience initiale doit être 0");


        playerService.gainExperience(playerTest, 50, true);
        assertThat(playerTest.getExperience()).isEqualTo(0).withFailMessage("L'expérience après gain doit être 0");
        assertThat(playerTest.getLevel()).isEqualTo(2).withFailMessage("Le niveau après gain doit être 2");
        System.out.println("-------------------");
        playerService.gainExperience(playerTest, 50, true);
        assertThat(playerTest.getExperience()).isEqualTo(50).withFailMessage("L'expérience après gain doit être 50");
        assertThat(playerTest.getLevel()).isEqualTo(2).withFailMessage("Le niveau après gain doit encore être 2 car il faut 55 d'expérience pour passer au niveau 3");
        System.out.println("-------------------");
        playerService.gainExperience(playerTest, 50, true);
        assertThat(playerTest.getExperience()).isEqualTo(45).withFailMessage("L'expérience après gain doit être 45");
        assertThat(playerTest.getLevel()).isEqualTo(3).withFailMessage("Le niveau après gain doit encore être 3 car il manquait 5 points d'expérience d'expérience pour passer au niveau 3");
        System.out.println("-------------------");
        playerService.gainExperience(playerTest, 5,true);
        assertThat(playerTest.getExperience()).isEqualTo(50).withFailMessage("L'expérience après gain de 5 doit passer de 45 a 50");
        assertThat(playerTest.getLevel()).isEqualTo(3).withFailMessage("Le niveau après gain de 5 doit encore être 3 car il manque encore 10 points d'expérience d'expérience pour passer au niveau 4");
        System.out.println("-------------------");
        playerService.gainExperience(playerTest,77 ,true);
        assertThat(playerTest.getExperience()).isEqualTo(1).withFailMessage("L'expérience après gain de 77 doit passer de 50 a 1 tout en faisant 2 levelup");
        assertThat(playerTest.getLevel()).isEqualTo(5).withFailMessage("Le niveau après gain de 77 doit passer à 5 avec u gain de 2 niveaux");


        System.out.println("---------passage niveau 49->50 ----------");
        playerTest.setLevel(49);
        playerTest.setExperience(0);
        playerService.gainExperience(playerTest, 4850,true);
        assertThat(playerTest.getExperience()).isEqualTo(0).withFailMessage("L'expérience après gain de 4850 au niveau 49  doit être 0");
        assertThat(playerTest.getLevel()).isEqualTo(50).withFailMessage("Le niveau après gain de 4850 au niveau 49 doit encore être 50 points tout pile ");

        System.out.println("---------ajout 10000 points niveau 50 ----------");
        playerService.gainExperience(playerTest, 10000,true);
        assertThat(playerTest.getExperience()).isEqualTo(10000).withFailMessage("Une fois au niveau 50 l'expérience peut augmenter mais le niveau restera 50");
        assertThat(playerTest.getLevel()).isEqualTo(50).withFailMessage("Le niveau ne peut pas dépasser 50");




    }


}
