package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.DestinationChargeAmount;
import sa.com.saib.dig.wlt.repository.DestinationChargeAmountRepository;
import sa.com.saib.dig.wlt.repository.search.DestinationChargeAmountSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.DestinationChargeAmount}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DestinationChargeAmountResource {

    private final Logger log = LoggerFactory.getLogger(DestinationChargeAmountResource.class);

    private static final String ENTITY_NAME = "destinationChargeAmount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DestinationChargeAmountRepository destinationChargeAmountRepository;

    private final DestinationChargeAmountSearchRepository destinationChargeAmountSearchRepository;

    public DestinationChargeAmountResource(DestinationChargeAmountRepository destinationChargeAmountRepository, DestinationChargeAmountSearchRepository destinationChargeAmountSearchRepository) {
        this.destinationChargeAmountRepository = destinationChargeAmountRepository;
        this.destinationChargeAmountSearchRepository = destinationChargeAmountSearchRepository;
    }

    /**
     * {@code POST  /destination-charge-amounts} : Create a new destinationChargeAmount.
     *
     * @param destinationChargeAmount the destinationChargeAmount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new destinationChargeAmount, or with status {@code 400 (Bad Request)} if the destinationChargeAmount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/destination-charge-amounts")
    public ResponseEntity<DestinationChargeAmount> createDestinationChargeAmount(@RequestBody DestinationChargeAmount destinationChargeAmount) throws URISyntaxException {
        log.debug("REST request to save DestinationChargeAmount : {}", destinationChargeAmount);
        if (destinationChargeAmount.getId() != null) {
            throw new BadRequestAlertException("A new destinationChargeAmount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DestinationChargeAmount result = destinationChargeAmountRepository.save(destinationChargeAmount);
        destinationChargeAmountSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/destination-charge-amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /destination-charge-amounts} : Updates an existing destinationChargeAmount.
     *
     * @param destinationChargeAmount the destinationChargeAmount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated destinationChargeAmount,
     * or with status {@code 400 (Bad Request)} if the destinationChargeAmount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the destinationChargeAmount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/destination-charge-amounts")
    public ResponseEntity<DestinationChargeAmount> updateDestinationChargeAmount(@RequestBody DestinationChargeAmount destinationChargeAmount) throws URISyntaxException {
        log.debug("REST request to update DestinationChargeAmount : {}", destinationChargeAmount);
        if (destinationChargeAmount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DestinationChargeAmount result = destinationChargeAmountRepository.save(destinationChargeAmount);
        destinationChargeAmountSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, destinationChargeAmount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /destination-charge-amounts} : get all the destinationChargeAmounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of destinationChargeAmounts in body.
     */
    @GetMapping("/destination-charge-amounts")
    public List<DestinationChargeAmount> getAllDestinationChargeAmounts() {
        log.debug("REST request to get all DestinationChargeAmounts");
        return destinationChargeAmountRepository.findAll();
    }

    /**
     * {@code GET  /destination-charge-amounts/:id} : get the "id" destinationChargeAmount.
     *
     * @param id the id of the destinationChargeAmount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the destinationChargeAmount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/destination-charge-amounts/{id}")
    public ResponseEntity<DestinationChargeAmount> getDestinationChargeAmount(@PathVariable Long id) {
        log.debug("REST request to get DestinationChargeAmount : {}", id);
        Optional<DestinationChargeAmount> destinationChargeAmount = destinationChargeAmountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(destinationChargeAmount);
    }

    /**
     * {@code DELETE  /destination-charge-amounts/:id} : delete the "id" destinationChargeAmount.
     *
     * @param id the id of the destinationChargeAmount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/destination-charge-amounts/{id}")
    public ResponseEntity<Void> deleteDestinationChargeAmount(@PathVariable Long id) {
        log.debug("REST request to delete DestinationChargeAmount : {}", id);
        destinationChargeAmountRepository.deleteById(id);
        destinationChargeAmountSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/destination-charge-amounts?query=:query} : search for the destinationChargeAmount corresponding
     * to the query.
     *
     * @param query the query of the destinationChargeAmount search.
     * @return the result of the search.
     */
    @GetMapping("/_search/destination-charge-amounts")
    public List<DestinationChargeAmount> searchDestinationChargeAmounts(@RequestParam String query) {
        log.debug("REST request to search DestinationChargeAmounts for query {}", query);
        return StreamSupport
            .stream(destinationChargeAmountSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
