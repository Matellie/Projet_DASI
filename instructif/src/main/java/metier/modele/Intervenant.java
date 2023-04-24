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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Intervenant {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
    protected String nom;
    protected String prenom;
    @Column(unique=true)
    protected String mail;
    protected String motDePasse;
    protected String numTel;
    protected Niveau niveauMin;
    protected Niveau niveauMax;
    protected int nbInterventionsTotal;
    protected boolean available;

    public Intervenant() {
        this.nbInterventionsTotal = 0;
        this.available = true;
    }

    public Intervenant(String nom, String prenom, String mail, String motDePasse, String numTel, Niveau niveauMin, Niveau niveauMax) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.numTel = numTel;
        
        if(niveauMin.ordinal()>niveauMax.ordinal()){
            this.niveauMin = niveauMax;
            this.niveauMax = niveauMin;
        }else{
            this.niveauMin = niveauMin;
            this.niveauMax = niveauMax;
        }
        
        this.nbInterventionsTotal = 0;
        this.available = true;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getNumTel() {
        return numTel;
    }

    public Niveau getNiveauMin() {
        return niveauMin;
    }

    public Niveau getNiveauMax() {
        return niveauMax;
    }

    public int getNbInterventionsTotal() {
        return nbInterventionsTotal;
    }

    public boolean isAvailable() {
        return available;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public void setNiveauMin(Niveau niveauMin) {
        this.niveauMin = niveauMin;
    }

    public void setNiveauMax(Niveau niveauMax) {
        this.niveauMax = niveauMax;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public void incrementNbIntervention(){
        ++nbInterventionsTotal;
    }

    @Override
    public String toString() {
        return "Intervenant{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", motDePasse=" + motDePasse + ", numTel=" + numTel + ", niveauMin=" + niveauMin + ", niveauMax=" + niveauMax + '}';
    }
    
}
