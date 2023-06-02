/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author etarassov
 */
public class HistoriqueClientSerialisation extends Serialisation {
    
    @Override
    public void serializer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[TEST] Appel de HistoriqueClientSerialisation");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Consultation> consultations = (List<Consultation>)request.getAttribute("historique");
        JsonObject jsonHistorique = new JsonObject();
        
        if (consultations != null)
        {
            jsonHistorique.addProperty("empty", Boolean.FALSE);
            
            for(Consultation c : consultations)
            {
                JsonObject jsonConsult = new JsonObject();
                jsonConsult.addProperty("id", c.getId());
                jsonConsult.addProperty("medium", c.getMedium().getDenomination());
                jsonConsult.addProperty("date", sdf.format(c.getDate_heure()));
                
                jsonHistorique.add("consultations", jsonConsult);
            }
        }
        else
        {
            jsonHistorique.addProperty("empty", Boolean.TRUE);
        }

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(jsonHistorique, out);
        out.close();
    }
    
}
