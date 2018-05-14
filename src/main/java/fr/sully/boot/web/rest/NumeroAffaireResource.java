package fr.sully.boot.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sully.boot.domain.NumeroAffaire;

import fr.sully.boot.repository.NumeroAffaireRepository;
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
 * REST controller for managing NumeroAffaire.
 */
@RestController
@RequestMapping("/api")
public class NumeroAffaireResource {

    private final Logger log = LoggerFactory.getLogger(NumeroAffaireResource.class);

    private static final String ENTITY_NAME = "numeroAffaire";

    private final NumeroAffaireRepository numeroAffaireRepository;

    public NumeroAffaireResource(NumeroAffaireRepository numeroAffaireRepository) {
        this.numeroAffaireRepository = numeroAffaireRepository;
    }

    /**
     * POST  /numero-affaires : Create a new numeroAffaire.
     *
     * @param numeroAffaire the numeroAffaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new numeroAffaire, or with status 400 (Bad Request) if the numeroAffaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/numero-affaires")
    @Timed
    public ResponseEntity<NumeroAffaire> createNumeroAffaire(@Valid @RequestBody NumeroAffaire numeroAffaire) throws URISyntaxException {
        log.debug("REST request to save NumeroAffaire : {}", numeroAffaire);
        if (numeroAffaire.getId() != null) {
            throw new BadRequestAlertException("A new numeroAffaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NumeroAffaire result = numeroAffaireRepository.save(numeroAffaire);
        return ResponseEntity.created(new URI("/api/numero-affaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /numero-affaires : Updates an existing numeroAffaire.
     *
     * @param numeroAffaire the numeroAffaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated numeroAffaire,
     * or with status 400 (Bad Request) if the numeroAffaire is not valid,
     * or with status 500 (Internal Server Error) if the numeroAffaire couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/numero-affaires")
    @Timed
    public ResponseEntity<NumeroAffaire> updateNumeroAffaire(@Valid @RequestBody NumeroAffaire numeroAffaire) throws URISyntaxException {
        log.debug("REST request to update NumeroAffaire : {}", numeroAffaire);
        if (numeroAffaire.getId() == null) {
            return createNumeroAffaire(numeroAffaire);
        }
        NumeroAffaire result = numeroAffaireRepository.save(numeroAffaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, numeroAffaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /numero-affaires : get all the numeroAffaires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of numeroAffaires in body
     */
    @GetMapping("/numero-affaires")
    @Timed
    public ResponseEntity<List<NumeroAffaire>> getAllNumeroAffaires(Pageable pageable) {
        log.debug("REST request to get a page of NumeroAffaires");
        Page<NumeroAffaire> page = numeroAffaireRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/numero-affaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /numero-affaires/:id : get the "id" numeroAffaire.
     *
     * @param id the id of the numeroAffaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the numeroAffaire, or with status 404 (Not Found)
     */
    @GetMapping("/numero-affaires/{id}")
    @Timed
    public ResponseEntity<NumeroAffaire> getNumeroAffaire(@PathVariable Long id) {
        log.debug("REST request to get NumeroAffaire : {}", id);
        NumeroAffaire numeroAffaire = numeroAffaireRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(numeroAffaire));
    }

    /**
     * DELETE  /numero-affaires/:id : delete the "id" numeroAffaire.
     *
     * @param id the id of the numeroAffaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/numero-affaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteNumeroAffaire(@PathVariable Long id) {
        log.debug("REST request to delete NumeroAffaire : {}", id);
        numeroAffaireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
