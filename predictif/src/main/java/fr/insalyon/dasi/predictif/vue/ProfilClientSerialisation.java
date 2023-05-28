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
import fr.insalyon.dasi.test.predictif.TestClient;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author etarassov
 */
public class ProfilClientSerialisation extends Serialisation {

    @Override
    public void serializer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Client utilisateur = (Client)request.getAttribute("client");
        JsonObject jsonConnexion = new JsonObject();

        if (utilisateur != null) {
            JsonObject jsonUser = new JsonObject();
            jsonUser.addProperty("id", utilisateur.getId());
            jsonUser.addProperty("nom", utilisateur.getNom());
            jsonUser.addProperty("prenom", utilisateur.getPrenom());
            jsonUser.addProperty("tel", utilisateur.getMail());
            jsonUser.addProperty("dateNaissance", sdf.format(utilisateur.getDateNaissance()));
            jsonUser.addProperty("password", utilisateur.getMotDePasse());
            if(utilisateur.isMasculin()){
                jsonUser.addProperty("sexe", "masculin");
            }else{
                jsonUser.addProperty("sexe", "feminin");
            }
            

            jsonConnexion.addProperty("connexion", Boolean.TRUE);
            jsonConnexion.add("client", jsonUser);

            System.out.println(jsonConnexion);            
        }
        else {
            System.out.println("Rien");
            jsonConnexion.addProperty("connexion", Boolean.FALSE);

            System.out.println(jsonConnexion);
        }
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(jsonConnexion, out);
        out.close();
    }
    
}
