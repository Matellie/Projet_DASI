/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Employe;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class GetHistoriqueEmployeAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de GetHistoriqueEmployeAction");
        
        Employe employe = (Employe)request.getSession(false).getAttribute("employe");
        
        if (employe != null)
        {
            request.setAttribute("historique", employe.getHistorique());
        }
        else
        {
            request.setAttribute("historique", null);
        }
    }
    
}
