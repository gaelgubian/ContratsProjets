package fr.sully.boot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Dependency.
 */
@Entity
@Table(name = "dependency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dependency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "version")
    private String version;

    @ManyToOne
    private Application application;

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

    public Dependency nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVersion() {
        return version;
    }

    public Dependency version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Application getApplication() {
        return application;
    }

    public Dependency application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
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
        Dependency dependency = (Dependency) o;
        if (dependency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dependency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dependency{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
