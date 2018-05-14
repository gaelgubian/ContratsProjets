package fr.sully.boot.repository;

import fr.sully.boot.domain.NumeroAffaire;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NumeroAffaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumeroAffaireRepository extends JpaRepository<NumeroAffaire, Long> {

}
