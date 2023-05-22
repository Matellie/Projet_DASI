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
@DiscriminatorValue("C")
public class Cartomancien extends Medium{

    public Cartomancien(String denomination, String presentation, boolean masculin) {
        super(denomination, presentation, masculin);
    }

    public Cartomancien() {
    }

    @Override
    public String toString() {
        return "Cartomancien{" + super.toString() + '}';
    }
    
    
}
