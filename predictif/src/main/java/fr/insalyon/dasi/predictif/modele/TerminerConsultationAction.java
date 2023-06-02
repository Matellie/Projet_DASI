/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import fr.insalyon.dasi.predictif.metier.objets.Employe;
import fr.insalyon.dasi.predictif.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class TerminerConsultationAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de TerminerConsultationAction");
        
        Service service = new Service();
        Consultation consultation = (Consultation)request.getSession(false).getAttribute("consultation");
        Employe employe = (Employe)request.getSession(false).getAttribute("employe");
        String commentaire = (String)request.getParameter("commentaire");
        
        service.terminerConsultation(consultation, employe, commentaire);
        
        request.setAttribute("termine", Boolean.TRUE);
    }
    
}
