package fr.sully.boot.service;

import fr.sully.boot.domain.DonneePersonnelle;
import fr.sully.boot.repository.DonneePersonnelleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DonneePersonnelle.
 */
@Service
@Transactional
public class DonneePersonnelleService {

    private final Logger log = LoggerFactory.getLogger(DonneePersonnelleService.class);

    private final DonneePersonnelleRepository donneePersonnelleRepository;

    public DonneePersonnelleService(DonneePersonnelleRepository donneePersonnelleRepository) {
        this.donneePersonnelleRepository = donneePersonnelleRepository;
    }

    /**
     * Save a donneePersonnelle.
     *
     * @param donneePersonnelle the entity to save
     * @return the persisted entity
     */
    public DonneePersonnelle save(DonneePersonnelle donneePersonnelle) {
        log.debug("Request to save DonneePersonnelle : {}", donneePersonnelle);
        return donneePersonnelleRepository.save(donneePersonnelle);
    }

    /**
     * Get all the donneePersonnelles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DonneePersonnelle> findAll(Pageable pageable) {
        log.debug("Request to get all DonneePersonnelles");
        return donneePersonnelleRepository.findAll(pageable);
    }

    /**
     * Get one donneePersonnelle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DonneePersonnelle findOne(Long id) {
        log.debug("Request to get DonneePersonnelle : {}", id);
        return donneePersonnelleRepository.findOne(id);
    }

    /**
     * Delete the donneePersonnelle by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DonneePersonnelle : {}", id);
        donneePersonnelleRepository.delete(id);
    }
}
