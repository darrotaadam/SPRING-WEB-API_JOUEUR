package imt.fisa.player.persistence.dto;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "monstres")
public class Monstres {
    @MongoId
    private String id;

    public Monstres(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
