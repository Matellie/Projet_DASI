/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import fr.insalyon.dasi.predictif.metier.objets.Employe;
import fr.insalyon.dasi.predictif.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class DemarerConsultationAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de DemarerConsultationAction");
        
        Service service = new Service();
        Consultation consultation = (Consultation)request.getSession(false).getAttribute("consultation");
        Employe employe = (Employe)request.getSession(false).getAttribute("employe");
        
        service.commencerConsultation(consultation, employe);
        
        request.setAttribute("consultationDemaree", Boolean.TRUE);
    }
    
}
