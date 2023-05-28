/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Client;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class GetProfilAstralClientAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de GetProfilAstralClientAction");
        
        Client client = (Client)request.getSession(false).getAttribute("client");
        
        if (client != null)
        {
            request.setAttribute("profilAstral", client.getProfilAstral());
        }
        else
        {
            request.setAttribute("profilAstral", null);
        }
    }
    
}
