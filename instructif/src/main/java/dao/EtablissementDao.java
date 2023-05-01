/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Etablissement;

/**
 *
 * @author mhabran
 */
public class EtablissementDao {

    public EtablissementDao() {
    }

    public void create(Etablissement etablissement) {
        JpaUtil.obtenirContextePersistance().persist(etablissement);
    }

    public Etablissement getEtablissement(String uai) {
        String s = "select e from Etablissement e where e.uai = :leUai";

        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Etablissement.class);
        query.setParameter("leUai", uai);

        Etablissement etab = null;

        try {
            etab = (Etablissement) query.getSingleResult();
        } 
        catch (Exception ex) {

        }
        
        return etab;
    }
    
    public List<String> getAllIPS() {
        String s = "select e.ips from Etablissement e";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Etablissement.class);
        List<String> allIPS = query.getResultList();
        
        return allIPS;
    }
    
    public List<String> getAllAcademie() {
        String s = "select e.academie from Etablissement e";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Etablissement.class);
        List<String> allAcademie = query.getResultList();
        
        return allAcademie;
    }
}
