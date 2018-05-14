package fr.sully.boot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import fr.sully.boot.domain.enumeration.Finalite;

import fr.sully.boot.domain.enumeration.FondementTraitement;

import fr.sully.boot.domain.enumeration.TypeTraitement;

/**
 * A Traitement.
 */
@Entity
@Table(name = "traitement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Traitement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "finalite")
    private Finalite finalite;

    @Enumerated(EnumType.STRING)
    @Column(name = "fondement_traitement")
    private FondementTraitement fondementTraitement;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_traitement")
    private TypeTraitement typeTraitement;

    @Column(name = "sous_traitant")
    private String sousTraitant;

    @Column(name = "technologie")
    private String technologie;

    @Column(name = "risque")
    private String risque;

    @Column(name = "mesure_securite")
    private String mesureSecurite;

    @Column(name = "documentation")
    private String documentation;

    @Column(name = "aipd")
    private String aipd;

    @Column(name = "information")
    private String information;

    @Column(name = "procedure_exercice_droits")
    private String procedureExerciceDroits;

    @Column(name = "remarques")
    private String remarques;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "date_maj")
    private LocalDate dateMAJ;

    @ManyToOne
    private Application application;

    @OneToMany(mappedBy = "traitement")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DonneePersonnelle> donneesPersonnelles = new HashSet<>();

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

    public Traitement nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Traitement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Finalite getFinalite() {
        return finalite;
    }

    public Traitement finalite(Finalite finalite) {
        this.finalite = finalite;
        return this;
    }

    public void setFinalite(Finalite finalite) {
        this.finalite = finalite;
    }

    public FondementTraitement getFondementTraitement() {
        return fondementTraitement;
    }

    public Traitement fondementTraitement(FondementTraitement fondementTraitement) {
        this.fondementTraitement = fondementTraitement;
        return this;
    }

    public void setFondementTraitement(FondementTraitement fondementTraitement) {
        this.fondementTraitement = fondementTraitement;
    }

    public TypeTraitement getTypeTraitement() {
        return typeTraitement;
    }

    public Traitement typeTraitement(TypeTraitement typeTraitement) {
        this.typeTraitement = typeTraitement;
        return this;
    }

    public void setTypeTraitement(TypeTraitement typeTraitement) {
        this.typeTraitement = typeTraitement;
    }

    public String getSousTraitant() {
        return sousTraitant;
    }

    public Traitement sousTraitant(String sousTraitant) {
        this.sousTraitant = sousTraitant;
        return this;
    }

    public void setSousTraitant(String sousTraitant) {
        this.sousTraitant = sousTraitant;
    }

    public String getTechnologie() {
        return technologie;
    }

    public Traitement technologie(String technologie) {
        this.technologie = technologie;
        return this;
    }

    public void setTechnologie(String technologie) {
        this.technologie = technologie;
    }

    public String getRisque() {
        return risque;
    }

    public Traitement risque(String risque) {
        this.risque = risque;
        return this;
    }

    public void setRisque(String risque) {
        this.risque = risque;
    }

    public String getMesureSecurite() {
        return mesureSecurite;
    }

    public Traitement mesureSecurite(String mesureSecurite) {
        this.mesureSecurite = mesureSecurite;
        return this;
    }

    public void setMesureSecurite(String mesureSecurite) {
        this.mesureSecurite = mesureSecurite;
    }

    public String getDocumentation() {
        return documentation;
    }

    public Traitement documentation(String documentation) {
        this.documentation = documentation;
        return this;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getAipd() {
        return aipd;
    }

    public Traitement aipd(String aipd) {
        this.aipd = aipd;
        return this;
    }

    public void setAipd(String aipd) {
        this.aipd = aipd;
    }

    public String getInformation() {
        return information;
    }

    public Traitement information(String information) {
        this.information = information;
        return this;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getProcedureExerciceDroits() {
        return procedureExerciceDroits;
    }

    public Traitement procedureExerciceDroits(String procedureExerciceDroits) {
        this.procedureExerciceDroits = procedureExerciceDroits;
        return this;
    }

    public void setProcedureExerciceDroits(String procedureExerciceDroits) {
        this.procedureExerciceDroits = procedureExerciceDroits;
    }

    public String getRemarques() {
        return remarques;
    }

    public Traitement remarques(String remarques) {
        this.remarques = remarques;
        return this;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Traitement dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Traitement dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateMAJ() {
        return dateMAJ;
    }

    public Traitement dateMAJ(LocalDate dateMAJ) {
        this.dateMAJ = dateMAJ;
        return this;
    }

    public void setDateMAJ(LocalDate dateMAJ) {
        this.dateMAJ = dateMAJ;
    }

    public Application getApplication() {
        return application;
    }

    public Traitement application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Set<DonneePersonnelle> getDonneesPersonnelles() {
        return donneesPersonnelles;
    }

    public Traitement donneesPersonnelles(Set<DonneePersonnelle> donneePersonnelles) {
        this.donneesPersonnelles = donneePersonnelles;
        return this;
    }

    public Traitement addDonneesPersonnelles(DonneePersonnelle donneePersonnelle) {
        this.donneesPersonnelles.add(donneePersonnelle);
        donneePersonnelle.setTraitement(this);
        return this;
    }

    public Traitement removeDonneesPersonnelles(DonneePersonnelle donneePersonnelle) {
        this.donneesPersonnelles.remove(donneePersonnelle);
        donneePersonnelle.setTraitement(null);
        return this;
    }

    public void setDonneesPersonnelles(Set<DonneePersonnelle> donneePersonnelles) {
        this.donneesPersonnelles = donneePersonnelles;
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
        Traitement traitement = (Traitement) o;
        if (traitement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), traitement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Traitement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", finalite='" + getFinalite() + "'" +
            ", fondementTraitement='" + getFondementTraitement() + "'" +
            ", typeTraitement='" + getTypeTraitement() + "'" +
            ", sousTraitant='" + getSousTraitant() + "'" +
            ", technologie='" + getTechnologie() + "'" +
            ", risque='" + getRisque() + "'" +
            ", mesureSecurite='" + getMesureSecurite() + "'" +
            ", documentation='" + getDocumentation() + "'" +
            ", aipd='" + getAipd() + "'" +
            ", information='" + getInformation() + "'" +
            ", procedureExerciceDroits='" + getProcedureExerciceDroits() + "'" +
            ", remarques='" + getRemarques() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", dateMAJ='" + getDateMAJ() + "'" +
            "}";
    }
}
