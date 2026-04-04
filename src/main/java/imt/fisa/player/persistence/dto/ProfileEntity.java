package imt.fisa.player.persistence.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "profile")
public class ProfileEntity {
    @MongoId
    private String identifiant;

    private int level;

    private int experience;

    private List<Monstres> monstres = new ArrayList<>();


    public ProfileEntity() {
    }

    public ProfileEntity(String identifiant, int level, int experience, List<Monstres> monstres) {
        this.identifiant = identifiant;
        this.level = level;
        this.experience = experience;
        this.monstres = monstres;
    }

    public ProfileEntity(String identifiant, int level, int experience) {
        this.identifiant = identifiant;
        this.level = level;
        this.experience = experience;
        this.monstres = new ArrayList<Monstres>();
    }

    public ProfileEntity(String identifiant, int level) {
        this.identifiant = identifiant;
        this.level = level;
        this.experience = 0;
        this.monstres = new ArrayList<Monstres>();
    }

    public ProfileEntity(String identifiant) {
        this.identifiant = identifiant;
        this.level = 1;
        this.experience = 0;
        this.monstres = new ArrayList<Monstres>();
    }

    //------------------------------------

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public List<Monstres> getMonstres() {
        return monstres;
    }

    public void setMonstres(List<Monstres> monstres) {
        this.monstres = monstres;
    }
}
