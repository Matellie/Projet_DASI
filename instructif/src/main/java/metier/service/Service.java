/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import metier.modele.*;
import dao.*;
import util.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author etarassov
 */
public class Service {

    public Service() {
    }

    public void inscriptionEleve(Eleve eleve, String codeEtablissement) {
        Message message = new Message();
        EleveDao eleveDao = new EleveDao();
        EtablissementDao etabDao = new EtablissementDao();
        EducNetApi api = new EducNetApi();
        GeoNetApi apiGeo = new GeoNetApi();

        String mailExpediteur = "Systeme";
        String mailDestinataire = eleve.getMail();
        boolean etabDoitEtreCree = false;

        JpaUtil.creerContextePersistance();

        Etablissement etab = etabDao.getEtablissement(codeEtablissement);

        if (etab != null) {
            eleve.setEtablissement(etab);
        } else {
            List<String> result = null;
            LatLng localisation = null;

            if (Niveau.SIXIEME == eleve.getNiveau() || Niveau.CINQUIEME == eleve.getNiveau()
                    || Niveau.QUATRIEME == eleve.getNiveau() || eleve.getNiveau() == Niveau.TROISIEME) {
                try {
                    result = api.getInformationCollege(codeEtablissement);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("L'établissement demandé n'a pas pu être trouvé !");
                }
            } else {
                try {
                    result = api.getInformationLycee(codeEtablissement);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("L'établissement demandé n'a pas pu être trouvé !");
                }
            }

            if (result != null) {
                String uai = result.get(0);
                String nom = result.get(1);
                String secteur = result.get(2);
                String codeCommune = result.get(3);
                String nomCommune = result.get(4);
                String codeDepartement = result.get(5);
                String nomDepartement = result.get(6);
                String academie = result.get(7);
                String ips = result.get(8);
                localisation = apiGeo.getLatLng(nom + ", " + nomCommune);
                double lat = localisation.lat;
                double lon = localisation.lng;
                System.out.println("Etablissement " + uai + ": " + nom + " à " + nomCommune + ", " + nomDepartement);

                etab = new Etablissement(uai, nom, secteur, codeCommune, nomCommune, codeDepartement, nomDepartement, academie, ips, lat, lon);
                etabDoitEtreCree = true;
            } else {
                System.out.println("Etablissement inconnu");
            }
        }

        if (etab != null) {
            try {
                JpaUtil.ouvrirTransaction();

                eleve.setEtablissement(etab);
                eleveDao.create(eleve);
                if (etabDoitEtreCree) {
                    etabDao.create(etab);
                }

                JpaUtil.validerTransaction();

                message.envoyerMail(mailExpediteur, mailDestinataire, "Bienvenue"
                    + " sur le réseau INSTRUCT'IF", "Bonjour " + eleve.getPrenom()
                    + ", nous te confirmons ton inscription sur le réseau INSTRUCT'IF. "
                    + "Si tu as besoin de soutien pour tes leçons ou tes devoirs, "
                    + "rends-toi sur notre site pour une mise en relation avec un intervenant.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JpaUtil.annulerTransaction();

                message.envoyerMail(mailExpediteur, mailDestinataire, "Echec de "
                    + "l'inscription sur le réseau INSTRUCT'IF", "Bonjour " + eleve.getPrenom() + 
                    ", ton inscription sur sur le réseau INSTRUCT'IF a malencontreusement échoué"
                    + "... Merci de recommencer ultérieurement.");
            }
        } else {
            message.envoyerMail(mailExpediteur, mailDestinataire, "Echec de "
                    + "l'inscription sur le réseau INSTRUCT'IF", "Bonjour " + eleve.getPrenom() + 
                    ", ton inscription sur sur le réseau INSTRUCT'IF a malencontreusement échoué"
                    + "... Merci de recommencer ultérieurement.");
        }
        JpaUtil.fermerContextePersistance();
    }

    public static void initialiserIntervenants() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        Etudiant tom;
        Etudiant tim;
        Etudiant marie;

        Enseignant leila;
        Enseignant blum;
        Enseignant potato;

        Autre patrik = null;

        tom = new Etudiant("INSA", "maths", "Green", "Tom", "tom.green@insa.fr", "zerhb", "06458596", Niveau.QUATRIEME, Niveau.TERMINALE);
        tim = new Etudiant("IUT", "info", "Green", "Tim", "tim.green@iut.fr", "polkjh", "064584123", Niveau.PREMIERE, Niveau.TERMINALE);
        marie = new Etudiant("Lyon1", "physique", "Brown", "Marie", "marie.brown@Lyon1.fr", "tgbtrf", "05457287", Niveau.TERMINALE, Niveau.PREMIERE);
        leila = new Enseignant("Universite", "Blue", "Leila", "leila.blue@insa.fr", "ghdsf", "064553278", Niveau.SIXIEME, Niveau.TERMINALE);
        blum = new Enseignant("Lycee", "Red", "Blum", "blum.red@lycee.fr", "vfdsqdfv", "07412258", Niveau.PREMIERE, Niveau.PREMIERE);
        potato = new Enseignant("College", "Golden", "Potato", "potato.golden@college.fr", "thrmùp", "078896321", Niveau.SIXIEME, Niveau.CINQUIEME);
        patrik = new Autre("Mecanicien", "Fire", "Patrick", "patrick.fire@meca.fr", "thrmùp", "078896321", Niveau.SIXIEME, Niveau.TROISIEME);

        IntervenantDao intervenantDao = new IntervenantDao();

        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();

            intervenantDao.create(tom);
            intervenantDao.create(tim);
            intervenantDao.create(marie);
            intervenantDao.create(leila);
            intervenantDao.create(blum);
            intervenantDao.create(potato);
            intervenantDao.create(patrik);

            JpaUtil.validerTransaction();

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerContextePersistance();

    }

