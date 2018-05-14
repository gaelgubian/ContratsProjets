package fr.sully.boot.repository;

import fr.sully.boot.domain.Outil;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Outil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutilRepository extends JpaRepository<Outil, Long> {

}
