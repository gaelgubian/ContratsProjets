package fr.sully.boot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import fr.sully.boot.domain.enumeration.CategorieFonctionnelle;

import fr.sully.boot.domain.enumeration.CategorieRGPD;

import fr.sully.boot.domain.enumeration.Source;

import fr.sully.boot.domain.enumeration.Destinataire;

import fr.sully.boot.domain.enumeration.NatureTransmissionPaysTiers;

/**
 * A DonneePersonnelle.
 */
@Entity
@Table(name = "donnee_personnelle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DonneePersonnelle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie_fonctionnelle")
    private CategorieFonctionnelle categorieFonctionnelle;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie_rgpd")
    private CategorieRGPD categorieRGPD;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private Source source;

    @Column(name = "vulnerable")
    private Boolean vulnerable;

    @Enumerated(EnumType.STRING)
    @Column(name = "destinataire")
    private Destinataire destinataire;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature_transmission_pays_tiers")
    private NatureTransmissionPaysTiers natureTransmissionPaysTiers;

    @NotNull
    @Column(name = "delai_conservation", nullable = false)
    private String delaiConservation;

    @Column(name = "echange")
    private String echange;

    @ManyToOne
    private Traitement traitement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public DonneePersonnelle nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public CategorieFonctionnelle getCategorieFonctionnelle() {
        return categorieFonctionnelle;
    }

    public DonneePersonnelle categorieFonctionnelle(CategorieFonctionnelle categorieFonctionnelle) {
        this.categorieFonctionnelle = categorieFonctionnelle;
        return this;
    }

    public void setCategorieFonctionnelle(CategorieFonctionnelle categorieFonctionnelle) {
        this.categorieFonctionnelle = categorieFonctionnelle;
    }

    public CategorieRGPD getCategorieRGPD() {
        return categorieRGPD;
    }

    public DonneePersonnelle categorieRGPD(CategorieRGPD categorieRGPD) {
        this.categorieRGPD = categorieRGPD;
        return this;
    }

    public void setCategorieRGPD(CategorieRGPD categorieRGPD) {
        this.categorieRGPD = categorieRGPD;
    }

    public Source getSource() {
        return source;
    }

    public DonneePersonnelle source(Source source) {
        this.source = source;
        return this;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Boolean isVulnerable() {
        return vulnerable;
    }

    public DonneePersonnelle vulnerable(Boolean vulnerable) {
        this.vulnerable = vulnerable;
        return this;
    }

    public void setVulnerable(Boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public Destinataire getDestinataire() {
        return destinataire;
    }

    public DonneePersonnelle destinataire(Destinataire destinataire) {
        this.destinataire = destinataire;
        return this;
    }

    public void setDestinataire(Destinataire destinataire) {
        this.destinataire = destinataire;
    }

    public NatureTransmissionPaysTiers getNatureTransmissionPaysTiers() {
        return natureTransmissionPaysTiers;
    }

    public DonneePersonnelle natureTransmissionPaysTiers(NatureTransmissionPaysTiers natureTransmissionPaysTiers) {
        this.natureTransmissionPaysTiers = natureTransmissionPaysTiers;
        return this;
    }

    public void setNatureTransmissionPaysTiers(NatureTransmissionPaysTiers natureTransmissionPaysTiers) {
        this.natureTransmissionPaysTiers = natureTransmissionPaysTiers;
    }

    public String getDelaiConservation() {
        return delaiConservation;
    }

    public DonneePersonnelle delaiConservation(String delaiConservation) {
        this.delaiConservation = delaiConservation;
        return this;
    }

    public void setDelaiConservation(String delaiConservation) {
        this.delaiConservation = delaiConservation;
    }

    public String getEchange() {
        return echange;
    }

    public DonneePersonnelle echange(String echange) {
        this.echange = echange;
        return this;
    }

    public void setEchange(String echange) {
        this.echange = echange;
    }

    public Traitement getTraitement() {
        return traitement;
    }

    public DonneePersonnelle traitement(Traitement traitement) {
        this.traitement = traitement;
        return this;
    }

    public void setTraitement(Traitement traitement) {
        this.traitement = traitement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DonneePersonnelle donneePersonnelle = (DonneePersonnelle) o;
        if (donneePersonnelle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), donneePersonnelle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DonneePersonnelle{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", categorieFonctionnelle='" + getCategorieFonctionnelle() + "'" +
            ", categorieRGPD='" + getCategorieRGPD() + "'" +
            ", source='" + getSource() + "'" +
            ", vulnerable='" + isVulnerable() + "'" +
            ", destinataire='" + getDestinataire() + "'" +
            ", natureTransmissionPaysTiers='" + getNatureTransmissionPaysTiers() + "'" +
            ", delaiConservation='" + getDelaiConservation() + "'" +
            ", echange='" + getEchange() + "'" +
            "}";
    }
}
