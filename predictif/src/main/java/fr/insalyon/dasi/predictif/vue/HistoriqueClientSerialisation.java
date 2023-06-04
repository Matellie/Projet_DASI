/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
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
            
            JsonArray array = new JsonArray();
            
            for(Consultation c : consultations)
            {
                System.out.println(c);
                JsonObject jsonConsult = new JsonObject();
                jsonConsult.addProperty("id", c.getId());
                jsonConsult.addProperty("date", sdf.format(c.getDate_heure()));
                jsonConsult.addProperty("medium", c.getMedium().getDenomination());
                
                array.add(jsonConsult);
                
            }
            jsonHistorique.add("consultations", array);
        }
        else
        {
            jsonHistorique.addProperty("empty", Boolean.TRUE);
        }
        System.out.println(jsonHistorique);

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(jsonHistorique, out);
        out.close();
    }
    
}
