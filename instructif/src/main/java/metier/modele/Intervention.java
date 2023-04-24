/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.time.Duration;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author etarassov
 */
public class Intervention {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Eleve eleve;
    @ManyToOne
    private Intervenant intervenant;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDemande;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDebut;
    private Duration dureeVisio;
    private int autoEvaluation;
    private String description;

    public Intervention() {
    }

    public Intervention(Date dateDemande, Date dateDebut, Duration dureeVisio, int autoEvaluation, String description) {
        this.dateDemande = dateDemande;
        this.dateDebut = dateDebut;
        this.dureeVisio = dureeVisio;
        this.autoEvaluation = autoEvaluation;
        this.description = description;
    }

    public Intervention(Eleve eleve, Date dateDemande, String description) {
        this.eleve = eleve;
        this.dateDemande = dateDemande;
        this.description = description;
    }
    
    

    public Long getId() {
        return id;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Duration getDureeVisio() {
        return dureeVisio;
    }

    public int getAutoEvaluation() {
        return autoEvaluation;
    }

    public String getDescription() {
        return description;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDureeVisio(Duration dureeVisio) {
        this.dureeVisio = dureeVisio;
    }

    public void setAutoEvaluation(int autoEvaluation) {
        this.autoEvaluation = autoEvaluation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Intervention{" + "id=" + id + ", dateDemande=" + dateDemande + ", dateDebut=" + dateDebut + ", dureeVisio=" + dureeVisio + ", autoEvaluation=" + autoEvaluation + ", description=" + description + '}';
    }
    
}
