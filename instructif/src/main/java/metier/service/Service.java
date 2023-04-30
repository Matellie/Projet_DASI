/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import metier.modele.Eleve;
import metier.modele.Autre;
import metier.modele.Enseignant;
import metier.modele.Etablissement;
import metier.modele.Etudiant;
import metier.modele.Intervenant;
import metier.modele.Intervention;
import metier.modele.Niveau;
import dao.EleveDao;
import dao.EtablissementDao;
import dao.IntervenantDao;
import dao.JpaUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.EducNetApi;
import util.Message;

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

        String mailExpediteur = "Systeme";
        String mailDestinataire = eleve.getMail();
        boolean etabDoitEtreCree = false;

        JpaUtil.creerContextePersistance();

        Etablissement etab = etabDao.getEtablissement(codeEtablissement);

        if (etab != null) {
            eleve.setEtablissement(etab);
        } else {
            List<String> result = null;

            if (Niveau.SIXIEME == eleve.getNiveau() || Niveau.CINQUIEME == eleve.getNiveau()
                    || Niveau.QUATRIEME == eleve.getNiveau() || eleve.getNiveau() == Niveau.TROISIEME) {
                try {
                    result = api.getInformationCollege(codeEtablissement);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    result = api.getInformationLycee(codeEtablissement);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
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
                System.out.println("Etablissement " + uai + ": " + nom + " à " + nomCommune + ", " + nomDepartement);

                etab = new Etablissement(uai, nom, secteur, codeCommune, nomCommune, codeDepartement, nomDepartement, academie, ips);
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

                message.envoyerMail(mailExpediteur, mailDestinataire, "Confirmation inscription", "Vous etes bien inscrit !");
            } catch (Exception ex) {
                ex.printStackTrace();
                JpaUtil.annulerTransaction();

                message.envoyerMail(mailExpediteur, mailDestinataire, "Infirmation inscription", "Oups, une erreur s est produite !");
            }
        }
        JpaUtil.fermerContextePersistance();
    }
    
    public Eleve connexionEleve(String login, String motDePasse) {
        EleveDao eleveDao = new EleveDao();
        Eleve auth = null;
        
        try {
            JpaUtil.creerContextePersistance();
            
            auth = eleveDao.authenticate(login, motDePasse);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return auth;
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
         
        tom = new Etudiant("INSA", "maths", "Green", "Tom", "t.g@insa.fr", "zerhb", "06458596", Niveau.QUATRIEME, Niveau.TERMINALE);
        tim = new Etudiant("IUT", "info", "Green", "Tim", "t.g@iut.fr", "polkjh", "064584123", Niveau.PREMIERE, Niveau.TERMINALE);
        marie = new Etudiant("Lyon1", "physique", "Brown", "Marie", "m.b@Lyon1.fr", "tgbtrf", "05457287", Niveau.TERMINALE, Niveau.PREMIERE);
        leila = new Enseignant("Universite", "Blue", "Leila", "l.b@insa.fr", "ghdsf", "064553278", Niveau.SIXIEME, Niveau.TERMINALE);
        blum = new Enseignant("Lycee", "Red", "Blum", "l.b@lycee.fr", "vfdsqdfv", "07412258", Niveau.PREMIERE, Niveau.PREMIERE);
        potato = new Enseignant("College", "Golden", "Potato", "p.g@college.fr", "thrmùp", "078896321", Niveau.SIXIEME, Niveau.CINQUIEME);
        patrik = new Autre("Mecanicien", "Fire", "Patrick", "p.f@meca.fr", "thrmùp", "078896321", Niveau.SIXIEME, Niveau.TROISIEME);
        
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
    
    public Long connexionIntervenant(String login, String motDePasse) {
        IntervenantDao intervenantDao = new IntervenantDao();
        Long auth = null;
        
        try {
            JpaUtil.creerContextePersistance();
            
            auth = intervenantDao.authenticate(login, motDePasse);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return auth;
    }
    
    public Long TrouverIntervenant(Eleve eleve){
        
        Intervenant monIntervenant = new Intervenant();
        IntervenantDao intervenantDao = new IntervenantDao();
        
        JpaUtil.creerContextePersistance();
        
        List<Intervenant> intervenants = intervenantDao.listeOrdonneeIntervenantsDisponibles(eleve.getNiveau());
        System.out.println(intervenants);
        
        for(Intervenant intr : intervenants){
            try {
                JpaUtil.ouvrirTransaction();
                
                intr.setAvailable(0);
                intr.incrementNbIntervention();
                intr = intervenantDao.update(intr);
                
                JpaUtil.validerTransaction();
                
                monIntervenant = intr;
                break;
            } 
            catch (Exception ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
                JpaUtil.annulerTransaction();
            }
        }
        JpaUtil.fermerContextePersistance();
        
        return monIntervenant.getId();
    } 
}
