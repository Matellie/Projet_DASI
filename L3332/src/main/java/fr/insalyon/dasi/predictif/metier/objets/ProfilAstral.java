/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.metier.objets;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author jbondyfala
 */


@Embeddable
public class ProfilAstral {
    
    /*ATTRIBUTS*/
    
    private String zodiaque;
    private String chinois;
    private String couleur;
    private String animal;

    /*CONSTRUCTEURS*/
    
    public ProfilAstral(String zodiaque, String chinois, String couleur, String animal) {
        this.zodiaque = zodiaque;
        this.chinois = chinois;
        this.couleur = couleur;
        this.animal = animal;
    }

    public ProfilAstral() {
    }

    /*GETTERS*/
    
    public String getZodiaque() {
        return zodiaque;
    }

    public String getChinois() {
        return chinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getAnimal() {
        return animal;
    }
    
    
}
