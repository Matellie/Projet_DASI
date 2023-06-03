/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Client;
import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import fr.insalyon.dasi.predictif.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class GetNoteAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de GetNoteAction");
        
        Service service = new Service();
        Long idConsultation = Long.parseLong(request.getParameter("idConsultation"));
        Consultation consultation = service.findConsultation(idConsultation);
        
        if (consultation.getNote() == null)
        {
            request.setAttribute("dejaNote", Boolean.FALSE);
        }
        else
        {
            request.setAttribute("dejaNote", Boolean.TRUE);
            request.setAttribute("note", consultation.getNote());
        }
    }
    
}
