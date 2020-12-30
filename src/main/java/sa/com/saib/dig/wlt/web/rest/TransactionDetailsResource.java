package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.TransactionDetails;
import sa.com.saib.dig.wlt.repository.TransactionDetailsRepository;
import sa.com.saib.dig.wlt.repository.search.TransactionDetailsSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.TransactionDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransactionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(TransactionDetailsResource.class);

    private static final String ENTITY_NAME = "transactionDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionDetailsRepository transactionDetailsRepository;

    private final TransactionDetailsSearchRepository transactionDetailsSearchRepository;

    public TransactionDetailsResource(TransactionDetailsRepository transactionDetailsRepository, TransactionDetailsSearchRepository transactionDetailsSearchRepository) {
        this.transactionDetailsRepository = transactionDetailsRepository;
        this.transactionDetailsSearchRepository = transactionDetailsSearchRepository;
    }

    /**
     * {@code POST  /transaction-details} : Create a new transactionDetails.
     *
     * @param transactionDetails the transactionDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionDetails, or with status {@code 400 (Bad Request)} if the transactionDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-details")
    public ResponseEntity<TransactionDetails> createTransactionDetails(@RequestBody TransactionDetails transactionDetails) throws URISyntaxException {
        log.debug("REST request to save TransactionDetails : {}", transactionDetails);
        if (transactionDetails.getId() != null) {
            throw new BadRequestAlertException("A new transactionDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionDetails result = transactionDetailsRepository.save(transactionDetails);
        transactionDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transaction-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-details} : Updates an existing transactionDetails.
     *
     * @param transactionDetails the transactionDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDetails,
     * or with status {@code 400 (Bad Request)} if the transactionDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-details")
    public ResponseEntity<TransactionDetails> updateTransactionDetails(@RequestBody TransactionDetails transactionDetails) throws URISyntaxException {
        log.debug("REST request to update TransactionDetails : {}", transactionDetails);
        if (transactionDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionDetails result = transactionDetailsRepository.save(transactionDetails);
        transactionDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-details} : get all the transactionDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionDetails in body.
     */
    @GetMapping("/transaction-details")
    public List<TransactionDetails> getAllTransactionDetails() {
        log.debug("REST request to get all TransactionDetails");
        return transactionDetailsRepository.findAll();
    }

    /**
     * {@code GET  /transaction-details/:id} : get the "id" transactionDetails.
     *
     * @param id the id of the transactionDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-details/{id}")
    public ResponseEntity<TransactionDetails> getTransactionDetails(@PathVariable Long id) {
        log.debug("REST request to get TransactionDetails : {}", id);
        Optional<TransactionDetails> transactionDetails = transactionDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transactionDetails);
    }

    /**
     * {@code DELETE  /transaction-details/:id} : delete the "id" transactionDetails.
     *
     * @param id the id of the transactionDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-details/{id}")
    public ResponseEntity<Void> deleteTransactionDetails(@PathVariable Long id) {
        log.debug("REST request to delete TransactionDetails : {}", id);
        transactionDetailsRepository.deleteById(id);
        transactionDetailsSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/transaction-details?query=:query} : search for the transactionDetails corresponding
     * to the query.
     *
     * @param query the query of the transactionDetails search.
     * @return the result of the search.
     */
    @GetMapping("/_search/transaction-details")
    public List<TransactionDetails> searchTransactionDetails(@RequestParam String query) {
        log.debug("REST request to search TransactionDetails for query {}", query);
        return StreamSupport
            .stream(transactionDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
