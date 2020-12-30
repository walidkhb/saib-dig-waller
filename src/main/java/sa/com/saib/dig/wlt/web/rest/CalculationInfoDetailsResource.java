package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.CalculationInfoDetails;
import sa.com.saib.dig.wlt.repository.CalculationInfoDetailsRepository;
import sa.com.saib.dig.wlt.repository.search.CalculationInfoDetailsSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.CalculationInfoDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CalculationInfoDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CalculationInfoDetailsResource.class);

    private static final String ENTITY_NAME = "calculationInfoDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalculationInfoDetailsRepository calculationInfoDetailsRepository;

    private final CalculationInfoDetailsSearchRepository calculationInfoDetailsSearchRepository;

    public CalculationInfoDetailsResource(CalculationInfoDetailsRepository calculationInfoDetailsRepository, CalculationInfoDetailsSearchRepository calculationInfoDetailsSearchRepository) {
        this.calculationInfoDetailsRepository = calculationInfoDetailsRepository;
        this.calculationInfoDetailsSearchRepository = calculationInfoDetailsSearchRepository;
    }

    /**
     * {@code POST  /calculation-info-details} : Create a new calculationInfoDetails.
     *
     * @param calculationInfoDetails the calculationInfoDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calculationInfoDetails, or with status {@code 400 (Bad Request)} if the calculationInfoDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calculation-info-details")
    public ResponseEntity<CalculationInfoDetails> createCalculationInfoDetails(@RequestBody CalculationInfoDetails calculationInfoDetails) throws URISyntaxException {
        log.debug("REST request to save CalculationInfoDetails : {}", calculationInfoDetails);
        if (calculationInfoDetails.getId() != null) {
            throw new BadRequestAlertException("A new calculationInfoDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalculationInfoDetails result = calculationInfoDetailsRepository.save(calculationInfoDetails);
        calculationInfoDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/calculation-info-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calculation-info-details} : Updates an existing calculationInfoDetails.
     *
     * @param calculationInfoDetails the calculationInfoDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calculationInfoDetails,
     * or with status {@code 400 (Bad Request)} if the calculationInfoDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calculationInfoDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calculation-info-details")
    public ResponseEntity<CalculationInfoDetails> updateCalculationInfoDetails(@RequestBody CalculationInfoDetails calculationInfoDetails) throws URISyntaxException {
        log.debug("REST request to update CalculationInfoDetails : {}", calculationInfoDetails);
        if (calculationInfoDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalculationInfoDetails result = calculationInfoDetailsRepository.save(calculationInfoDetails);
        calculationInfoDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calculationInfoDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /calculation-info-details} : get all the calculationInfoDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calculationInfoDetails in body.
     */
    @GetMapping("/calculation-info-details")
    public List<CalculationInfoDetails> getAllCalculationInfoDetails() {
        log.debug("REST request to get all CalculationInfoDetails");
        return calculationInfoDetailsRepository.findAll();
    }

    /**
     * {@code GET  /calculation-info-details/:id} : get the "id" calculationInfoDetails.
     *
     * @param id the id of the calculationInfoDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calculationInfoDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calculation-info-details/{id}")
    public ResponseEntity<CalculationInfoDetails> getCalculationInfoDetails(@PathVariable Long id) {
        log.debug("REST request to get CalculationInfoDetails : {}", id);
        Optional<CalculationInfoDetails> calculationInfoDetails = calculationInfoDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(calculationInfoDetails);
    }

    /**
     * {@code DELETE  /calculation-info-details/:id} : delete the "id" calculationInfoDetails.
     *
     * @param id the id of the calculationInfoDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calculation-info-details/{id}")
    public ResponseEntity<Void> deleteCalculationInfoDetails(@PathVariable Long id) {
        log.debug("REST request to delete CalculationInfoDetails : {}", id);
        calculationInfoDetailsRepository.deleteById(id);
        calculationInfoDetailsSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/calculation-info-details?query=:query} : search for the calculationInfoDetails corresponding
     * to the query.
     *
     * @param query the query of the calculationInfoDetails search.
     * @return the result of the search.
     */
    @GetMapping("/_search/calculation-info-details")
    public List<CalculationInfoDetails> searchCalculationInfoDetails(@RequestParam String query) {
        log.debug("REST request to search CalculationInfoDetails for query {}", query);
        return StreamSupport
            .stream(calculationInfoDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
