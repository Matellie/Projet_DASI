/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.TypedQuery;
import metier.modele.Eleve;

/**
 *
 * @author etarassov
 */
public class EleveDao {

    public EleveDao() {
    }
    
    public void create(Eleve eleve) {
        JpaUtil.obtenirContextePersistance().persist(eleve);
    }
    
    public Eleve findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Eleve.class, id);
    }
    
    public Eleve authenticate(String login, String motDePasse) {
        String s = "select e from Eleve e where e.mail = :leLogin";
        
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Eleve.class);
        query.setParameter("leLogin", login);
        Eleve elv = (Eleve)query.getSingleResult();
        
        Eleve eleveReturn = null;
        if(elv.getMotDePasse().equals(motDePasse)){
            eleveReturn = elv;
        }
        
        return eleveReturn;
    }
}
