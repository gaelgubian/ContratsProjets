package fr.sully.boot.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sully.boot.domain.Dependency;
import fr.sully.boot.service.DependencyService;
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
 * REST controller for managing Dependency.
 */
@RestController
@RequestMapping("/api")
public class DependencyResource {

    private final Logger log = LoggerFactory.getLogger(DependencyResource.class);

    private static final String ENTITY_NAME = "dependency";

    private final DependencyService dependencyService;

    public DependencyResource(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    /**
     * POST  /dependencies : Create a new dependency.
     *
     * @param dependency the dependency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dependency, or with status 400 (Bad Request) if the dependency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dependencies")
    @Timed
    public ResponseEntity<Dependency> createDependency(@Valid @RequestBody Dependency dependency) throws URISyntaxException {
        log.debug("REST request to save Dependency : {}", dependency);
        if (dependency.getId() != null) {
            throw new BadRequestAlertException("A new dependency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dependency result = dependencyService.save(dependency);
        return ResponseEntity.created(new URI("/api/dependencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dependencies : Updates an existing dependency.
     *
     * @param dependency the dependency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dependency,
     * or with status 400 (Bad Request) if the dependency is not valid,
     * or with status 500 (Internal Server Error) if the dependency couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dependencies")
    @Timed
    public ResponseEntity<Dependency> updateDependency(@Valid @RequestBody Dependency dependency) throws URISyntaxException {
        log.debug("REST request to update Dependency : {}", dependency);
        if (dependency.getId() == null) {
            return createDependency(dependency);
        }
        Dependency result = dependencyService.save(dependency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dependency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dependencies : get all the dependencies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dependencies in body
     */
    @GetMapping("/dependencies")
    @Timed
    public ResponseEntity<List<Dependency>> getAllDependencies(Pageable pageable) {
        log.debug("REST request to get a page of Dependencies");
        Page<Dependency> page = dependencyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dependencies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dependencies/:id : get the "id" dependency.
     *
     * @param id the id of the dependency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dependency, or with status 404 (Not Found)
     */
    @GetMapping("/dependencies/{id}")
    @Timed
    public ResponseEntity<Dependency> getDependency(@PathVariable Long id) {
        log.debug("REST request to get Dependency : {}", id);
        Dependency dependency = dependencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dependency));
    }

    /**
     * DELETE  /dependencies/:id : delete the "id" dependency.
     *
     * @param id the id of the dependency to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dependencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteDependency(@PathVariable Long id) {
        log.debug("REST request to delete Dependency : {}", id);
        dependencyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
