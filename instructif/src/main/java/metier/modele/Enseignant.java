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
public class Enseignant extends Intervenant{
    
    private String typeEtablissement;

    public Enseignant() {
    }

    public Enseignant(String typeEtablissement, String nom, String prenom, String mail, String motDePasse, String numTel, Niveau niveauMin, Niveau niveauMax) {
        super(nom, prenom, mail, motDePasse, numTel, niveauMin, niveauMax);
        this.typeEtablissement = typeEtablissement;
    }

    public String getTypeEtablissement() {
        return typeEtablissement;
    }

    public void setTypeEtablissement(String typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }

    @Override
    public String toString() {
        return "Enseignant{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", motDePasse=" + motDePasse + ", numTel=" + numTel + ", niveauMin=" + niveauMin + ", niveauMax=" + niveauMax + ", typeEtablissement=" + typeEtablissement + '}';
    }
    
    
    
}
