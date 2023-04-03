/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import metier.modele.Eleve;
import dao.EleveDao;
import dao.EtablissementDao;
import dao.JpaUtil;
import java.io.IOException;
import java.util.List;
import metier.modele.Etablissement;
import util.EducNetApi;
import util.Message;

/**
 *
 * @author etarassov
 */
public class Service {

    public Service() {
    }
    
    public void inscriptionEleve(Eleve eleve, String codeEtablissement) throws IOException {
        Message message = new Message();
        EleveDao eleveDao = new EleveDao();
        EtablissementDao etabDao = new EtablissementDao();
        EducNetApi api = new EducNetApi();
        
        String mailExpediteur = "Systeme";
        String mailDestinataire = eleve.getMail();
        boolean etabDoitEtreCree = false;
        
        JpaUtil.creerContextePersistance();
        
        Etablissement etab = etabDao.getEtablissement(codeEtablissement);
        
        if(etab != null){
            eleve.setEtablissement(etab);
        }else{
            List<String> result = api.getInformationCollege(codeEtablissement);
            // List<String> result = api.getInformationLycee("0690132U");
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
            }
            else {
                System.out.println("Etablissement inconnu");
            }  
        }
        
        if(etab != null){
            try {
                JpaUtil.ouvrirTransaction();
                eleveDao.create(eleve);
                if(etabDoitEtreCree){
                    etabDao.create(etab);
                }
                JpaUtil.validerTransaction();

                message.envoyerMail(mailExpediteur, mailDestinataire, "Confirmation inscription", "Vous etes bien inscrit !");
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JpaUtil.annulerTransaction();

                message.envoyerMail(mailExpediteur, mailDestinataire, "Infirmation inscription", "Oups, une erreur s est produite !");
            }
            finally {
                JpaUtil.fermerContextePersistance();
            }
        }
    }
}
