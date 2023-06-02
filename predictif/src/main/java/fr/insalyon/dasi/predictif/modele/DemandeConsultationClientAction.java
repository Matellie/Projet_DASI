/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Client;
import fr.insalyon.dasi.predictif.metier.objets.Medium;
import fr.insalyon.dasi.predictif.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class DemandeConsultationClientAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de DemandeConsultationClientAction");
        
        Service service = new Service();
        
        Client client = (Client)request.getSession(false).getAttribute("client");
        Medium medium = service.findMedium(Long.parseLong(request.getParameter("mediumId")));
        
        Boolean consultationAcceptee = (Boolean)service.demanderConsultation(client, medium);
        
        request.setAttribute("consultationAcceptee", consultationAcceptee);
    }
    
}
