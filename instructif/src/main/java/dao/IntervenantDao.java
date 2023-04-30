/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.*;
import metier.modele.Intervenant;
import metier.modele.Niveau;

/**
 *
 * @author etarassov
 */
public class IntervenantDao {

    public IntervenantDao() {
    }
    
    public void create(Intervenant intervenant) {
        JpaUtil.obtenirContextePersistance().persist(intervenant);
    }
    
    public Intervenant findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Intervenant.class, id);
    }
    
    public Long authenticate(String login, String motDePasse) {
        String s = "select i from Intervenant i where i.mail = :leLogin";
        
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Intervenant.class);
        query.setParameter("leLogin", login);
        Intervenant itv = (Intervenant)query.getSingleResult();
        
        Long id = null;
        if(itv.getMotDePasse().equals(motDePasse)){
            id = itv.getId();
        }
        
        return id;
    }
    
    public List<Intervenant> listeOrdonneeIntervenantsDisponibles(Niveau nivEleve){
        String s = "select i from Intervenant i where i.available = 1 and i.niveauMin <= :niveauEleve and :niveauEleve <= i.niveauMax order by i.nbInterventionsTotal asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Intervenant.class);
        query.setParameter("niveauEleve", nivEleve);
        
        return query.getResultList();
    }
    
    public Intervenant update(Intervenant intervenant){
        return JpaUtil.obtenirContextePersistance().merge(intervenant);
    }
}
