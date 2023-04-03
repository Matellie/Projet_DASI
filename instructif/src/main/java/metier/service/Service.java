/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import metier.modele.Eleve;
import dao.EleveDao;
import dao.JpaUtil;
import util.Message;

/**
 *
 * @author etarassov
 */
public class Service {

    public Service() {
    }
    
    public void inscriptionEleve(Eleve eleve) {
        Message message = new Message();
        EleveDao eleveDao = new EleveDao();
        
        String mailExpediteur = "Systeme";
        String mailDestinataire = eleve.getMail();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            eleveDao.create(eleve);
            
            JpaUtil.validerTransaction();
            
            message.envoyerMail(mailExpediteur, mailDestinataire, "Confirmation inscription", "Vous etes bien inscrit !");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            
            message.envoyerMail(mailExpediteur, mailDestinataire, "Infirmation inscription", "Oups, une erreur s est produite !");
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
    }
}
