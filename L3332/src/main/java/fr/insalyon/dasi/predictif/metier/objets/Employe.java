/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.metier.objets;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author jbondyfala
 */
@Entity
public class Employe {
    
    /*ATTRIBUTS*/
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String nom;
    private String prenom;
    private boolean masculin;
    
    private String telPro;
    
    private boolean online;
    
    @Column(nullable = false, unique = true)
    private String mail;
    
    private String motDePasse;
    
    @OneToMany
    private List<Consultation> historique;
    
    private Integer nbConsult;
 
    /*CONSTRUCTEURS*/
    
    public Employe() {
    }

    public Employe(String nom, String prenom, boolean masculin, String telPro, String mail) {
        this.nom = nom;
        this.prenom = prenom;
        this.masculin = masculin;
        this.telPro = telPro;
        this.mail = mail;
        
        //Par defaut;
        online = false;
        nbConsult = 0;
    }

    /*SETTERS*/

    public void setNbConsult(Integer nbConsult) {
        this.nbConsult = nbConsult;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    public void setOnline(boolean online) {
        this.online = online;
    }

    /*GETTERS*/
    
    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public List<Consultation> getHistorique() {
        return historique;
    }

    public int getNbConsult(){
        return nbConsult;
    }

    public String getTelPro() {
        return telPro;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    /*METHODES*/
    
    public void addCons(Consultation cons){
        historique.add(cons);
        nbConsult++;
    }
    
    public Consultation obtenirConsultCurrent() {
    	for (int i = historique.size() - 1; i >= 0; i--) {
    		Consultation cons = historique.get(i);
    		if (cons.getCommentaire() == null) {
    			return cons;
    		}
    	}
    	return null;
    }

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", masculin=" + masculin + ", telPro=" + telPro + ", online=" + online + ", mail=" + mail + ", nbConsult=" + nbConsult + '}';
    }
    
    
    
}
