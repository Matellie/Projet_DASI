/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.vue;

import fr.insalyon.dasi.predictif.metier.service.Service;
import fr.insalyon.dasi.predictif.metier.objets.Medium;
import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import fr.insalyon.dasi.predictif.metier.objets.Client;
import fr.insalyon.dasi.predictif.metier.objets.Employe;
import fr.insalyon.dasi.predictif.dao.JpaUtil;
import fr.insalyon.dasi.predictif.util.Saisie;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * Cette classe Main contient un ensemble de test des different service et cas d'usage principaux
 * 
 * @author jbondyfala
 */
public class TestMain {
    
    public static void main(String[] args) {
       
        JpaUtil.creerFabriquePersistance();
        
        //Toujours executer ces 2 lignes en drop and create
        initEmployeMedium();
        testInscriptionClient();
        
        
        //testAuthentifierClient();
        //testAuthentifierEmploye();
        
        
        //testDemandeConsultation();
        //testConsultation();
        
        //A partir de la create
        
        //testNoteHistorique();
        
        //testStatistiques();
        
        
        JpaUtil.fermerFabriquePersistance();
        
    }
    
    public static void initEmployeMedium() {
        Service s = new Service();
        s.initialiserEmployesMedium();
    }
    
    public static void testInscriptionClient() {
        try {
            Service s = new Service();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Client c = new Client("jean", "jean", new Date(), true, "0102030405", "adresse", "jean@mail");
            c.setMotDePasse("jean");
            
            //Fonctionne
            s.inscriptionClient(c);
            
            Client c3 = new Client("albert", "georges", new Date(), true, "0504030201", "adresse2", "jean@mail");
            c3.setMotDePasse("mdp");
            
            //Fonctionne pas car condition unique sur mail
            s.inscriptionClient(c3);
            
            Client c2 = new Client("toto", "toto", sdf.parse("2012/11/12"), true, "0102030405", "adresse", "toto@mail");
            c2.setMotDePasse("toto");
            
            //Fonctionne
            s.inscriptionClient(c2);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    
    public static Client testAuthentifierClient() {
        Service s = new Service();
        String mail= Saisie.lireChaine("Mail : ");
        String mdp= Saisie.lireChaine("Mot de passe : ");
        Client e = s.authentifierClient(mail, mdp);
        
        if (e != null) {
            System.out.println("Bonjour " + e.getPrenom());
            return e;
        }
        else {
            System.out.println("Erreur lors de la connexion");
            return null;
        }
        
    }
    
    public static Employe testAuthentifierEmploye() {
        Service s = new Service();
        String mail= Saisie.lireChaine("Mail : ");
        String mdp= Saisie.lireChaine("Mot de passe : ");
        Employe e = s.authentifierEmploye(mail, mdp);
        
        if (e != null) {
            System.out.println("Bonjour " + e.getPrenom());
            return e;
        }
        else {
            System.out.println("Erreur lors de la connexion");
            return null;
        }
        
    }
    
    public static void testStatistiques() {
        
        Service s = new Service();
        
        Map<Medium, Float> repartMedium = s.calculerRepartitionPopularite();
        
        for (Map.Entry<Medium, Float> o : repartMedium.entrySet()) {
            System.out.println(o.getKey() + " -> " + o.getValue() * 100);
        }
        
        System.out.println("");
        
        Map<Employe, Map<Medium, Integer>> repartEmp = s.calculerRepartitionEmploye();
        
        for (Map.Entry<Employe, Map<Medium, Integer>> o : repartEmp.entrySet()) {
            System.out.println(o.getKey());
            for (Map.Entry<Medium, Integer> p : o.getValue().entrySet()) {
                System.out.println("\t" + p.getKey() + " -> " + p.getValue());
            }
        }        
    }
    
    /**
     * Tester avec une consultation pas encore noté et une deja noté
     */
    public static void testNoteHistorique() {
        Service s= new Service();
        
        Client client = s.authentifierClient("jean@mail", "jean");
        
        for (Consultation cons : client.getHistorique()) {
            System.out.println(cons);
        }
        
        Integer num= Saisie.lireInteger("Numéro de la consultation à noter : ");
        
        Consultation cons = s.findConsultation(new Long(num));
        
        boolean ok = s.noterConsultation(cons, 4);
        if (ok) {
            System.out.println("La consultation est noté !");
        }
        else {
            System.out.println("Ca n'a pas marché...");
        }
        
    }
    
    /**
     * Pour la faire échoué en drop and create on peut passer tous online a false dans service.initialiserEmploye
     */
    public static void testDemandeConsultation(){
        
        Service s= new Service();
        
        Client client = s.authentifierClient("jean@mail", "jean");
        
        List<Medium> tous= s.findAllMedium();
        for (Medium m : tous) {
            System.out.println(m);
        }
        
        Integer numMed= Saisie.lireInteger("Numéro du Médium de votre Choix : ");
        
        Medium m = s.findMedium(new Long(numMed));
        Boolean ok = s.demanderConsultation(client, m);
        if (ok){
            System.out.println("ok");
        }
        else{
            System.out.println("Pas dispo");
        }
    }
    
    public static void testConsultation() {
        Service s = new Service();
        
        Client client = s.authentifierClient("jean@mail", "jean");
        Medium m = s.findMedium(new Long(4));
        
        boolean ok = s.demanderConsultation(client, m);
        if (!ok) {
            System.out.println("pas ok");
        }
        
        Employe e = s.authentifierEmploye("sfavro@free.fr", "sfavro");
        
        Consultation c = e.obtenirConsultCurrent();
        
        s.commencerConsultation(c, e);
        
        
        List<String> predictions = s.obtenirPredictions(client, 2, 3, 1);
        System.out.println("~<[ Prédictions ]>~");
        System.out.println("[ Amour ] " + predictions.get(0));
        System.out.println("[ Santé ] " + predictions.get(1));
        System.out.println("[Travail] " + predictions.get(2));
        
        String commentaire = Saisie.lireChaine("Merci d'écrire un commentaire sur la consultation");
        s.terminerConsultation(c, e, commentaire);
    }
    
    public static void testInscriptionClientInteractif() {
        
        try {

            String nom = Saisie.lireChaine("Nom : ");
            String prenom = Saisie.lireChaine("Prenom : ");
            String dateNaissance = Saisie.lireChaine("Date de naissance : ");
            String genre = Saisie.lireChaine("Genre (H/F) : ");
            String tel = Saisie.lireChaine("Tel : ");
            String adressePostale = Saisie.lireChaine("Adresse Postale : ");
            String mail = Saisie.lireChaine("Mail : ");
            String mdp = Saisie.lireChaine("Mot de passe : ");
            
            boolean masculin = true;
            if (genre.equals("F")) {
                masculin = false;
            }
            
            Service s = new Service();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Client c = new Client(nom, prenom, sdf.parse(dateNaissance), masculin, tel, adressePostale, mail);
            c.setMotDePasse(mdp);
            
            s.inscriptionClient(c);
            
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
    
    
}
