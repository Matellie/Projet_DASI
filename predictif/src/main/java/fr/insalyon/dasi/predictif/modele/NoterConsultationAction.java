/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.modele;

import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import fr.insalyon.dasi.predictif.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author etarassov
 */
public class NoterConsultationAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        System.out.println("[TEST] Appel de GetNoteAction");
        
        Service service = new Service();
        Long idConsultation = Long.parseLong(request.getParameter("idConsultation"));
        Consultation consultation = service.findConsultation(idConsultation);
        int note = Integer.parseInt(request.getParameter("note"));
        
        Boolean statut = service.noterConsultation(consultation, note);
        
        if (statut)
        {
            request.setAttribute("statut", Boolean.TRUE);
        }
        else
        {
            request.setAttribute("statut", Boolean.FALSE);
        }
    }
    
}
