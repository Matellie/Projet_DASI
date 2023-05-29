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
import fr.insalyon.dasi.predictif.metier.objets.Medium;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author etarassov
 */
public class AllMediumSerialisation extends Serialisation {
    
    @Override
    public void serializer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[TEST] Appel de ProfilAstralClientSerialisation");
        
        List<Medium> allMedium = (List<Medium>)request.getAttribute("allMedium");
        JsonObject jsonAllMedium = new JsonObject();
        
        if (allMedium != null)
        {
            jsonAllMedium.addProperty("allMedium", Boolean.TRUE);
            
            JsonArray mediumList = new JsonArray();
            for(Medium m : allMedium)
            {
                JsonObject jsonMedium = new JsonObject();
                jsonMedium.addProperty("type", m.getClass().getSimpleName());
                jsonMedium.addProperty("nom", m.getDenomination());
                jsonMedium.addProperty("presentation", m.getPresentation());
                
                mediumList.add(jsonMedium);
            }
            
            jsonAllMedium.add("mediumList", mediumList);
        }
        else
        {
            jsonAllMedium.addProperty("allMedium", Boolean.FALSE);
        }
        System.out.println(jsonAllMedium);

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(jsonAllMedium, out);
        out.close();
    }
    
}
