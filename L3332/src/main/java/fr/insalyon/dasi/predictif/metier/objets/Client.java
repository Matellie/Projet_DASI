/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.metier.objets;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jbondyfala
 */
@Entity
public class Client {
    
    /*ATTRIBUTS*/
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String nom;
    private String prenom;
    
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    
    private boolean masculin;
    
    private String tel;
    private String adressePostale;
    
    @Column(nullable = false, unique = true)
    private String mail;
    
    private String motDePasse;
    
    @Embedded
    private ProfilAstral profilAstral;
    
    @OneToMany(mappedBy="client")
    private List<Consultation> historique;
    
    /*CONSTRUCTEURS*/

    public Client() {
    }

    public Client(String nom, String prenom, Date dateNaissance, boolean masculin, String tel, String adressePostale, String mail) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.masculin = masculin;
        this.tel = tel;
        this.adressePostale = adressePostale;
        this.mail = mail;
    }

    /*SETTERS*/
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setProfilAstral(ProfilAstral profilAstral) {
        this.profilAstral = profilAstral;
    }

    /*GETTERS*/
    
    public String getMail() {
        return mail;
    }

    public Long getId() {
        return id;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public List<Consultation> getHistorique() {
        return historique;
    }

    public boolean isMasculin() {
        return masculin;
    }

    public String getNom() {
        return nom;
    }

    public String getTel() {
        return tel;
    }
    
    public ProfilAstral getProfilAstral() {
        return profilAstral;
    }

    /*METHODES*/
    
    public void addCons(Consultation c) {
        historique.add(c);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", masculin=" + masculin + ", tel=" + tel + ", adressePostale=" + adressePostale + ", mail=" + mail + '}';
    }

    
    
}
