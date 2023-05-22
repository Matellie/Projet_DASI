/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Client;
import fr.insalyon.dasi.predictif.metier.service.Service;
import fr.insalyon.dasi.test.predictif.TestClient;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class AuthentifierClientAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de AuthentifierClientAction");
        
        String login = (String)request.getParameter("login");
        String password = (String)request.getParameter("password");
        
        if (login != null) {
            Service service = new Service();
            Client client = service.authentifierClient(login, password);            
            if(client != null){
                request.setAttribute("client", client);
            } else {
                System.out.println("Personne n'est connecté");
                request.setAttribute("client", null);
            }
        }
    }
    
}
