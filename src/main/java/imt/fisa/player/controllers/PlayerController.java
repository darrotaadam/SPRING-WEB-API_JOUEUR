package imt.fisa.player.controllers;


import imt.fisa.player.controllers.httpdto.PlayerProfileResponse;
import imt.fisa.player.persistence.repositories.ProfileRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private ProfileRepository profileRepository;

    public PlayerController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ResponseEntity<PlayerProfileResponse> getPlayerProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

    }



}
