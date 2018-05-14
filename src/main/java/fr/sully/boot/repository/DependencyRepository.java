package fr.sully.boot.repository;

import fr.sully.boot.domain.Dependency;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dependency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DependencyRepository extends JpaRepository<Dependency, Long> {

}
