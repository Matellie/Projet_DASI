/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import fr.insalyon.dasi.predictif.metier.objets.Employe;
import java.util.List;
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
            List<Consultation> historique = employe.getHistorique();
            request.setAttribute("historique", historique);
        }
        else
        {
            request.setAttribute("historique", null);
        }
    }
    
}
