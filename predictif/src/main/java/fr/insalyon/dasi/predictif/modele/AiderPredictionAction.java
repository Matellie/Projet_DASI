/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import fr.insalyon.dasi.predictif.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class AiderPredictionAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de AiderPredictionAction");
        
        Service service = new Service();
        Consultation consultation = (Consultation)request.getSession(false).getAttribute("consultation");
        int amour = Integer.parseInt((String)request.getParameter("amour"));
        int sante = Integer.parseInt((String)request.getParameter("sante"));
        int travail = Integer.parseInt((String)request.getParameter("travail"));
        
        List<String> predictions = service.obtenirPredictions(consultation.getClient(), amour, sante, travail);
        
        if (predictions != null)
        {
            request.setAttribute("predictions", predictions);
        }
        else
        {
            request.setAttribute("predictions", null);
        }
    }
    
}
