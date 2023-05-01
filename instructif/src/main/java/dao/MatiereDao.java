/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.*;
import metier.modele.Matiere;

/**
 *
 * @author etarassov
 */
public class MatiereDao {
    
    public MatiereDao() {
    }
    
    public void create(Matiere matiere) {
        JpaUtil.obtenirContextePersistance().persist(matiere);
    }
    
    public Matiere findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Matiere.class, id);
    }
    
    public Matiere findByName(String nom) {
        String s = "select m from Matiere m where m.nom = :leNom";
        
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Matiere.class);
        query.setParameter("leNom", nom);
        Matiere mat = (Matiere)query.getSingleResult();
        
        return mat;
    }
}