    public static void initialiserMatieres() {
        Matiere francais = new Matiere("Francais");
        Matiere philosophie = new Matiere("Philosophie");
        Matiere mathematiques = new Matiere("Mathematiques");
        Matiere physiqueChimie = new Matiere("Physique-Chimie");
        Matiere histoireGeographie = new Matiere("Histoire-Geographie");
        Matiere eps = new Matiere("EPS");
        Matiere anglais = new Matiere("Anglais");
        Matiere allemand = new Matiere("Allemand");
        Matiere espagnol = new Matiere("Espagnol");
        Matiere technologie = new Matiere("Technologie");
        Matiere artsPlasiques = new Matiere("Arts-Plasiques");

        MatiereDao matiereDao = new MatiereDao();

        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();

            matiereDao.create(francais);
            matiereDao.create(philosophie);
            matiereDao.create(mathematiques);
            matiereDao.create(physiqueChimie);
            matiereDao.create(histoireGeographie);
            matiereDao.create(eps);
            matiereDao.create(anglais);
            matiereDao.create(allemand);
            matiereDao.create(espagnol);
            matiereDao.create(technologie);
            matiereDao.create(artsPlasiques);

            JpaUtil.validerTransaction();

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerContextePersistance();

    }

    public Eleve connexionEleve(String login, String motDePasse) {
        EleveDao eleveDao = new EleveDao();
        Eleve auth = null;

        try {
            JpaUtil.creerContextePersistance();

            auth = eleveDao.authenticate(login, motDePasse);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return auth;
    }

    public Long connexionIntervenant(String login, String motDePasse) {
        IntervenantDao intervenantDao = new IntervenantDao();
        Long auth = null;

        try {
            JpaUtil.creerContextePersistance();

            auth = intervenantDao.authenticate(login, motDePasse);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return auth;
    }

    public Long trouverIntervenant(Eleve eleve) {

        Intervenant monIntervenant = new Intervenant();
        IntervenantDao intervenantDao = new IntervenantDao();

        JpaUtil.creerContextePersistance();

        List<Intervenant> intervenants = intervenantDao.listeOrdonneeIntervenantsDisponibles(eleve.getNiveau());

        for (Intervenant intr : intervenants) {
            try {
                JpaUtil.ouvrirTransaction();

                intr.setAvailable(false);
                intr.incrementNbIntervention();
                intr = intervenantDao.update(intr);

                JpaUtil.validerTransaction();

                monIntervenant = intr;
                break;
            } catch (Exception ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
                JpaUtil.annulerTransaction();
            }
        }

        JpaUtil.fermerContextePersistance();

        return monIntervenant.getId();
    }

    public Long faireDemandeIntervention(Eleve eleve, Long idIntervenant, String nomMatiere, String description) {
        Message message = new Message();
        IntervenantDao intervenantDao = new IntervenantDao();
        MatiereDao matiereDao = new MatiereDao();
        InterventionDao interventionDao = new InterventionDao();

        JpaUtil.creerContextePersistance();

        Intervenant intervenant = intervenantDao.findById(idIntervenant);
        Matiere matiere = matiereDao.findByName(nomMatiere);
        Intervention intervention = new Intervention(eleve, intervenant, new Date(), matiere, description);

        try {
            JpaUtil.ouvrirTransaction();

            interventionDao.create(intervention);

            JpaUtil.validerTransaction();

            String messageNotif = "Bonjour " + intervenant.getPrenom() + ". Merci de prendre en charge la demande de soutien en \" " + intervention.getMatiere()
                    + " \" demandée à " + intervention.getDateDemande() + " par " + eleve.getPrenom() + " en classe de " + eleve.getNiveau() + ".";
            message.envoyerNotification(intervenant.getNumTel(), messageNotif);

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        }

        JpaUtil.fermerContextePersistance();

        return intervention.getId();
    }
    
    public List<Matiere> getAllMatieres() {
        MatiereDao matiereDao = new MatiereDao();
        
        JpaUtil.creerContextePersistance();
        
        List<Matiere> matieres = matiereDao.getAllMatieres();
                
        JpaUtil.fermerContextePersistance();
        
        return matieres;
    }
    
    public Intervenant getIntervenantById(Long idIntervenant) {
        IntervenantDao intervenantDao = new IntervenantDao();
        
        JpaUtil.creerContextePersistance();
        
        Intervenant intervenant = intervenantDao.findById(idIntervenant);
                
        JpaUtil.fermerContextePersistance();
        
        return intervenant;
    }

    public Intervention consulterInformationsIntervention(Long idIntervenant) {
        Intervention intervention = null;
        IntervenantDao intervenantDao = new IntervenantDao();
        InterventionDao interventionDao = new InterventionDao();

        JpaUtil.creerContextePersistance();

        Intervenant intervenant = intervenantDao.findById(idIntervenant);
        List<Intervention> interventions = interventionDao.findByIntervenant(intervenant);

        if (interventions.get(0).getDateDebut() == null) {
            intervention = interventions.get(0);
        } else {
            System.out.println("Vous n avez pas de demandes d'intervention en cours !");
        }

        JpaUtil.fermerContextePersistance();

        return intervention;
    }

    public void creationVisio(Intervention intervention) {
        InterventionDao interventionDao = new InterventionDao();

        JpaUtil.creerContextePersistance();

        try {
            JpaUtil.ouvrirTransaction();

            intervention.setDateDebut(new Date());
            interventionDao.update(intervention);

            JpaUtil.validerTransaction();

            System.out.println("---- La visio a été lancée ! ----");

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        }

        JpaUtil.fermerContextePersistance();
    }

    public void arretVisio(Long idIntervention) {
        InterventionDao interventionDao = new InterventionDao();
        IntervenantDao intervenantDao = new IntervenantDao();

        JpaUtil.creerContextePersistance();

        Intervention intervention = interventionDao.findById(idIntervention);

        try {
            JpaUtil.ouvrirTransaction();

            Date dateArret = new Date();

            Instant instantDebut = intervention.getDateDebut().toInstant();
            Instant instantArret = dateArret.toInstant();
            Duration duree = Duration.between(instantDebut, instantArret);
            intervention.setDureeVisio(duree);
            
            intervention.getIntervenant().setAvailable(true);
            intervenantDao.update(intervention.getIntervenant());

            interventionDao.update(intervention);

            JpaUtil.validerTransaction();

            System.out.println("---- La visio a été arrêtée ! ----");

            long hours = duree.toHours();
            long minutes = duree.toMinutes() % 60;
            long seconds = duree.getSeconds() % 60;
            System.out.println("---- Elle a durée: " + hours + "h" + minutes + "m" + seconds + "s" + " ----");
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        }

        JpaUtil.fermerContextePersistance();
    }

    public void autoEvaluation(Long idIntervention, int note) {
        InterventionDao interventionDao = new InterventionDao();

        JpaUtil.creerContextePersistance();

        Intervention intervention = interventionDao.findById(idIntervention);

        try {
            JpaUtil.ouvrirTransaction();

            intervention.setAutoEvaluation(note);
            interventionDao.update(intervention);

            JpaUtil.validerTransaction();

            System.out.println("---- Vous avez mis une note de " + intervention.getAutoEvaluation() + " ----");
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        }

        JpaUtil.fermerContextePersistance();
    }

    public List<Intervention> historiqueInterventionIntervenant(Long idIntervenant) {
        IntervenantDao intervenantDao = new IntervenantDao();
        InterventionDao interventionDao = new InterventionDao();

        JpaUtil.creerContextePersistance();

        Intervenant intervenant = intervenantDao.findById(idIntervenant);
        List<Intervention> interventions = interventionDao.findByIntervenant(intervenant);

        JpaUtil.fermerContextePersistance();

        return interventions;
    }
    
    public List<Intervention> historiqueInterventionEleve(Eleve eleve) {
        InterventionDao interventionDao = new InterventionDao();

        JpaUtil.creerContextePersistance();

        List<Intervention> interventions = interventionDao.findByEleve(eleve);

        JpaUtil.fermerContextePersistance();

        return interventions;
    }

    // Statistiques Instructif
    public double getIPSMoyen() {
        EtablissementDao etablissementDao = new EtablissementDao();

        JpaUtil.creerContextePersistance();
        List<String> allIPS = etablissementDao.getAllIPS();
        JpaUtil.fermerContextePersistance();

        double sommeIPS = 0.0;

        for (String ips : allIPS) {
            sommeIPS += Double.parseDouble(ips);
        }
        double ipsMoyen = sommeIPS / allIPS.size();

        return ipsMoyen;
    }

    public Map<Matiere, Long> nbInterventionsParMatiere() {
        MatiereDao matiereDao = new MatiereDao();
        InterventionDao interventionDao = new InterventionDao();
        Map<Matiere, Long> map = new HashMap();

        JpaUtil.creerContextePersistance();
        List<Intervention> interventions = interventionDao.getAllIntervention();

        for (Intervention intervention : interventions) {
            // Pour chaque intervention ajouter 1 à la matiere correspondante
            map.merge(intervention.getMatiere(), new Long(1), Long::sum);
        }
        JpaUtil.fermerContextePersistance();

        return map;
    }

    public Map<Niveau, Long> nbInterventionsParNiveau() {
        InterventionDao interventionDao = new InterventionDao();
        Map<Niveau, Long> map = new HashMap();

        JpaUtil.creerContextePersistance();
        List<Intervention> interventions = interventionDao.getAllIntervention();

        for (Intervention intervention : interventions) {
            // Pour chaque intervention ajouter 1 au niveau correspondant
            map.merge(intervention.getEleve().getNiveau(), new Long(1), Long::sum);
        }
        JpaUtil.fermerContextePersistance();

        return map;
    }

    public Map<String, Long> nbInterventionsParAcademie() {
        InterventionDao interventionDao = new InterventionDao();
        Map<String, Long> map = new HashMap();

        JpaUtil.creerContextePersistance();
        List<Intervention> interventions = interventionDao.getAllIntervention();

        for (Intervention intervention : interventions) {
            // Pour chaque intervention ajouter 1 à l academie correspondante
            map.merge(intervention.getEleve().getEtablissement().getAcademie(), new Long(1), Long::sum);
        }
        JpaUtil.fermerContextePersistance();

        return map;
    }

    public Map<String, Long> nbInterventionsParDepartement() {
        InterventionDao interventionDao = new InterventionDao();
        Map<String, Long> map = new HashMap();

        JpaUtil.creerContextePersistance();
        List<Intervention> interventions = interventionDao.getAllIntervention();

        for (Intervention intervention : interventions) {
            // Pour chaque intervention ajouter 1 à l academie correspondante
            map.merge(intervention.getEleve().getEtablissement().getNomDepartement(), new Long(1), Long::sum);
        }
        JpaUtil.fermerContextePersistance();

        return map;
    }
    
    public List<Etablissement> getAllEtablissements() {
        EtablissementDao etablissementDao = new EtablissementDao();
        
        JpaUtil.creerContextePersistance();
        List<Etablissement> allEtablissements = etablissementDao.getAllEtablissements();
        JpaUtil.fermerContextePersistance();
        
        return allEtablissements;
    }
}
