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
@DiscriminatorValue("S")
public class Spirite extends Medium{
    private String support;

    public Spirite() {
    }

    public Spirite(String support, String denomination, String presentation, boolean masculin) {
        super(denomination, presentation, masculin);
        this.support = support;
    }

    @Override
    public String toString() {
        return "Spirite{" + super.toString() + ", support=" + support + '}';
    }
    
    
}
