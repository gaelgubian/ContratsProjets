package fr.sully.boot.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sully.boot.domain.DonneePersonnelle;
import fr.sully.boot.service.DonneePersonnelleService;
import fr.sully.boot.web.rest.errors.BadRequestAlertException;
import fr.sully.boot.web.rest.util.HeaderUtil;
import fr.sully.boot.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DonneePersonnelle.
 */
@RestController
@RequestMapping("/api")
public class DonneePersonnelleResource {

    private final Logger log = LoggerFactory.getLogger(DonneePersonnelleResource.class);

    private static final String ENTITY_NAME = "donneePersonnelle";

    private final DonneePersonnelleService donneePersonnelleService;

    public DonneePersonnelleResource(DonneePersonnelleService donneePersonnelleService) {
        this.donneePersonnelleService = donneePersonnelleService;
    }

    /**
     * POST  /donnee-personnelles : Create a new donneePersonnelle.
     *
     * @param donneePersonnelle the donneePersonnelle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new donneePersonnelle, or with status 400 (Bad Request) if the donneePersonnelle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/donnee-personnelles")
    @Timed
    public ResponseEntity<DonneePersonnelle> createDonneePersonnelle(@Valid @RequestBody DonneePersonnelle donneePersonnelle) throws URISyntaxException {
        log.debug("REST request to save DonneePersonnelle : {}", donneePersonnelle);
        if (donneePersonnelle.getId() != null) {
            throw new BadRequestAlertException("A new donneePersonnelle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonneePersonnelle result = donneePersonnelleService.save(donneePersonnelle);
        return ResponseEntity.created(new URI("/api/donnee-personnelles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /donnee-personnelles : Updates an existing donneePersonnelle.
     *
     * @param donneePersonnelle the donneePersonnelle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated donneePersonnelle,
     * or with status 400 (Bad Request) if the donneePersonnelle is not valid,
     * or with status 500 (Internal Server Error) if the donneePersonnelle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/donnee-personnelles")
    @Timed
    public ResponseEntity<DonneePersonnelle> updateDonneePersonnelle(@Valid @RequestBody DonneePersonnelle donneePersonnelle) throws URISyntaxException {
        log.debug("REST request to update DonneePersonnelle : {}", donneePersonnelle);
        if (donneePersonnelle.getId() == null) {
            return createDonneePersonnelle(donneePersonnelle);
        }
        DonneePersonnelle result = donneePersonnelleService.save(donneePersonnelle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, donneePersonnelle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /donnee-personnelles : get all the donneePersonnelles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of donneePersonnelles in body
     */
    @GetMapping("/donnee-personnelles")
    @Timed
    public ResponseEntity<List<DonneePersonnelle>> getAllDonneePersonnelles(Pageable pageable) {
        log.debug("REST request to get a page of DonneePersonnelles");
        Page<DonneePersonnelle> page = donneePersonnelleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/donnee-personnelles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /donnee-personnelles/:id : get the "id" donneePersonnelle.
     *
     * @param id the id of the donneePersonnelle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the donneePersonnelle, or with status 404 (Not Found)
     */
    @GetMapping("/donnee-personnelles/{id}")
    @Timed
    public ResponseEntity<DonneePersonnelle> getDonneePersonnelle(@PathVariable Long id) {
        log.debug("REST request to get DonneePersonnelle : {}", id);
        DonneePersonnelle donneePersonnelle = donneePersonnelleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(donneePersonnelle));
    }

    /**
     * DELETE  /donnee-personnelles/:id : delete the "id" donneePersonnelle.
     *
     * @param id the id of the donneePersonnelle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/donnee-personnelles/{id}")
    @Timed
    public ResponseEntity<Void> deleteDonneePersonnelle(@PathVariable Long id) {
        log.debug("REST request to delete DonneePersonnelle : {}", id);
        donneePersonnelleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
