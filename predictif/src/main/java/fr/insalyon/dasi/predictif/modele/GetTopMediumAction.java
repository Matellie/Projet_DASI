/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Medium;
import fr.insalyon.dasi.predictif.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class GetTopMediumAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de GetTopMediumAction");
        
        Service service = new Service();
        int nbTopMedium = Integer.parseInt((String)request.getParameter("nbTopMedium"));
        
        List<Medium> topMediums = service.findTopMedium(nbTopMedium);
        
        if (topMediums != null)
        {
            request.setAttribute("topMediums", topMediums);
        }
        else
        {
            request.setAttribute("topMediums", null);
        }
    }
    
}
