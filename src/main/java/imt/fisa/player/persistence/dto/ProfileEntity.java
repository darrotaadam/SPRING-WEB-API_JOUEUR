package imt.fisa.player.persistence.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "profile")
public class ProfileEntity {
    @MongoId
    private String identifiant;

    private int level;

    private int experience;

}
