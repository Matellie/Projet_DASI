/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.metier.objets.Client;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author etarassov
 */
public class StatutInscriptionClientSerialisation extends Serialisation {
    
    @Override
    public void serializer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[TEST] Appel de StatutInscriptionClientSerialisation");
        
        Client client = (Client)request.getAttribute("client");
        JsonObject jsonInscription = new JsonObject();
        
        if (client != null)
        {
            jsonInscription.addProperty("inscription", Boolean.TRUE);
        }
        else
        {
            jsonInscription.addProperty("inscription", Boolean.FALSE);
        }

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(jsonInscription, out);
        out.close();
    }
    
}
