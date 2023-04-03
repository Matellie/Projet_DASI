/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author etarassov
 */
@Entity
public class Eleve {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    @Column(unique=true)
    private String mail;
    private String motDePasse;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeNaissance;
    private Niveau niveau;
    private Etablissement etablissement;

    public Eleve() {
    }

    public Eleve(String nom, String prenom, String mail, String motDePasse, Date dateDeNaissance, Niveau niveau) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.dateDeNaissance = dateDeNaissance;
        this.niveau = niveau;
        //il ne faut pas oublier de set etablissement.
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

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Etablissement getEtablissement() {
        return etablissement;
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

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    @Override
    public String toString() {
        return "Eleve{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", motDePasse=" + motDePasse + ", dateDeNaissance=" + dateDeNaissance + ", niveau=" + niveau + ", etablissement=" + etablissement + '}';
    }

   
    
    
}
