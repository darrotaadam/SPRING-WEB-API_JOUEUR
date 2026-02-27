package imt.fisa.player.controllers;


import imt.fisa.player.controllers.httpdto.PlayerLevelResponse;
import imt.fisa.player.controllers.httpdto.PlayerProfileResponse;
import imt.fisa.player.persistence.dto.ProfileEntity;
import imt.fisa.player.persistence.repositories.ProfileRepository;
import imt.fisa.player.services.AuthService;
import imt.fisa.player.services.PlayerService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private final PlayerService playerService;
    private final AuthService authService;

    public PlayerController(PlayerService playerService, AuthService authService) {
        this.playerService = playerService;
        this.authService = authService;
        System.out.println("[*] PlayerController");
    }

    //la récupération de toutes les informations du profi
    @GetMapping("/profile")
    public ResponseEntity<PlayerProfileResponse> getPlayerProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        System.out.println("[*] /profile ");
        String identifiant = authService.getUserFromToken(authorization);
        System.out.println("[*] identifiant:  " + identifiant);
        ProfileEntity player =  playerService.getProfile(identifiant);
        return ResponseEntity.ok(new PlayerProfileResponse(
                player.getIdentifiant(),
                player.getLevel(),
                player.getExperience(),
                player.getMonstres()
        ));
    }

    //la récupération du niveau du joueur
    @GetMapping("/level")
    public ResponseEntity<PlayerLevelResponse> getPlayerLevel(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        System.out.println("[*] /level");
        String identifiant = authService.getUserFromToken(authorization);
        ProfileEntity player = playerService.getProfile(identifiant);
        return ResponseEntity.ok(new PlayerLevelResponse(
                player.getIdentifiant(),
                player.getLevel()
        ));
    }



    //un gain d’expérience (quantité passée en paramètre) et retourner le nouveau statut
    //utilisateur
    @PostMapping("/xp-progress")
    public ResponseEntity<PlayerProfileResponse> gainExperience(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, int experienceGained) {
        System.out.println("[*] /xp-progress");
        String identifiant = authService.getUserFromToken(authorization);
        ProfileEntity player = playerService.getProfile(identifiant);
        player.setExperience(player.getExperience() + experienceGained);
    }
}
