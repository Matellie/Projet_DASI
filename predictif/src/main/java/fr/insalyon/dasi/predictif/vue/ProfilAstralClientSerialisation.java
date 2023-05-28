/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.metier.objets.ProfilAstral;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author etarassov
 */
public class ProfilAstralClientSerialisation extends Serialisation {
    
    @Override
    public void serializer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[TEST] Appel de ProfilAstralClientSerialisation");
        
        ProfilAstral profilAstral = (ProfilAstral)request.getAttribute("profilAstral");
        JsonObject jsonProfilAstral = new JsonObject();
        
        if (profilAstral != null)
        {
            jsonProfilAstral.addProperty("zodiaque", profilAstral.getZodiaque());
            jsonProfilAstral.addProperty("couleur", profilAstral.getCouleur());
            jsonProfilAstral.addProperty("animal", profilAstral.getAnimal());
            jsonProfilAstral.addProperty("chinois", profilAstral.getChinois());
        }
        System.out.println(jsonProfilAstral);

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(jsonProfilAstral, out);
        out.close();
    }
    
}
