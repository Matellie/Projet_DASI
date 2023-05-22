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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Cette classe Main contient tout pour simuler un fonctionnement quasi complet de PREDICT-IF.
 * Elle lance un menu console ou l'utilisateur peut faire tous les services du projet et simuler une utilisation courante mais sur une console au lieu d'un site internet.
 * 
 * @author jbondyfala
 */
public class MenuMain {
    
    /**
     * Pour aller plus vite et pas devoir les réinscrir à chaque fois
     */
    public static void inscriptionClientFactice() {
        try {
            Service s = new Service();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Client c = new Client("jean", "jean", new Date(), true, "0102030405", "adresse", "jean@mail");
            c.setMotDePasse("jean");
            
            s.inscriptionClient(c);
            
            Client c2 = new Client("toto", "toto", sdf.parse("2012/11/12"), true, "0102030405", "adresse", "toto@mail");
            c2.setMotDePasse("toto");
            
            s.inscriptionClient(c2);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        JpaUtil.creerFabriquePersistance();
        
        Service s = new Service();
        
        s.initialiserEmployesMedium();
        inscriptionClientFactice();
        
        System.out.println("Bienvenue sur predict'if");
        
        System.out.println("Voici nos mediums :");
        List<Medium> listMedium = s.findTopMedium(5);
        for (int i = 0; i < listMedium.size();i++) {
        	Medium m = listMedium.get(i);
        	System.out.println("\t" + i + " " + m.getDenomination());
        }
        
        List<Integer> valeurs = new ArrayList<>(2);
        valeurs.add(1);
        valeurs.add(2);
        valeurs.add(3);
        
        boolean ok = true;
        while (ok) {
        	int action = Saisie.lireInteger("Taper 1 pour vous connecter et 2 pour vous inscire et 3 pour quitter:", valeurs);
            
            switch(action) {
            case 1:
            	int choixEmpClient = Saisie.lireInteger("Taper 1 si vous êtes un client et 2 si vous êtes un employé", valeurs);
            	if (choixEmpClient == 1) {
	            	Client c = connexionClient(s);
	            	if (c != null) {
	            		System.out.println("bonjour " + c.getPrenom());
	            		
	            		//profil astral
	            		System.out.println("Voici votre profil astral : ");
	            		System.out.println("\t Animal totem : " + c.getProfilAstral().getAnimal());
	            		System.out.println("\t Signe chinois : " + c.getProfilAstral().getChinois());
	            		System.out.println("\t Signe du zodiaque : " + c.getProfilAstral().getZodiaque());
	            		System.out.println("\t Couleur : " + c.getProfilAstral().getCouleur());
	            		
	            		//Historique + note
	            		System.out.println("Voici l'historique des consultations :");
	            		for (Consultation cons : c.getHistorique()) {
	            			System.out.println("\t " + cons.getId() + "  " + cons);
	            		}
	            		int actionNote = Saisie.lireInteger("Si vous souhaitez noter une de ces consultations taper 1 sinon taper 2", valeurs);
	            		if (actionNote == 1) {
	            			int note = Saisie.lireInteger("Note : ");
	            			int numCons = Saisie.lireInteger("ID : ");
	            			s.noterConsultation(s.findConsultation(new Long(numCons)), note);
	            		}
	            		
	            		//Demande de consultation
	            		int demande = Saisie.lireInteger("Taper 1 si vous souhaitez faire une demande de consultation ou 2 sinon : ", valeurs);
	            		if (demande == 1) {
	            			List<Medium> listAllMedium = s.findAllMedium();
                                        for (int i = 0; i < listAllMedium.size();i++) {
                                                Medium m = listAllMedium.get(i);
                                                System.out.println("\t" + m.getId() + " " + m.getDenomination());
                                        }
                                        int numMed = Saisie.lireInteger("A partir de la liste des mediums donnez l'id de celui qui vous interesse : ");
	            			s.demanderConsultation(c, s.findMedium(new Long(numMed)));
	            		}
	            	}
	            	else {
	            		System.out.println("Erreur lors de la connexion");
	            	}
            	}
            	else {
            		Employe emp = connexionEmploye(s);
            		if (emp != null) {
            			System.out.println("bonjour " + emp.getPrenom());
            			
                                emp.getHistorique().size();
                                
                                System.out.println(emp.getHistorique());
                                
            			//Info client consult en cours
            			Consultation enCours = emp.obtenirConsultCurrent();
            			if (enCours != null) {
            				System.out.println("Vous avez une consultation en attente. Voici de quoi vous préparez");
            				
            				Client c = enCours.getClient();
            				
            				System.out.println("Voici le profil astral de " + c.getPrenom() + " : ");
                                        System.out.println("\t Animal totem : " + c.getProfilAstral().getAnimal());
                                        System.out.println("\t Signe chinois : " + c.getProfilAstral().getChinois());
                                        System.out.println("\t Signe du zodiaque : " + c.getProfilAstral().getZodiaque());
                                        System.out.println("\t Couleur : " + c.getProfilAstral().getCouleur());
    	            		
                                        //Debut de la consultation ?
                                        int debut = Saisie.lireInteger("Taper 1 si vous etes prêt à commencer, 2 sinon", valeurs);
                                        if (debut == 1) {

                                                s.commencerConsultation(enCours, emp);

                                                boolean fin = false;
                                                while (!fin) {
                                                        int aide = Saisie.lireInteger("Avez vous besoin d'aide ? 1 ou 2", valeurs);
                                                        if (aide == 1) {
                                                            int noteAmour = Saisie.lireInteger("Note de 1 à 4 sur l'amour : ");
                                                            int noteSante = Saisie.lireInteger("Note de 1 à 4 sur la sante : ");
                                                            int noteTravail = Saisie.lireInteger("Note de 1 à 4 sur le travail : ");

                                                            //Appel service
                                                            List<String> predictions = s.obtenirPredictions(c, noteAmour, noteSante, noteTravail);
                                                            System.out.println("~<[ Prédictions ]>~");
                                                            System.out.println("[ Amour ] " + predictions.get(0));
                                                            System.out.println("[ Santé ] " + predictions.get(1));
                                                            System.out.println("[Travail] " + predictions.get(2));

                                                        }

                                                        int fini = Saisie.lireInteger("Fini ? (1 ou 2)", valeurs);
                                                        if (fini == 1) {
                                                                fin = true;
                                                        }
                                                }

                                                String commentaire = Saisie.lireChaine("Merci d'écrire un commentaire sur la consultation");
                                                s.terminerConsultation(enCours, emp, commentaire);
                                        }
    	            		
                                }
                                
                                else {
                                    System.out.println("Vous n'avez pas de consultation en attente");
            			}
                                
    	            		//Historique
    	            		System.out.println("Voici l'historique des consultations :");
    	            		for (Consultation cons : emp.getHistorique()) {
    	            			System.out.println("\t " + cons.getId() + "  " + cons);
    	            		}
            			
                                int deco = 2;
                                while (deco != 1) {
                                    deco = Saisie.lireInteger("Vous êtes sur de partir ? 1 ou 2", valeurs);
                                }
            			//emp = s.deconnexionEmploye(emp);
            			
            		}
            		else {
            			System.out.println("Erreur lors de la connexion");
            		}
            	}
            	break;
            case 2:
            	inscrirClient(s);
            	break;
            case 3:
            	ok = false;
            	break;
            	
            }
        }
        
        
        
        
        JpaUtil.fermerFabriquePersistance();
        
    }

    
    public static void inscrirClient(Service s) {
    	
    	String nom = Saisie.lireChaine("Nom : ");
    	String prenom = Saisie.lireChaine("Prenom : ");
    	String dateNaissance = Saisie.lireChaine("Date de naissance : ");
    	boolean masculin = Saisie.lireInteger("1 pour Homme, 0 pour Femme : ")==1;
    	String tel = Saisie.lireChaine("Tel : ");
    	String adresse = Saisie.lireChaine("Adresse : ");
    	String mail = Saisie.lireChaine("Mail : ");
        String mdp = Saisie.lireChaine("MDP : ");
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Client c;
        try {
            c = new Client(nom, prenom, sdf.parse(dateNaissance), masculin, tel, adresse, mail);

            c.setMotDePasse(mdp);

            s.inscriptionClient(c);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    	
    	
    }
    
    public static Client connexionClient(Service s) {
    	String mail = Saisie.lireChaine("Mail : ");
    	String mdp = Saisie.lireChaine("Mdp : ");
    	
    	Client c = s.authentifierClient(mail, mdp);
    	
    	return c;
    	
    }
    
    public static Employe connexionEmploye(Service s) {
    	String mail = Saisie.lireChaine("Mail : ");
    	String mdp = Saisie.lireChaine("Mdp : ");
    	
    	Employe c = s.authentifierEmploye(mail, mdp);
    	
    	return c;
    	
    }
}
