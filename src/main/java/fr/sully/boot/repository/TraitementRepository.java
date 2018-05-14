package fr.sully.boot.repository;

import fr.sully.boot.domain.Traitement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Traitement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TraitementRepository extends JpaRepository<Traitement, Long> {

}
