/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Client;
import fr.insalyon.dasi.predictif.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class GetInfoClientAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de GetInfoClientAction");
        
        Service service = new Service();
        
        Long consultationId = (Long)request.getSession(false).getAttribute("consultationEnCoursId");
        Client client = service.findConsultation(consultationId).getClient();
    }
    
}
