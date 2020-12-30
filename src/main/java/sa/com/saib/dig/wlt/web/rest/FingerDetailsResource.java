package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.FingerDetails;
import sa.com.saib.dig.wlt.repository.FingerDetailsRepository;
import sa.com.saib.dig.wlt.repository.search.FingerDetailsSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.FingerDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FingerDetailsResource {

    private final Logger log = LoggerFactory.getLogger(FingerDetailsResource.class);

    private static final String ENTITY_NAME = "fingerDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FingerDetailsRepository fingerDetailsRepository;

    private final FingerDetailsSearchRepository fingerDetailsSearchRepository;

    public FingerDetailsResource(FingerDetailsRepository fingerDetailsRepository, FingerDetailsSearchRepository fingerDetailsSearchRepository) {
        this.fingerDetailsRepository = fingerDetailsRepository;
        this.fingerDetailsSearchRepository = fingerDetailsSearchRepository;
    }

    /**
     * {@code POST  /finger-details} : Create a new fingerDetails.
     *
     * @param fingerDetails the fingerDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fingerDetails, or with status {@code 400 (Bad Request)} if the fingerDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/finger-details")
    public ResponseEntity<FingerDetails> createFingerDetails(@RequestBody FingerDetails fingerDetails) throws URISyntaxException {
        log.debug("REST request to save FingerDetails : {}", fingerDetails);
        if (fingerDetails.getId() != null) {
            throw new BadRequestAlertException("A new fingerDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FingerDetails result = fingerDetailsRepository.save(fingerDetails);
        fingerDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/finger-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /finger-details} : Updates an existing fingerDetails.
     *
     * @param fingerDetails the fingerDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fingerDetails,
     * or with status {@code 400 (Bad Request)} if the fingerDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fingerDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/finger-details")
    public ResponseEntity<FingerDetails> updateFingerDetails(@RequestBody FingerDetails fingerDetails) throws URISyntaxException {
        log.debug("REST request to update FingerDetails : {}", fingerDetails);
        if (fingerDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FingerDetails result = fingerDetailsRepository.save(fingerDetails);
        fingerDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fingerDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /finger-details} : get all the fingerDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fingerDetails in body.
     */
    @GetMapping("/finger-details")
    public List<FingerDetails> getAllFingerDetails() {
        log.debug("REST request to get all FingerDetails");
        return fingerDetailsRepository.findAll();
    }

    /**
     * {@code GET  /finger-details/:id} : get the "id" fingerDetails.
     *
     * @param id the id of the fingerDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fingerDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/finger-details/{id}")
    public ResponseEntity<FingerDetails> getFingerDetails(@PathVariable Long id) {
        log.debug("REST request to get FingerDetails : {}", id);
        Optional<FingerDetails> fingerDetails = fingerDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fingerDetails);
    }

    /**
     * {@code DELETE  /finger-details/:id} : delete the "id" fingerDetails.
     *
     * @param id the id of the fingerDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/finger-details/{id}")
    public ResponseEntity<Void> deleteFingerDetails(@PathVariable Long id) {
        log.debug("REST request to delete FingerDetails : {}", id);
        fingerDetailsRepository.deleteById(id);
        fingerDetailsSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/finger-details?query=:query} : search for the fingerDetails corresponding
     * to the query.
     *
     * @param query the query of the fingerDetails search.
     * @return the result of the search.
     */
    @GetMapping("/_search/finger-details")
    public List<FingerDetails> searchFingerDetails(@RequestParam String query) {
        log.debug("REST request to search FingerDetails for query {}", query);
        return StreamSupport
            .stream(fingerDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
