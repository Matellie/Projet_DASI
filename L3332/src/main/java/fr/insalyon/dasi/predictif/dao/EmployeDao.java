/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.dao;

import fr.insalyon.dasi.predictif.metier.objets.Employe;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author jbondyfala
 */
public class EmployeDao {
    
    public void create(Employe e) {
        JpaUtil.obtenirContextePersistance().persist(e);
    }

    public Employe update(Employe e) {
        return JpaUtil.obtenirContextePersistance().merge(e);
    }
    
    
    public Employe authentifier(String mail, String mdp) {
        String s = "select e from Employe e where e.mail = :mail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        query.setParameter("mail", mail);
        Employe e = (Employe) query.getSingleResult();
        if (e.getMotDePasse().equals(mdp)) {
            return e;
        }
        else {
            return null;
        }
    }
    
    public List<Employe>  findOnline(boolean genreMed) {
        String s = "select e from Employe e where e.masculin= :masculin and e.online= :online order by e.nbConsult asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        query.setParameter("masculin", genreMed);
        query.setParameter("online", true);
        return query.getResultList();
    }
    
    public List<Employe> findAll() {
        String s = "select e from Employe e";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        return query.getResultList();
    }
    
}
