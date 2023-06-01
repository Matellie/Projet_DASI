/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Client;
import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import fr.insalyon.dasi.predictif.metier.objets.ProfilAstral;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class GetInfoClientAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de GetInfoClientAction");
        
        Consultation consultation = (Consultation)request.getSession(false).getAttribute("consultation");
        Client client = consultation.getClient();
        
        request.setAttribute("profilAstralClient", client.getProfilAstral());
        request.setAttribute("historiqueClient", client.getHistorique());
    }
    
}
