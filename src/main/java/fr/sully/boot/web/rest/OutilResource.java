package fr.sully.boot.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sully.boot.domain.Outil;
import fr.sully.boot.service.OutilService;
import fr.sully.boot.web.rest.errors.BadRequestAlertException;
import fr.sully.boot.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Outil.
 */
@RestController
@RequestMapping("/api")
public class OutilResource {

    private final Logger log = LoggerFactory.getLogger(OutilResource.class);

    private static final String ENTITY_NAME = "outil";

    private final OutilService outilService;

    public OutilResource(OutilService outilService) {
        this.outilService = outilService;
    }

    /**
     * POST  /outils : Create a new outil.
     *
     * @param outil the outil to create
     * @return the ResponseEntity with status 201 (Created) and with body the new outil, or with status 400 (Bad Request) if the outil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/outils")
    @Timed
    public ResponseEntity<Outil> createOutil(@Valid @RequestBody Outil outil) throws URISyntaxException {
        log.debug("REST request to save Outil : {}", outil);
        if (outil.getId() != null) {
            throw new BadRequestAlertException("A new outil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Outil result = outilService.save(outil);
        return ResponseEntity.created(new URI("/api/outils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /outils : Updates an existing outil.
     *
     * @param outil the outil to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated outil,
     * or with status 400 (Bad Request) if the outil is not valid,
     * or with status 500 (Internal Server Error) if the outil couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/outils")
    @Timed
    public ResponseEntity<Outil> updateOutil(@Valid @RequestBody Outil outil) throws URISyntaxException {
        log.debug("REST request to update Outil : {}", outil);
        if (outil.getId() == null) {
            return createOutil(outil);
        }
        Outil result = outilService.save(outil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, outil.getId().toString()))
            .body(result);
    }

    /**
     * GET  /outils : get all the outils.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of outils in body
     */
    @GetMapping("/outils")
    @Timed
    public List<Outil> getAllOutils() {
        log.debug("REST request to get all Outils");
        return outilService.findAll();
        }

    /**
     * GET  /outils/:id : get the "id" outil.
     *
     * @param id the id of the outil to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the outil, or with status 404 (Not Found)
     */
    @GetMapping("/outils/{id}")
    @Timed
    public ResponseEntity<Outil> getOutil(@PathVariable Long id) {
        log.debug("REST request to get Outil : {}", id);
        Outil outil = outilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(outil));
    }

    /**
     * DELETE  /outils/:id : delete the "id" outil.
     *
     * @param id the id of the outil to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/outils/{id}")
    @Timed
    public ResponseEntity<Void> deleteOutil(@PathVariable Long id) {
        log.debug("REST request to delete Outil : {}", id);
        outilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
