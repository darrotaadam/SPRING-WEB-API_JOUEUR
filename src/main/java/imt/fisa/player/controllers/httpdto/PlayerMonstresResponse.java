package imt.fisa.player.controllers.httpdto;

import imt.fisa.player.persistence.dto.Monstres;

import java.util.ArrayList;
import java.util.List;

public class PlayerMonstresResponse {
    private String identifiant;
    private List<Monstres> monstres;

    public PlayerMonstresResponse(String identifiant, List<Monstres> monstres) {
        this.identifiant = identifiant;
        this.monstres = new ArrayList<Monstres>(monstres);
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public List<Monstres> getMonstres() {
        return monstres;
    }

    public void setMonstres(List<Monstres> monstres) {
        this.monstres = new ArrayList<Monstres>(monstres);;
    }
}
