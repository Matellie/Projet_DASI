/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.metier.objets;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jbondyfala
 */
@Entity
public class Consultation {
    
    /*ATTRIBUTS*/
    
    @Id
    @GeneratedValue
    private Long id;
    private String commentaire;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_heure;
    private Integer note;
   
    @JoinColumn(nullable = false)
    @ManyToOne
    private Client client;
    
    @JoinColumn(nullable = false)
    @ManyToOne
    private Medium medium;

    
    /*CONSTRUCTEURS*/
    
    public Consultation(Client client, Medium medium) {
        this.client = client;
        this.medium = medium;
        //par défaut
        this.commentaire=null;
        this.note=null;
        this.date_heure = new Date();
    }

    public Consultation() {
    }

    /*GETTERS*/
    public String getCommentaire() {
        return commentaire;
    }

    public Date getDate_heure() {
        return date_heure;
    }

    public Integer getNote() {
        return note;
    }

    public Client getClient() {
        return client;
    }

    public Medium getMedium() {
        return medium;
    }
    
    public Long getId() {
        return id;
    }

    /*SETTERS*/
    
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    /*METHODES*/

    @Override
    public String toString() {
        return "Consultation{" + "id=" + id + ", commentaire=" + commentaire + ", date_heure=" + date_heure + ", note=" + note + ", client=" + client + ", medium=" + medium + '}';
    }
    
    
}
