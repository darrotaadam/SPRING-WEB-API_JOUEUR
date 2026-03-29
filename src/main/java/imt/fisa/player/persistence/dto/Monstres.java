package imt.fisa.player.persistence.dto;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


// Représente un monstre (créé|instancié) uniquement avec son id.
// La vraie collection des monstres est située dans la mongo de l'api monstres (avec tous les monstres)
// Ici on ne stocke donc qu'un id qui référence un monstre de la collection de l'api monstres
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
