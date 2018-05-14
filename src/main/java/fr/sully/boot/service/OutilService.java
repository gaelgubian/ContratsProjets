package fr.sully.boot.service;

import fr.sully.boot.domain.Outil;
import fr.sully.boot.repository.OutilRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Outil.
 */
@Service
@Transactional
public class OutilService {

    private final Logger log = LoggerFactory.getLogger(OutilService.class);

    private final OutilRepository outilRepository;

    public OutilService(OutilRepository outilRepository) {
        this.outilRepository = outilRepository;
    }

    /**
     * Save a outil.
     *
     * @param outil the entity to save
     * @return the persisted entity
     */
    public Outil save(Outil outil) {
        log.debug("Request to save Outil : {}", outil);
        return outilRepository.save(outil);
    }

    /**
     * Get all the outils.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Outil> findAll() {
        log.debug("Request to get all Outils");
        return outilRepository.findAll();
    }

    /**
     * Get one outil by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Outil findOne(Long id) {
        log.debug("Request to get Outil : {}", id);
        return outilRepository.findOne(id);
    }

    /**
     * Delete the outil by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Outil : {}", id);
        outilRepository.delete(id);
    }
}
