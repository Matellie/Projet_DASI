/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Eleve;
import metier.modele.Niveau;
import metier.service.Service;

/**
 *
 * @author etarassov
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        Eleve mathieu = null;
        
        try {
            mathieu = new Eleve("H", "M", "m.h@insa.fr", "abcf", sdf.parse("2002/12/20"), Niveau.QUATRIEME);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JpaUtil.creerFabriquePersistance();
        
        testerInscriptionEleve(mathieu);
        
        JpaUtil.fermerFabriquePersistance();
        
    }
    
    public static void testerInscriptionEleve(Eleve eleve) {
        Service service = new Service();
        service.inscriptionEleve(eleve);
    }
}
