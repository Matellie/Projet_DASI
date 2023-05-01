/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.*;
import metier.modele.Intervenant;
import metier.modele.Intervention;

/**
 *
 * @author etarassov
 */
public class InterventionDao {
    
    public InterventionDao() {
    }
    
    public void create(Intervention intervention) {
        JpaUtil.obtenirContextePersistance().persist(intervention);
    }
    
    public Intervention findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Intervention.class, id);
    }
    
    public List<Intervention> findByIntervenant(Intervenant intervenant) {        
        String s = "select i from Intervention i where i.intervenant = :lIntervenant order by i.dateDemande desc";
        
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Intervention.class);
        query.setParameter("lIntervenant", intervenant);
        List<Intervention> interventions = query.getResultList();
        
        return interventions;
    }
    
    public Intervention update(Intervention intervention){
        return JpaUtil.obtenirContextePersistance().merge(intervention);
    }
}
