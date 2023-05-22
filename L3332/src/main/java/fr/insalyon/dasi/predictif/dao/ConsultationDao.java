/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.dao;

import fr.insalyon.dasi.predictif.metier.objets.Consultation;

/**
 *
 * @author jbondyfala
 */
public class ConsultationDao {
    public void create(Consultation e) {
        JpaUtil.obtenirContextePersistance().persist(e);
    }
    
    public Consultation update(Consultation e) {
        return JpaUtil.obtenirContextePersistance().merge(e);
    }
    
    public Consultation findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Consultation.class, id);
    }
}
