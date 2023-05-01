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
import metier.modele.*;
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
        
        testerConsulterInformationsIntervention();
        
        JpaUtil.fermerFabriquePersistance();
        
    }
    
    public static void testerInscriptionEleve() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        Eleve bob = null;
        Eleve suzie = null;
        String college = "0691664J";

        Eleve alice = null;
        Eleve steeve = null;
        String lycee = "0690132U";

        
        try {
            bob = new Eleve("H", "M", "m.h@insa.fr", "abcf", sdf.parse("2002/12/20"), Niveau.QUATRIEME);
            suzie = new Eleve("R", "T", "t.r@insa.fr", "cool", sdf.parse("2005/10/28"), Niveau.SIXIEME);
            alice = new Eleve("T", "E", "e.t@insa.fr", "toto", sdf.parse("2002/06/03"), Niveau.PREMIERE);
            steeve = new Eleve("B", "S", "s.b@insa.fr", "1234", sdf.parse("2001/01/10"), Niveau.TERMINALE);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Service service = new Service();
        service.inscriptionEleve(bob, college);
        service.inscriptionEleve(suzie, college);
        service.inscriptionEleve(alice, lycee);
        service.inscriptionEleve(steeve, lycee);
    }
    
    public static void testerInitialiserIntervenants() {
        Service service = new Service();
        service.initialiserIntervenants();
    }
    
    public static void testerInitialiserMatieres() {
        Service service = new Service();
        service.initialiserMatieres();
    }
    
    public static void testerConnexionIntervenant() {
        Service service = new Service();
        service.initialiserIntervenants();
        Long id = service.connexionIntervenant("l.b@insa.fr", "ghdsf");
        
        System.out.println(id);
    }
    
    public static void testerConnexionEleve() {
        Service service = new Service();
        
        testerInscriptionEleve();
        Eleve eleve = service.connexionEleve("m.h@insa.fr", "abcf");
        
        System.out.println(eleve);
    }
    
    public static void testerTrouverIntervenant() {
        Service service = new Service();
        
        System.out.println("INSCRIPTION ELEVES");
        testerInscriptionEleve();
        System.out.println();
        
        System.out.println("INSCRIPTION INTERVENANTS");
        testerInitialiserIntervenants();
        System.out.println();
        
        System.out.println("CONNEXION ELEVE");
        Eleve eleve = service.connexionEleve("m.h@insa.fr", "abcf");
        System.out.println();
        
        System.out.println("TROUVER INTERVENANT");
        Long idIntervenant = service.TrouverIntervenant(eleve);
        System.out.println();
        
        System.out.println("niveau élève: " + eleve.getNiveau() + " idIntervenant: " + idIntervenant);
    }
    
    public static void testerFaireDemandeSoutien() {
        Service service = new Service();
        
        testerInscriptionEleve();
        testerInitialiserIntervenants();
        testerInitialiserMatieres();
        Eleve eleve = service.connexionEleve("m.h@insa.fr", "abcf");
        Long idIntervenant = service.TrouverIntervenant(eleve);
        
        String nomMatiere = "Francais";
        String description = "Je voudrais qu on m aide pour ça svp";
        
        service.faireDemandeSoutien(eleve, idIntervenant, nomMatiere, description);
    }
    
    public static void testerConsulterInformationsIntervention() {
        Service service = new Service();
        
        testerInscriptionEleve();
        testerInitialiserIntervenants();
        testerInitialiserMatieres();
        
        // Partie Eleve
        Eleve eleve = service.connexionEleve("m.h@insa.fr", "abcf");
        Long idIntervenantTrouve = service.TrouverIntervenant(eleve);
        
        String nomMatiere = "Francais";
        String description = "Je voudrais qu on m aide pour ça svp";
        service.faireDemandeSoutien(eleve, idIntervenantTrouve, nomMatiere, description);
        
        
        // Partie Intervenant
        Long idIntervenant = service.connexionIntervenant("t.g@insa.fr", "zerhb");
        Intervention intervention = service.consulterInformationsIntervention(idIntervenant);
        System.out.println(intervention);
        System.out.println(intervention.getEleve());
        //service.creationVisio(intervention);
    }
}
