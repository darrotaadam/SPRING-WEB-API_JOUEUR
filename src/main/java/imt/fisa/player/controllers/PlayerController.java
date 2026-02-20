package imt.fisa.player.controllers;


import imt.fisa.player.controllers.httpdto.PlayerProfileResponse;
import imt.fisa.player.persistence.dto.ProfileEntity;
import imt.fisa.player.persistence.repositories.ProfileRepository;
import imt.fisa.player.services.AuthService;
import imt.fisa.player.services.PlayerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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



}
