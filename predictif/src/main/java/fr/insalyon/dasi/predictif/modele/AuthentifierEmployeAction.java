/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Employe;
import fr.insalyon.dasi.predictif.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class AuthentifierEmployeAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de AuthentifierEmployeAction");
        
        String login = (String)request.getParameter("login");
        String password = (String)request.getParameter("password");
        
        if (login != null && password != null)
        {
            Service service = new Service();
            Employe employe = service.authentifierEmploye(login, password);
            
            if(employe != null)
            {
                request.setAttribute("employe", employe);
                request.getSession(false).setAttribute("employe", employe);
            }
            else
            {
                request.setAttribute("employe", null);
            }
        }
        else
        {
            request.setAttribute("client", null);
        }
    }
    
}
