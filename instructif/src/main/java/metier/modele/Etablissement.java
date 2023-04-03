/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.logging.Logger;
import javax.persistence.*;

/**
 *
 * @author mhabran
 */
@Entity
public class Etablissement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uai;
    private String nom;
    private String secteur;
    private String codeCommune;
    private String nomCommune;
    private String codeDepartement;
    private String nomDepartement;
    private String academie;
    private String ips;

    public Etablissement() {
    }

    public Etablissement(String uai, String nom, String secteur, String codeCommune, String nomCommune, String codeDepartement, String nomDepartement, String academie, String ips) {
        this.uai = uai;
        this.nom = nom;
        this.secteur = secteur;
        this.codeCommune = codeCommune;
        this.nomCommune = nomCommune;
        this.codeDepartement = codeDepartement;
        this.nomDepartement = nomDepartement;
        this.academie = academie;
        this.ips = ips;
    }

    public Long getId() {
        return id;
    }

    public String getUai() {
        return uai;
    }

    public String getNom() {
        return nom;
    }

    public String getSecteur() {
        return secteur;
    }

    public String getCodeCommune() {
        return codeCommune;
    }

    public String getNomCommune() {
        return nomCommune;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public String getNomDepartement() {
        return nomDepartement;
    }

    public String getAcademie() {
        return academie;
    }

    public String getIps() {
        return ips;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUai(String uai) {
        this.uai = uai;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public void setNomDepartement(String nomDepartement) {
        this.nomDepartement = nomDepartement;
    }

    public void setAcademie(String academie) {
        this.academie = academie;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    @Override
    public String toString() {
        return "Etablissement{" + "id=" + id + ", uai=" + uai + ", nom=" + nom + ", secteur=" + secteur + ", codeCommune=" + codeCommune + ", nomCommune=" + nomCommune + ", codeDepartement=" + codeDepartement + ", nomDepartement=" + nomDepartement + ", academie=" + academie + ", ips=" + ips + '}';
    }

}
