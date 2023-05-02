/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
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
    
    public List<Matiere> getAllMatieres() {
        String s = "select m from Matiere m";
        
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Matiere.class);
        List<Matiere> mat = query.getResultList();
        
        return mat;
    }
    
    public Matiere findByName(String nom) {
        String s = "select m from Matiere m where m.nom = :leNom";
        
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Matiere.class);
        query.setParameter("leNom", nom);
        Matiere mat = (Matiere)query.getSingleResult();
        
        return mat;
    }
}
