/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.*;

/**
 *
 * @author etarassov
 */
@Entity
public class Autre extends Intervenant{
    private String activite;

    public Autre() {
    }

    public Autre(String activite, String nom, String prenom, String mail, String motDePasse, String numTel, Niveau niveauMin, Niveau niveauMax) {
        super(nom, prenom, mail, motDePasse, numTel, niveauMin, niveauMax);
        this.activite = activite;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    @Override
    public String toString() {
        return "Autre{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", motDePasse=" + motDePasse + ", numTel=" + numTel + ", niveauMin=" + niveauMin + ", niveauMax=" + niveauMax + ", activite=" + activite + '}';
    }
    
    
     
    
}
