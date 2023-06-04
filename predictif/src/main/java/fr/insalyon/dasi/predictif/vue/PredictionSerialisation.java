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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author etarassov
 */
public class PredictionSerialisation extends Serialisation {
    
    @Override
    public void serializer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[TEST] Appel de PredictionSerialisation");
        
        List<String> predictions = (List<String>)request.getAttribute("predictions");
        System.out.println(predictions);
        JsonObject jsonPredictions = new JsonObject();
        if (predictions != null)
        {
            jsonPredictions.addProperty("predictionsGenerees", Boolean.TRUE);

            JsonArray array = new JsonArray();
            for(String str : predictions)
            {
                JsonObject jsonPrediction = new JsonObject();
                jsonPrediction.addProperty("texte", str);
                
                array.add(jsonPrediction);
            }
            jsonPredictions.add("prediction", array);
        }
        else
        {
            jsonPredictions.addProperty("predictionsGenerees", Boolean.FALSE);
        }
        
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(jsonPredictions, out);
        out.close();
    }
    
}
