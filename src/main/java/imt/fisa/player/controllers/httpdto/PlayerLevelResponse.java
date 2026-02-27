package imt.fisa.player.controllers.httpdto;

public class PlayerLevelResponse {
    private String identifiant;
    private int level;

    public PlayerLevelResponse(String identifiant, int level) {
        this.identifiant = identifiant;
        this.level = level;
    }

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
}
