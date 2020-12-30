package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.Creditor;
import sa.com.saib.dig.wlt.repository.CreditorRepository;
import sa.com.saib.dig.wlt.repository.search.CreditorSearchRepository;
import sa.com.saib.dig.wlt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.Creditor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CreditorResource {

    private final Logger log = LoggerFactory.getLogger(CreditorResource.class);

    private static final String ENTITY_NAME = "creditor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditorRepository creditorRepository;

    private final CreditorSearchRepository creditorSearchRepository;

    public CreditorResource(CreditorRepository creditorRepository, CreditorSearchRepository creditorSearchRepository) {
        this.creditorRepository = creditorRepository;
        this.creditorSearchRepository = creditorSearchRepository;
    }

    /**
     * {@code POST  /creditors} : Create a new creditor.
     *
     * @param creditor the creditor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditor, or with status {@code 400 (Bad Request)} if the creditor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/creditors")
    public ResponseEntity<Creditor> createCreditor(@RequestBody Creditor creditor) throws URISyntaxException {
        log.debug("REST request to save Creditor : {}", creditor);
        if (creditor.getId() != null) {
            throw new BadRequestAlertException("A new creditor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Creditor result = creditorRepository.save(creditor);
        creditorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/creditors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /creditors} : Updates an existing creditor.
     *
     * @param creditor the creditor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditor,
     * or with status {@code 400 (Bad Request)} if the creditor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/creditors")
    public ResponseEntity<Creditor> updateCreditor(@RequestBody Creditor creditor) throws URISyntaxException {
        log.debug("REST request to update Creditor : {}", creditor);
        if (creditor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Creditor result = creditorRepository.save(creditor);
        creditorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /creditors} : get all the creditors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditors in body.
     */
    @GetMapping("/creditors")
    public List<Creditor> getAllCreditors() {
        log.debug("REST request to get all Creditors");
        return creditorRepository.findAll();
    }

    /**
     * {@code GET  /creditors/:id} : get the "id" creditor.
     *
     * @param id the id of the creditor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/creditors/{id}")
    public ResponseEntity<Creditor> getCreditor(@PathVariable Long id) {
        log.debug("REST request to get Creditor : {}", id);
        Optional<Creditor> creditor = creditorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(creditor);
    }

    /**
     * {@code DELETE  /creditors/:id} : delete the "id" creditor.
     *
     * @param id the id of the creditor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/creditors/{id}")
    public ResponseEntity<Void> deleteCreditor(@PathVariable Long id) {
        log.debug("REST request to delete Creditor : {}", id);
        creditorRepository.deleteById(id);
        creditorSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/creditors?query=:query} : search for the creditor corresponding
     * to the query.
     *
     * @param query the query of the creditor search.
     * @return the result of the search.
     */
    @GetMapping("/_search/creditors")
    public List<Creditor> searchCreditors(@RequestParam String query) {
        log.debug("REST request to search Creditors for query {}", query);
        return StreamSupport
            .stream(creditorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
