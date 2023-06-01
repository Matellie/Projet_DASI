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
import fr.insalyon.dasi.predictif.metier.objets.ProfilAstral;
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
public class InfoClientSerialisation extends Serialisation {
    
    @Override
    public void serializer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[TEST] Appel de InfoClientSerialisation");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        JsonObject jsonInfoClient = new JsonObject();
        
        
        ProfilAstral profilAstral = (ProfilAstral)request.getAttribute("profilAstralClient");
        JsonObject jsonProfilAstral = new JsonObject();
        jsonProfilAstral.addProperty("zodiaque", profilAstral.getZodiaque());
        jsonProfilAstral.addProperty("couleur", profilAstral.getCouleur());
        jsonProfilAstral.addProperty("animal", profilAstral.getAnimal());
        jsonProfilAstral.addProperty("chinois", profilAstral.getChinois());
        
        
        List<Consultation> consultations = (List<Consultation>)request.getAttribute("historiqueClient");
        JsonObject jsonHistorique = new JsonObject();
        for(Consultation c : consultations)
        {
            JsonObject jsonConsult = new JsonObject();
            jsonConsult.addProperty("id", c.getId());
            jsonConsult.addProperty("date", sdf.format(c.getDate_heure()));
            jsonConsult.addProperty("medium", c.getMedium().getDenomination());
            // Il n est pas possible de retrouver l employe qui a joué le medium facilement
            jsonConsult.addProperty("commentaire", c.getCommentaire());

            jsonHistorique.add("consultations", jsonConsult);
        }
        
        
        jsonInfoClient.add("profilAstralClient", jsonProfilAstral);
        jsonInfoClient.add("historiqueClient", jsonHistorique);
        
        
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(jsonInfoClient, out);
        out.close();
    }
    
}
