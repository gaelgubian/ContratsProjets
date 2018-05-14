package fr.sully.boot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Projet.
 */
@Entity
@Table(name = "projet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "nom_court", nullable = false)
    private String nomCourt;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "projet")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NumeroAffaire> numerosAffaires = new HashSet<>();

    @OneToMany(mappedBy = "projet")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Application> applications = new HashSet<>();

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

    public Projet nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCourt() {
        return nomCourt;
    }

    public Projet nomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
        return this;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public byte[] getImage() {
        return image;
    }

    public Projet image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Projet imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Client getClient() {
        return client;
    }

    public Projet client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<NumeroAffaire> getNumerosAffaires() {
        return numerosAffaires;
    }

    public Projet numerosAffaires(Set<NumeroAffaire> numeroAffaires) {
        this.numerosAffaires = numeroAffaires;
        return this;
    }

    public Projet addNumerosAffaires(NumeroAffaire numeroAffaire) {
        this.numerosAffaires.add(numeroAffaire);
        numeroAffaire.setProjet(this);
        return this;
    }

    public Projet removeNumerosAffaires(NumeroAffaire numeroAffaire) {
        this.numerosAffaires.remove(numeroAffaire);
        numeroAffaire.setProjet(null);
        return this;
    }

    public void setNumerosAffaires(Set<NumeroAffaire> numeroAffaires) {
        this.numerosAffaires = numeroAffaires;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public Projet applications(Set<Application> applications) {
        this.applications = applications;
        return this;
    }

    public Projet addApplications(Application application) {
        this.applications.add(application);
        application.setProjet(this);
        return this;
    }

    public Projet removeApplications(Application application) {
        this.applications.remove(application);
        application.setProjet(null);
        return this;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
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
        Projet projet = (Projet) o;
        if (projet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
