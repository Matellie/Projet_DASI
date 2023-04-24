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
public class Etudiant extends Intervenant {
    private String universite;
    private String specialite;

    public Etudiant() {
    }

    public Etudiant(String universite, String specialite, String nom, String prenom, String mail, String motDePasse, String numTel, Niveau niveauMin, Niveau niveauMax) {
        super(nom, prenom, mail, motDePasse, numTel, niveauMin, niveauMax);
        this.universite = universite;
        this.specialite = specialite;
    }

    public String getUniversite() {
        return universite;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return "Etudiant{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", motDePasse=" + motDePasse + ", numTel=" + numTel + ", niveauMin=" + niveauMin + ", niveauMax=" + niveauMax + ", universite=" + universite + ", specialite=" + specialite + '}';
    }

    
    
}
