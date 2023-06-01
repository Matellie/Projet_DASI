/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Client;
import fr.insalyon.dasi.predictif.metier.service.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class InscrireClientAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de InscrireClientAction");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        String prenom = (String)request.getParameter("prenom");
        String nom = (String)request.getParameter("nom");
        String mail = (String)request.getParameter("mail");
        String tel = (String)request.getParameter("telephone");
        String sexe = (String)request.getParameter("sexe");
        Date dateNaissance = null;
        try {
            dateNaissance = sdf.parse((String)request.getParameter("datenaissance"));
        } catch (ParseException ex) {
            Logger.getLogger(InscrireClientAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        String adresse = (String)request.getParameter("adresse");
        String password = (String)request.getParameter("password");
        
        if (true)
        {
            System.out.println("Inscription client");
            Service service = new Service();
            
            Client client = new Client(nom, prenom, dateNaissance, sexe.equals("masculin"), tel, adresse, mail);
            client.setMotDePasse(password);
            
            service.inscriptionClient(client);
            
            request.setAttribute("client", client);
        }
        else // A implementer si test de la validité des inputs
        {
            System.out.println("Personne n'est inscrit");
            request.setAttribute("client", null);
        }
    }
    
}
