/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import metier.modele.*;
import metier.service.Service;
import dao.JpaUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        testerInterfaceEleve();
        
        JpaUtil.fermerFabriquePersistance();
    }
    
    public static void testerInscriptionEleve() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        Eleve bob = null;
        String college1 = "0752524L";
        Eleve suzie = null;
        String college2 = "0691664J";

        Eleve alice = null;
        String lycee1 = "0750654D";
        Eleve steeve = null;
        String lycee2 = "0690132U";

        
        try {
            bob = new Eleve("Henders", "Bob", "bob.henders@insa.fr", "abcf", sdf.parse("2004/12/20"), Niveau.QUATRIEME);
            suzie = new Eleve("Rizzle", "Suzie", "suzie.rizzle@insa.fr", "cool", sdf.parse("2008/10/28"), Niveau.SIXIEME);
            alice = new Eleve("Tribo", "Alice", "alice.tribo@insa.fr", "toto", sdf.parse("2002/06/03"), Niveau.PREMIERE);
            steeve = new Eleve("Bouro", "Steeve", "steeve.bouro@insa.fr", "1234", sdf.parse("2001/01/10"), Niveau.TERMINALE);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Service service = new Service();
        service.inscriptionEleve(bob, college1);
        service.inscriptionEleve(suzie, college2);
        service.inscriptionEleve(alice, lycee1);
        service.inscriptionEleve(steeve, lycee2);
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
        Long idIntervenant = service.trouverIntervenant(eleve);
        System.out.println();
        
        System.out.println("niveau élève: " + eleve.getNiveau() + " idIntervenant: " + idIntervenant);
    }
    
    public static void testerFaireDemandeSoutien() {
        Service service = new Service();
        
        testerInscriptionEleve();
        testerInitialiserIntervenants();
        testerInitialiserMatieres();
        Eleve eleve = service.connexionEleve("m.h@insa.fr", "abcf");
        Long idIntervenant = service.trouverIntervenant(eleve);
        
        String nomMatiere = "Francais";
        String description = "Je voudrais qu on m aide pour ça svp";
        
        service.faireDemandeIntervention(eleve, idIntervenant, nomMatiere, description);
    }
    
    public static void testerConsulterInformationsIntervention() {
        Service service = new Service();
        
        testerInscriptionEleve();
        testerInitialiserIntervenants();
        testerInitialiserMatieres();
        
        // Partie Eleve
        Eleve eleve = service.connexionEleve("m.h@insa.fr", "abcf");
        Long idIntervenantTrouve = service.trouverIntervenant(eleve);
        
        String nomMatiere = "Francais";
        String description = "Je voudrais qu on m aide pour ça svp";
        service.faireDemandeIntervention(eleve, idIntervenantTrouve, nomMatiere, description);
        
        
        // Partie Intervenant
        Long idIntervenant = service.connexionIntervenant("t.g@insa.fr", "zerhb");
        Intervention intervention = service.consulterInformationsIntervention(idIntervenant);
        System.out.println(intervention);
        System.out.println(intervention.getEleve());
        //service.creationVisio(intervention);
    }
    
    public static void testerVisio() {
        Service service = new Service();
        
        testerInscriptionEleve();
        testerInitialiserIntervenants();
        testerInitialiserMatieres();
        
        
        // Partie Eleve
        Eleve eleve = service.connexionEleve("m.h@insa.fr", "abcf");
        Long idIntervenantTrouve = service.trouverIntervenant(eleve);
        
        String nomMatiere = "Francais";
        String description = "Je voudrais qu on m aide pour ça svp";
        Long idIntervention = service.faireDemandeIntervention(eleve, idIntervenantTrouve, nomMatiere, description);
        
        
        // Partie Intervenant
        Long idIntervenant = service.connexionIntervenant("t.g@insa.fr", "zerhb");
        Intervention intervention = service.consulterInformationsIntervention(idIntervenant);
        System.out.println(intervention);
        System.out.println(intervention.getEleve());
        service.creationVisio(intervention);
        
        
        // Partie Eleve n2
        service.arretVisio(idIntervention);
        int note = 4;
        service.autoEvaluation(idIntervention, note);
        
        
        // Partie Intervenant n2
        List<Intervention> interventions = service.historiqueInterventionIntervenant(idIntervenant);
        System.out.println(interventions);
    }
    
    public static void testerStatistiquesInstructif() {
        Service service = new Service();
        testerVisio();
        
        double ipsMoyen = service.getIPSMoyen();
        System.out.println(ipsMoyen);
        Map<Matiere, Long> mapParMatiere = service.nbInterventionsParMatiere();
        System.out.println(mapParMatiere);
        Map<Niveau, Long> mapParNiveau = service.nbInterventionsParNiveau();
        System.out.println(mapParNiveau);
        Map<String, Long> mapParAcademie = service.nbInterventionsParAcademie();
        System.out.println(mapParAcademie);
        Map<String, Long> mapParDepartement = service.nbInterventionsParDepartement();
        System.out.println(mapParDepartement);
        List<Etablissement> listeEtablissements = service.getAllEtablissements();
        System.out.println(listeEtablissements);
    }
    
    public static void testerInterfaceEleve() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        Service service = new Service();
        Scanner scanner = new Scanner(System.in);
        
        // Initialisations
        //service.initialiserIntervenants();
        //service.initialiserMatieres();
        //testerInscriptionEleve();
        
        
        System.out.println();
        System.out.println("---- INSCRIPTION ELEVE ----");
        String nom; String prenom; String mail; String motDePasse; Date dateDeNaissance = null; Niveau niveau; String etablissement;
        System.out.print("Nom : "); nom = scanner.nextLine();
        System.out.print("Prenom : "); prenom = scanner.nextLine();
        System.out.print("Mail : "); mail = scanner.nextLine();
        System.out.print("Mot de passe : "); motDePasse = scanner.nextLine();
        System.out.print("Date naissance yyyy/mm/dd : ");
        try {dateDeNaissance = sdf.parse(scanner.nextLine());} catch (ParseException ex) {Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);}
        System.out.print("Niveau 6e->0 ... term->6 : "); niveau = Niveau.values()[Integer.parseInt(scanner.nextLine())];
        System.out.print("Numero etablissement : "); etablissement = scanner.nextLine();
        Eleve user = new Eleve(nom, prenom, mail, motDePasse, dateDeNaissance, niveau);
        service.inscriptionEleve(user, etablissement);
        
      
        System.out.println();
        System.out.println("---- CONNEXION ELEVE ----");
        System.out.print("Mail : "); mail = scanner.nextLine();
        System.out.print("Mot de passe : "); motDePasse = scanner.nextLine();
        Eleve eleveConnecte = service.connexionEleve(mail, motDePasse);
        if(eleveConnecte != null) {
            System.out.println("L utilisateur connecté est: " + eleveConnecte);


            System.out.println();
            System.out.println("---- DEMANDE INTERVENTION ----");
            List<Intervention> interventionsEleve = service.historiqueInterventionEleve(eleveConnecte);
            System.out.print(interventionsEleve);
            Long idIntervenant = service.trouverIntervenant(eleveConnecte);
            System.out.print("Matiere (liste matieres) : "); System.out.println(service.getAllMatieres());
            String matiere = scanner.nextLine();
            System.out.print("Description : "); String description = scanner.nextLine();
            Long idIntervention = service.faireDemandeIntervention(eleveConnecte, idIntervenant, matiere, description);
            //System.out.println("Les informations de l intervention sont : " + eleveConnecte);


            // Partie Intervenant
            System.out.println();
            System.out.println("---- CONNEXION INTERVENANT ----");
            System.out.print("Mail : "); String mailInter = scanner.nextLine();
            System.out.print("Mot de passe : "); String motDePasseInter = scanner.nextLine();
            Long idIntervenantConnecte = service.connexionIntervenant(mailInter, motDePasseInter);
            System.out.print(service.getIntervenantById(idIntervenantConnecte));

            System.out.println();
            System.out.println("---- VOIR INFOS INTERVENTION ----");
            System.out.print("Tapez entrer pour voir les infos de l intervention"); scanner.nextLine();
            Intervention intervention = service.consulterInformationsIntervention(idIntervenantConnecte);
            System.out.println(intervention);


            if(intervention != null) {
                System.out.println();
                System.out.println("---- DEMARER VISIO ----");
                System.out.print("Tapez entrer pour démarer la visio"); scanner.nextLine();
                service.creationVisio(intervention);
                // Partie Intervenant


                System.out.println();
                System.out.println("---- ARRET INTERVENTION ----");
                System.out.print("Tapez entrer pour terminer la visio"); scanner.nextLine();
                service.arretVisio(idIntervention);


                System.out.println();
                System.out.println("---- AUTO-EVALUATION ----");
                System.out.print("Note : "); int note = Integer.parseInt(scanner.nextLine());
                service.autoEvaluation(idIntervention, note);
            }


            // Partie Intervenant
            System.out.println();
            System.out.println("---- VOIR HISTORIQUE ET STATS INTERVENTIONS ----");
            List<Intervention> interventionsIntervenant = service.historiqueInterventionIntervenant(idIntervenantConnecte);
            System.out.print(interventionsIntervenant);

            System.out.println(service.getIPSMoyen());
            System.out.println(service.getAllEtablissements());
            System.out.println(service.nbInterventionsParMatiere());
            System.out.println(service.nbInterventionsParNiveau());
            System.out.println(service.nbInterventionsParAcademie());
            System.out.println(service.nbInterventionsParDepartement());
            // Partie Intervenant
        }
    }
    
    /*public static void testerInterfaceIntervenant() {
        Service service = new Service();
        Scanner scanner = new Scanner(System.in);
        
        // Initialisations
        //service.initialiserIntervenants();
        //testerInscriptionEleve();
        //service.initialiserMatieres();
        
        
        System.out.println();
        System.out.println("---- RECEPTION NOTIFICATION ----");
        // Partie Eleve
        Eleve eleve = service.connexionEleve("m.h@insa.fr", "abcf");
        Long idIntervenant = service.trouverIntervenant(eleve);
        String nomMatiere = "Francais"; String description = "Aidez moi svp";
        Long idIntervention = service.faireDemandeIntervention(eleve, idIntervenant, nomMatiere, description);
        System.out.println("id de l intervenant : " + idIntervenant);
        // Partie Eleve
        
        
        System.out.println();
        System.out.println("---- CONNEXION INTERVENANT ----");
        System.out.print("Mail : "); String mail = scanner.nextLine();
        System.out.print("Mot de passe : "); String motDePasse = scanner.nextLine();
        Long idIntervenantConnecte = service.connexionIntervenant(mail, motDePasse);
        System.out.println("id de l intervenant connecté : " + idIntervenantConnecte);
        
        
        System.out.println();
        System.out.println("---- VOIR INFOS INTERVENTION ----");
        System.out.print("Tapez entrer pour voir les infos de l intervention"); scanner.nextLine();
        Intervention intervention = service.consulterInformationsIntervention(idIntervenantConnecte);
        System.out.println(intervention);
        System.out.println(intervention.getEleve());
        
        
        System.out.println();
        System.out.println("---- DEMARER VISIO ----");
        System.out.print("Tapez entrer pour démarer la visio"); scanner.nextLine();
        service.creationVisio(intervention);
        
        
        // Partie Eleve
        service.arretVisio(idIntervention);
        // Partie Eleve
        
        
        System.out.println();
        System.out.println("---- VOIR HISTORIQUE ET STATS INTERVENTIONS ----");
        service.historiqueInterventionIntervenant(idIntervenantConnecte);
        
        System.out.println(service.getIPSMoyen());
        System.out.println(service.getAllEtablissements());
        System.out.println(service.nbInterventionsParMatiere());
        System.out.println(service.nbInterventionsParNiveau());
        System.out.println(service.nbInterventionsParAcademie());
        System.out.println(service.nbInterventionsParDepartement());
    }*/
}
