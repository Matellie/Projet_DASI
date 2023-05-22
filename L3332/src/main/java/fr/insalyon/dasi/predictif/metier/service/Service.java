/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.metier.service;

import fr.insalyon.dasi.predictif.metier.objets.Medium;
import fr.insalyon.dasi.predictif.metier.objets.Astrologue;
import fr.insalyon.dasi.predictif.metier.objets.Spirite;
import fr.insalyon.dasi.predictif.metier.objets.Consultation;
import fr.insalyon.dasi.predictif.metier.objets.ProfilAstral;
import fr.insalyon.dasi.predictif.metier.objets.Cartomancien;
import fr.insalyon.dasi.predictif.metier.objets.Client;
import fr.insalyon.dasi.predictif.metier.objets.Employe;
import fr.insalyon.dasi.predictif.dao.ClientDao;
import fr.insalyon.dasi.predictif.dao.EmployeDao;
import fr.insalyon.dasi.predictif.dao.JpaUtil;
import fr.insalyon.dasi.predictif.util.Message;
import java.util.List;
import fr.insalyon.dasi.predictif.util.AstroNetApi;
import fr.insalyon.dasi.predictif.dao.ConsultationDao;
import fr.insalyon.dasi.predictif.dao.MediumDao;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jbondyfala
 */
public class Service {
    
    /*ATTRIBUTS*/
    
    //Ensemble des dao référant les objets métiers manipulés par cette classe Service
    private final ClientDao clientDao;
    private final EmployeDao empDao;
    private final MediumDao medDao;
    private final ConsultationDao consDao;
    
    /*CONSTRUCTEUR*/
    public Service() {
        clientDao = new ClientDao();
        medDao = new MediumDao();
        empDao = new EmployeDao();
        consDao = new ConsultationDao();
    }
    
    /*METHODES*/
    
