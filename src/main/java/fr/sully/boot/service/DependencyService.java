package fr.sully.boot.service;

import fr.sully.boot.domain.Dependency;
import fr.sully.boot.repository.DependencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Dependency.
 */
@Service
@Transactional
public class DependencyService {

    private final Logger log = LoggerFactory.getLogger(DependencyService.class);

    private final DependencyRepository dependencyRepository;

    public DependencyService(DependencyRepository dependencyRepository) {
        this.dependencyRepository = dependencyRepository;
    }

    /**
     * Save a dependency.
     *
     * @param dependency the entity to save
     * @return the persisted entity
     */
    public Dependency save(Dependency dependency) {
        log.debug("Request to save Dependency : {}", dependency);
        return dependencyRepository.save(dependency);
    }

    /**
     * Get all the dependencies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Dependency> findAll(Pageable pageable) {
        log.debug("Request to get all Dependencies");
        return dependencyRepository.findAll(pageable);
    }

    /**
     * Get one dependency by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Dependency findOne(Long id) {
        log.debug("Request to get Dependency : {}", id);
        return dependencyRepository.findOne(id);
    }

    /**
     * Delete the dependency by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Dependency : {}", id);
        dependencyRepository.delete(id);
    }
}
