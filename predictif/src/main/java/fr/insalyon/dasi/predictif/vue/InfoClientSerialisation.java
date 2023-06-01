/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.vue;

import java.io.IOException;
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
        
        
    }
    
}
