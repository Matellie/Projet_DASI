/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.dao;

import java.util.List;
import fr.insalyon.dasi.predictif.metier.objets.Client;
import javax.persistence.TypedQuery;

/**
 *
 * @author jbondyfala
 */
public class ClientDao {
    
    public void create(Client e) {
        JpaUtil.obtenirContextePersistance().persist(e);
    }

    
    public Client update(Client e) {
        return JpaUtil.obtenirContextePersistance().merge(e);
    }

    
    public Client authentifier(String mail, String mdp) {
        String s = "select e from Client e where e.mail = :mail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        query.setParameter("mail", mail);
        Client e = (Client) query.getSingleResult();
        if (e.getMotDePasse().equals(mdp)) {
            return e;
        }
        else {
            return null;
        }
    }
   
    
}
