/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.dao;

import fr.insalyon.dasi.predictif.metier.objets.Medium;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author jbondyfala
 */
public class MediumDao {
    public void create(Medium e) {
        JpaUtil.obtenirContextePersistance().persist(e);
    }
    public Medium update(Medium e) {
        return JpaUtil.obtenirContextePersistance().merge(e);
    }
    public List<Medium>  findAll() {
        String s = "select e from Medium e order by e.popularite desc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Medium.class);
        return query.getResultList();
    }
    public Medium findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Medium.class, id);
    }
    
    public Long getNbTotalConsult() {
        String s = "select sum(m.popularite) from Medium m";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Medium.class);
        return (Long)query.getSingleResult();
    }
}
