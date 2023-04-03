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
        JpaUtil.creerFabriquePersistance();
        
        testerInscriptionEleve();
        
        JpaUtil.fermerFabriquePersistance();
        
    }
    
    public static void testerInscriptionEleve() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        Eleve bob = null;
        String college = "0691664J";
        Eleve alice = null;
        String lycee = "0690132U";
        Eleve steeve = null;
        
        try {
            bob = new Eleve("H", "M", "m.h@insa.fr", "abcf", sdf.parse("2002/12/20"), Niveau.QUATRIEME);
            alice = new Eleve("T", "E", "e.t@insa.fr", "toto", sdf.parse("2002/06/03"), Niveau.PREMIERE);
            steeve = new Eleve("B", "S", "s.b@insa.fr", "1234", sdf.parse("2002/01/10"), Niveau.PREMIERE);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Service service = new Service();
        service.inscriptionEleve(bob, college);
        service.inscriptionEleve(alice, lycee);
        service.inscriptionEleve(steeve, lycee);
    }
}
