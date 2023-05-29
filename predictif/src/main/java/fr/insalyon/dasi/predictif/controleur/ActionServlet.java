/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.controleur;

import fr.insalyon.dasi.predictif.dao.JpaUtil;
import fr.insalyon.dasi.predictif.metier.service.Service;
import fr.insalyon.dasi.predictif.modele.*;
import fr.insalyon.dasi.predictif.vue.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author etarassov
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        JpaUtil.creerFabriquePersistance();
        //this.initDB();
    }

    @Override
    public void destroy() {
        JpaUtil.fermerFabriquePersistance();
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("[TEST] Appel de l’ActionServlet");
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(true);
        
        Action action = null;
        Serialisation serialisation = null;
        
        String todo = request.getParameter("todo");
        
        switch(todo) {
            case "inscrireClient" : {
                action = new InscrireClientAction();
                serialisation = new StatutInscriptionClientSerialisation();
            }
            break;
            
            case "authentifierClient" : {
                action = new AuthentifierClientAction();
                serialisation = new StatutConnexionClientSerialisation();
            }
            break;
            
            case "authentifierEmploye" : {
                action = new AuthentifierEmployeAction();
                serialisation = new StatutConnexionEmployeSerialisation();
            }
            break;
            
            case "getHistoriqueClient" : {
                action = new GetHistoriqueClientAction();
                serialisation = new HistoriqueClientSerialisation();
            }
            break;
            
            case "getProfilAstralClient" : {
                action = new GetProfilAstralClientAction();
                serialisation = new ProfilAstralClientSerialisation();
            }
            break;
            
            case "getAllMedium" : {
                action = new GetAllMediumAction();
                serialisation = new AllMediumSerialisation();
            }
            break;
            
            case "demandeConsultationClient" : {
                
            }
            break;
            
            case "getHistoriqueEmploye" : {
                
            }
            break;
            
        }
        
        if(action != null && serialisation != null)
        {
            action.executer(request);
            serialisation.serializer(request, response);
        }
        else
        {
            response.sendError(400, "Bad request");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    public void initDB() {
        Service service = new Service();
        service.initialiserEmployesMedium();
    }
}
