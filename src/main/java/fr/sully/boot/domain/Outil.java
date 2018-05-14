package fr.sully.boot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import fr.sully.boot.domain.enumeration.CategorieOutil;

/**
 * A Outil.
 */
@Entity
@Table(name = "outil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Outil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "categorie", nullable = false)
    private CategorieOutil categorie;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "url")
    private String url;

    @ManyToOne
    private Application application;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public Outil image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Outil imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public CategorieOutil getCategorie() {
        return categorie;
    }

    public Outil categorie(CategorieOutil categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(CategorieOutil categorie) {
        this.categorie = categorie;
    }

    public String getNom() {
        return nom;
    }

    public Outil nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUrl() {
        return url;
    }

    public Outil url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Application getApplication() {
        return application;
    }

    public Outil application(Application application) {
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
        Outil outil = (Outil) o;
        if (outil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), outil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Outil{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", nom='" + getNom() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