    /**
     * Méthode utile uniquement pour la partie développement du projet
     * Créer une liste d'objet Employe et Medium pour les phases de test
     * Les fait persister
     */
    public void initialiserEmployesMedium() {

        Employe e1 = new Employe("FAVRO", "Samuel", true, "0642049305", "sfavro@free.fr");
    	e1.setMotDePasse("sfavro");
        e1.setNbConsult(45);
        e1.setOnline(true);
    	
    	Employe e2 = new Employe("DONODIO GALVIS", "Florine", false, "0671150503", "florine.donodio-galvis@gmail.com");
    	e2.setMotDePasse("florine.donodio-galvis");
        e2.setNbConsult(3);
        e2.setOnline(true);
    	
    	Employe e3 = new Employe("DEKEW", "Simon", true, "0713200950", "sdekew4845@yahoo.com");
    	e3.setMotDePasse("sdekew4845");
        e3.setNbConsult(56);
        e3.setOnline(true);
    	
    	Medium m1 = new Spirite("boule de cristal", "Gwenaelle",  "Specialiste des grandes conversations au-dela de TOUTES les frontières", false);
    	Medium m2 = new Cartomancien("Mme Irma",  "Comprenez votre entourage grâce à mes cartes ! résultats rapides.", false);
    	Medium m3 = new Spirite("Marc de café, boule de cristal, oreilles de lapin", "Professeur Tran", "votre avenir est devant vous: regardons-le ensemble", true);
        Medium m4 = new Astrologue("Institut des Nouveaux Savoirs Astrologiques", 2010, "Mr M", "votre avenir est devant vous: regardons-le ensemble", true);
        
        
        JpaUtil.creerContextePersistance();
        
        try {
            
            JpaUtil.ouvrirTransaction();
            
            empDao.create(e1);
            empDao.create(e2);
            empDao.create(e3);
            
            medDao.create(m3);
            medDao.create(m2);
            medDao.create(m1);
            medDao.create(m4);
            
            
            JpaUtil.validerTransaction();
            
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        JpaUtil.fermerContextePersistance();
        
    }

    /**
     * Fait appel au dao de Employe pour rechercher un objet persisté ayant pour mail et mdp les paramètres
     * Retourne une instance d'Employe si un tel objet existe sinon null
     * 
     * @param mail
     * @param mdp
     * @return Employe || null
     */
    public Employe authentifierEmploye(String mail, String mdp) {
        JpaUtil.creerContextePersistance();

        Employe e = null;
        try {
            
            e = empDao.authentifier(mail, mdp);
            
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        JpaUtil.fermerContextePersistance();
        
        return e;
    }
    
    /**
     * Fait appel à l'api AstroNet afin de créer un profil astral et l'associe au client
     * Persiste le client dans la base
     * Envoie un mail (avec la classe Message) confirmant l'inscription ou l'infirmant en cas de problème
     * @param client 
     */
    public void inscriptionClient(Client client) {

        
        JpaUtil.creerContextePersistance();
        
        try {
               
            AstroNetApi astroapi = new AstroNetApi();
            List<String> profil = astroapi.getProfil(client.getPrenom(), client.getDateNaissance());
            ProfilAstral profilAstral = new ProfilAstral(profil.get(0),profil.get(1),profil.get(2),profil.get(3));
            client.setProfilAstral(profilAstral);
            
            JpaUtil.ouvrirTransaction();
            
            clientDao.create(client);
            
            JpaUtil.validerTransaction();
            String objet= "Bonjour "+client.getPrenom()+", nous vous confirmons votre inscription au service PREDICT'IF. Rendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos mediums.";
            Message.envoyerMail("contact@predict.if", client.getMail(), "Bienvenue chez PREDICT'IF", objet);
            
        }catch (Exception ex) {
            String err="Bonjour "+client.getPrenom()+", votre inscription au service PREDICT'IF a malencontreusement échoué... Merci de recommencer ultérieurement.";
            Message.envoyerMail("contact@predict.if", client.getMail(), "Echec de l'inscription", err);
            ex.printStackTrace();
        }
        
        
        JpaUtil.fermerContextePersistance();
    }
    
    /**
     * Fait appel au dao de Client pour rechercher un objet persisté ayant pour mail et mdp les paramètres
     * Retourne une instance de Client si un tel objet existe sinon null
     * 
     * @param mail
     * @param mdp
     * @return Client || null
     */
    public Client authentifierClient(String mail, String mdp) {
        JpaUtil.creerContextePersistance();

        Client e = null;
        try {
            
            e = clientDao.authentifier(mail, mdp);
           
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        JpaUtil.fermerContextePersistance();
        
        return e;
    }
    
    /**
     * Ce service réalise une demande de consultation avec un medium faite par un client (d'où les 2 parametres)
     * Retourne true si la demande a pu parvenir au bout ou false sinon
     * 
     * Algorithme:
     * En premier, on recherche les employés disponibles et du meme genre que le medium pour pouvoir se faire passer pour lui auprès du client
     * Cette recherche se fait par un appel à une méthode du dao.
     * On obtient une liste d'Employe triée par ordre croissant du nombre de consultation déjà effectuée.
     * Si aucun employés n'est disponible, la liste sera vide et donc on met fin au service.
     * Sinon, on choisit donc le premier (demande du sujet).
     * On crée donc une instance de Consultation et on attribue cette instance à l'employé choisi.
     * On dit maintenant que l'employé n'est plus disponible tant qu'il n'a pas fait sa consultation.
     * Pour les statitistiques, on incrémente la popularite du medium
     * Il ne nous reste maintenant plus qu'à persister toutes ces modifications dans la base.
     * On peut alors envoyer une notification à l'employé pour qu'il aille faire sa consultation.
     * 
     * @param client
     * @param medium
     * @return true || false
     */
    public boolean demanderConsultation(Client client,Medium medium){
        JpaUtil.creerContextePersistance();

        boolean res;
        
        try{
            List<Employe> dispos=empDao.findOnline(medium.isMasculin());
            if (dispos.isEmpty()){
                res=false;
            }
            else{
                
                Employe emp=dispos.get(0);
             
                Consultation cons = new Consultation(client,medium);
                
                emp.addCons(cons);
                emp.setOnline(false);
                
                medium.incrPopularite();

                JpaUtil.ouvrirTransaction();
                
                consDao.create(cons);
                emp = empDao.update(emp);
                medium = medDao.update(medium);
                
                JpaUtil.validerTransaction();
                
                String sexe=client.isMasculin() ? "M":"Mme";
                Message.envoyerNotification(emp.getTelPro(),"Bonjour "+ emp.getPrenom()+". Consultation requise pour " + sexe + " " + client.getPrenom() + " " + client.getNom() + ". Médium à incarner : " + medium.getDenomination());
                res = true;
            }
           
        }
        catch(Exception ex){
            ex.printStackTrace();
            res = false;
        }
        
        JpaUtil.fermerContextePersistance();
        
        return res;
    }
    
    /**
     * Ce service sert à envoyer une notification au client lorsque l'employe (emp) se sent pret pour faire sa consultation (cons).
     * @param cons
     * @param emp 
     */
    public void commencerConsultation(Consultation cons, Employe emp) {
    	SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat heure = new SimpleDateFormat("HH:mm");
        
    	String message = "Bonjour " + cons.getClient().getPrenom() + ". J'ai bien raçu votre demande de consultation du " + date.format(cons.getDate_heure())
    	+ " à " + heure.format(cons.getDate_heure()) + ". Vous pouvez dès à présent me contacter au " + emp.getTelPro() + ". A tout de suite ! Médiumiquement vôtre, " + cons.getMedium().getDenomination();
    	Message.envoyerNotification(cons.getClient().getTel(), message);
    	
    }
    
    /**
     * Ce service sert à clore une consultation c'est-à-dire en lui attribuant un commentaire et en l'ajoutant à l'historique du client.
     * Ce service se charge aussi de rendre à nouveau l'employe disponible pour une nouvelle consultation
     * Toutes les modifications sont persistés dans la base.
     * 
     * @param cons
     * @param emp
     * @param commentaire 
     */
    public void terminerConsultation(Consultation cons, Employe emp, String commentaire) {
    	
    	JpaUtil.creerContextePersistance();
    	
    	try {
    		
            cons.setCommentaire(commentaire);

            Client c = cons.getClient();

            //Ajout de la consultation à l'historique du client 
            c.addCons(cons);

            JpaUtil.ouvrirTransaction();

            consDao.update(cons);
            clientDao.update(c);

            //Repassage de l'employe en ligne pour signifier qu'il est à nouveau dispo
            emp.setOnline(true);
            empDao.update(emp);

            JpaUtil.validerTransaction();
    		
    	}catch (Exception ex) {
            ex.printStackTrace();
    	}
    	
    	JpaUtil.fermerContextePersistance();
    	
    }
    
    /**
     * Attribue une note passé en paramètre à la consultation (cons) et fait persister cette maj dans la base puis renvoie true en cas de réussite
     * Si une note est déjà attribué, ne fait rien et renvoie false.
     * 
     * @param cons
     * @param note
     * @return true || false
     */
    public boolean noterConsultation(Consultation cons, int note) {
    	
    	JpaUtil.creerContextePersistance();
    	
    	boolean resultat = false;
    	
    	try {
    		
    		JpaUtil.ouvrirTransaction();
    		
    		if (cons.getNote() != null) {
                    JpaUtil.annulerTransaction();
                    resultat = false;
    		}
    		else {
                    cons.setNote(note);
                    consDao.update(cons);

                    JpaUtil.validerTransaction();

                    resultat = true;
    		}

    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	JpaUtil.fermerContextePersistance();
    	
    	return resultat;
    	
    }
    
    /**
     * 
     * Fait appel à l'api AstroNet pour obtenir des prédictions selon 3 notes en fonction du profil astral du client 
     * Si tout se passe bien, on obtient une liste contenant les 3 predictions.
     * Sinon cela retourne null
     * 
     * @param c
     * @param noteAmour
     * @param noteSante
     * @param noteTravail
     * @return une liste de string contenant les predictions || null
     */
    public List<String> obtenirPredictions(Client c, int noteAmour, int noteSante, int noteTravail) {
    	AstroNetApi astroApi = new AstroNetApi();
    	List<String> predictions = null;
        try {
                predictions = astroApi.getPredictions(c.getProfilAstral().getCouleur(), c.getProfilAstral().getAnimal(), noteAmour, noteSante, noteTravail);

        } catch (IOException e) {
                e.printStackTrace();
        }

        return predictions;
    }
    
    /**
     * Fournit la liste de tous les mediums
     * @return liste de medium 
     */
    public List<Medium> findAllMedium(){
        JpaUtil.creerContextePersistance();
        List<Medium> list = medDao.findAll();
        JpaUtil.fermerContextePersistance();
        
        return list;
    }
    
    /**
     * Cherche un medium dans la base par son id
     * @param id
     * @return Medium
     */
    public Medium findMedium(Long id) {
        JpaUtil.creerContextePersistance();
        Medium m = medDao.findById(id);
        JpaUtil.fermerContextePersistance();
        
        return m;
    }
    
    /**
     * Cherche une consultation par son id
     * @param id
     * @return Consultation
     */
    public Consultation findConsultation(Long id) {
        JpaUtil.creerContextePersistance();
        Consultation cons = consDao.findById(id);
        JpaUtil.fermerContextePersistance();
        
        return cons;
    }
    
    /**
     * Retourne le classement des (nombre) mediums les plus populaire
     * @param nombre
     * @return une liste de Medium
     */
    public List<Medium> findTopMedium(int nombre) {
        JpaUtil.creerContextePersistance();
        List<Medium> list = medDao.findAll();
        JpaUtil.fermerContextePersistance();
        
        List<Medium> res;
        
        if (nombre > list.size()) {
            res = list;
        }
        else {
            res = list.subList(0, nombre);
        }
        
        return res;
        
    }
    
    /**
     * Service utile pour faire des statistiques.
     * Il recupere tous les mediums de la base ainsi que le nombre total de consultation.
     * Pour chaque medium, il calcule le rapport popularite sur nombre total de consult. 
     * Cela construit ainsi une map ayant pour clé un medium et pour valeur la proportion de consultation qu'il a "fait".
     * 
     * @return 
     */
    public Map<Medium, Float> calculerRepartitionPopularite () {
        Map<Medium, Float> map = new HashMap<Medium, Float>();
        
        JpaUtil.creerContextePersistance();
        List<Medium> list = medDao.findAll();
        Long total = medDao.getNbTotalConsult();
        
        JpaUtil.fermerContextePersistance();
        
        Map<Medium, Float> res;
        
        if (total == 0) {
            res = null;
        }
        else {
            for (Medium m : list) {
                map.put(m, (float)m.getPopularite()/(float)total);
            }
            
            res = map;
        }
        
        return res;
        
    }
    
    /**
     * Service utile pour faire des statistiques.
     * Ce service sert à calculer pour chaque employe, combien de consultations ils ont fait en tant que tel medium.
     * 
     * Algorithme:
     * On commence par recuperer l'ensemble des employés.
     * Ensuite pour chaque employé, on l'ajoute en tant que clé dans une map et on lui attribut comme valeurs une seconde map
     * On parcourt son historique de consultation. 
     * Si c'est une consultation faite par un medium pas encore enregistré dans cette seconde map, on l'enregistre et on lui attribut la valeur 1.
     * Si au contraire il est déjà enregistré, on modifie seulement sa valeur en ajoutant à la précédente.
     * 
     * 
     * @return 
     */
    public Map<Employe, Map<Medium, Integer>> calculerRepartitionEmploye () {
        Map<Employe, Map<Medium, Integer>> map = new HashMap<>();
        
        JpaUtil.creerContextePersistance();
        List<Employe> list = empDao.findAll();
        
        
        JpaUtil.fermerContextePersistance();
        
      
        for (Employe e : list) {
            
            map.put(e, new HashMap<Medium, Integer>());
            List<Consultation> histo = e.getHistorique();
            
            for (Consultation cons : histo) {
                
                Map<Medium, Integer> temp = map.get(e);
                temp.merge(cons.getMedium(), 1, (oldValue, newValue) -> (oldValue != null) ? oldValue+1 : 1);
            }
            
        }
        
        return map;
        
    }

}
