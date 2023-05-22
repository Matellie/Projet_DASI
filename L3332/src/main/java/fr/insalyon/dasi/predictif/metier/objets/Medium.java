/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.metier.objets;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author jbondyfala
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="medium_type")
public class Medium {
    
    /*ATTRIBUTS*/
    
    @Id
    @GeneratedValue
    protected Long id;
    
    protected String denomination;
    protected String presentation;
    protected Boolean masculin;
    protected Integer popularite;

    /*CONSTRUCTEURS*/
    
    public Medium(String denomination, String presentation, boolean masculin) {
        this.denomination = denomination;
        this.presentation = presentation;
        this.masculin = masculin;
        
        //par defaut:
        this.popularite=0;
    }

    public Medium() {
    }

    /*GETTERS*/
    
    public String getDenomination() {
        return denomination;
    }

    public String getPresentation() {
        return presentation;
    }

    public boolean isMasculin() {
        return masculin;
    }

    public Integer getPopularite() {
        return popularite;
    }    
     
    public Long getId() {
        return id;
    }
    
    /*METHODES*/
    
    public void incrPopularite(){
        popularite++;
    }

    @Override
    public String toString() {
        return "id=" + id + ", denomination=" + denomination + ", presentation=" + presentation + ", masculin=" + masculin + ", popularite=" + popularite;
    }

}
