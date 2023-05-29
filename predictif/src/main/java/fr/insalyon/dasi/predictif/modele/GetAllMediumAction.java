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
public class GetAllMediumAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de GetAllMediumAction");
        
        Service service = new Service();
        List<Medium> allMedium = (List<Medium>)service.findAllMedium();
        
        if (allMedium != null)
        {
            request.setAttribute("allMedium", allMedium);
        }
        else
        {
            request.setAttribute("allMedium", null);
        }
    }
    
}
