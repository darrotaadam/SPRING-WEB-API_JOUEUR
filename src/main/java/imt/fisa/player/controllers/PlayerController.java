package imt.fisa.player.controllers;


import imt.fisa.player.controllers.httpdto.PlayerLevelResponse;
import imt.fisa.player.controllers.httpdto.PlayerProfileResponse;
import imt.fisa.player.exceptions.UnauthorizedException;
import imt.fisa.player.persistence.dto.Monstres;
import imt.fisa.player.persistence.dto.ProfileEntity;
import imt.fisa.player.controllers.httpdto.PlayerMonstresResponse;
import imt.fisa.player.persistence.repositories.ProfileRepository;
import imt.fisa.player.services.AuthService;
import imt.fisa.player.services.PlayerService;
import io.micrometer.common.util.internal.logging.InternalLogger;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${INTERNAL_SECRET}")
    private String internalSecret;


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

    @GetMapping("/monstres")
    public ResponseEntity<PlayerMonstresResponse> getPlayerMonstres(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        System.out.println("[*] /monstres");
        String identifiant = authService.getUserFromToken(authorization);
        ProfileEntity player = playerService.getProfile(identifiant);
        return ResponseEntity.ok(new PlayerMonstresResponse(
                player.getIdentifiant(),
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
        playerService.gainExperience(player, experienceGained);

        return ResponseEntity.ok(new PlayerProfileResponse(
                player.getIdentifiant(),
                player.getLevel(),
                player.getExperience(),
                player.getMonstres()
        ));
    }


    @PostMapping("/ajout-monstre")
    public ResponseEntity<PlayerMonstresResponse> ajoutMonstre(
            //@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestHeader("X-INTERNAL-API-KEY") String InternalApiKey,
            String idMonstre,
            String idJoueur
    ) {
        System.out.println("[*] /ajout-monstre");
        if( !InternalApiKey.equals(internalSecret)){
            throw new UnauthorizedException("Clé d'API interne invalide");
        }

        ProfileEntity player = playerService.getProfile(idJoueur);
        playerService.ajoutMonstre(player, idMonstre);
        return ResponseEntity.ok(new PlayerMonstresResponse(
                player.getIdentifiant(),
                player.getMonstres()
        ));
    }

    @PostMapping("/supprime-monstre")
    public ResponseEntity<PlayerMonstresResponse> supprimeMonstre(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            String idMonstre
    ) {
        System.out.println("[*] /supprime-monstre");
        String identifiant = authService.getUserFromToken(authorization);
        ProfileEntity player = playerService.getProfile(identifiant);
        playerService.supprimeMonstre(player, idMonstre);
        return ResponseEntity.ok(new PlayerMonstresResponse(
                player.getIdentifiant(),
                player.getMonstres()
        ));
    }




}
