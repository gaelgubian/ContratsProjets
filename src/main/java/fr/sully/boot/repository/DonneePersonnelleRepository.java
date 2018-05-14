package fr.sully.boot.repository;

import fr.sully.boot.domain.DonneePersonnelle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DonneePersonnelle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonneePersonnelleRepository extends JpaRepository<DonneePersonnelle, Long> {

}
