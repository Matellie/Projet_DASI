/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.metier.objets;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author jbondyfala
 */
@Entity
@DiscriminatorValue("A")
public class Astrologue extends Medium{
    private String formation;
    private Integer promotion;

    public Astrologue(String formation, Integer promotion, String denomination, String presentation, boolean masculin) {
        super(denomination, presentation, masculin);
        this.formation = formation;
        this.promotion = promotion;
    }

    public Astrologue() {
    }

    @Override
    public String toString() {
        return "Astrologue{" + super.toString() +", formation=" + formation + ", promotion=" + promotion +'}';
    }
    
    
}
