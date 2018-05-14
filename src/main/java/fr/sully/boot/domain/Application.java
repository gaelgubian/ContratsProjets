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

import fr.sully.boot.domain.enumeration.Langage;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Application implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "langage_principal")
    private Langage langagePrincipal;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    private Projet projet;

    @OneToMany(mappedBy = "application")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dependency> dependencies = new HashSet<>();

    @OneToMany(mappedBy = "application")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Outil> outils = new HashSet<>();

    @OneToMany(mappedBy = "application")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Traitement> traitements = new HashSet<>();

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

    public Application nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCourt() {
        return nomCourt;
    }

    public Application nomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
        return this;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public Langage getLangagePrincipal() {
        return langagePrincipal;
    }

    public Application langagePrincipal(Langage langagePrincipal) {
        this.langagePrincipal = langagePrincipal;
        return this;
    }

    public void setLangagePrincipal(Langage langagePrincipal) {
        this.langagePrincipal = langagePrincipal;
    }

    public byte[] getImage() {
        return image;
    }

    public Application image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Application imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Projet getProjet() {
        return projet;
    }

    public Application projet(Projet projet) {
        this.projet = projet;
        return this;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Set<Dependency> getDependencies() {
        return dependencies;
    }

    public Application dependencies(Set<Dependency> dependencies) {
        this.dependencies = dependencies;
        return this;
    }

    public Application addDependencies(Dependency dependency) {
        this.dependencies.add(dependency);
        dependency.setApplication(this);
        return this;
    }

    public Application removeDependencies(Dependency dependency) {
        this.dependencies.remove(dependency);
        dependency.setApplication(null);
        return this;
    }

    public void setDependencies(Set<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public Set<Outil> getOutils() {
        return outils;
    }

    public Application outils(Set<Outil> outils) {
        this.outils = outils;
        return this;
    }

    public Application addOutils(Outil outil) {
        this.outils.add(outil);
        outil.setApplication(this);
        return this;
    }

    public Application removeOutils(Outil outil) {
        this.outils.remove(outil);
        outil.setApplication(null);
        return this;
    }

    public void setOutils(Set<Outil> outils) {
        this.outils = outils;
    }

    public Set<Traitement> getTraitements() {
        return traitements;
    }

    public Application traitements(Set<Traitement> traitements) {
        this.traitements = traitements;
        return this;
    }

    public Application addTraitements(Traitement traitement) {
        this.traitements.add(traitement);
        traitement.setApplication(this);
        return this;
    }

    public Application removeTraitements(Traitement traitement) {
        this.traitements.remove(traitement);
        traitement.setApplication(null);
        return this;
    }

    public void setTraitements(Set<Traitement> traitements) {
        this.traitements = traitements;
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
        Application application = (Application) o;
        if (application.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), application.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", langagePrincipal='" + getLangagePrincipal() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
