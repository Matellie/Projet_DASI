/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Client;
import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class GetHistoriqueClientAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de GetHistoriqueClientAction");
        
        Client client = (Client)request.getSession(false).getAttribute("client");
        
        if (client != null)
        {
            List<Consultation> historique = client.getHistorique();
            request.setAttribute("historique", historique);
        }
        else
        {
            request.setAttribute("historique", null);
        }
    }
    
}
