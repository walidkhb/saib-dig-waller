package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.TransactionHistory;
import sa.com.saib.dig.wlt.repository.TransactionHistoryRepository;
import sa.com.saib.dig.wlt.repository.search.TransactionHistorySearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.TransactionHistory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransactionHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TransactionHistoryResource.class);

    private static final String ENTITY_NAME = "transactionHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final TransactionHistorySearchRepository transactionHistorySearchRepository;

    public TransactionHistoryResource(TransactionHistoryRepository transactionHistoryRepository, TransactionHistorySearchRepository transactionHistorySearchRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistorySearchRepository = transactionHistorySearchRepository;
    }

    /**
     * {@code POST  /transaction-histories} : Create a new transactionHistory.
     *
     * @param transactionHistory the transactionHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionHistory, or with status {@code 400 (Bad Request)} if the transactionHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-histories")
    public ResponseEntity<TransactionHistory> createTransactionHistory(@RequestBody TransactionHistory transactionHistory) throws URISyntaxException {
        log.debug("REST request to save TransactionHistory : {}", transactionHistory);
        if (transactionHistory.getId() != null) {
            throw new BadRequestAlertException("A new transactionHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionHistory result = transactionHistoryRepository.save(transactionHistory);
        transactionHistorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transaction-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-histories} : Updates an existing transactionHistory.
     *
     * @param transactionHistory the transactionHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionHistory,
     * or with status {@code 400 (Bad Request)} if the transactionHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-histories")
    public ResponseEntity<TransactionHistory> updateTransactionHistory(@RequestBody TransactionHistory transactionHistory) throws URISyntaxException {
        log.debug("REST request to update TransactionHistory : {}", transactionHistory);
        if (transactionHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionHistory result = transactionHistoryRepository.save(transactionHistory);
        transactionHistorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-histories} : get all the transactionHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionHistories in body.
     */
    @GetMapping("/transaction-histories")
    public List<TransactionHistory> getAllTransactionHistories() {
        log.debug("REST request to get all TransactionHistories");
        return transactionHistoryRepository.findAll();
    }

    /**
     * {@code GET  /transaction-histories/:id} : get the "id" transactionHistory.
     *
     * @param id the id of the transactionHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-histories/{id}")
    public ResponseEntity<TransactionHistory> getTransactionHistory(@PathVariable Long id) {
        log.debug("REST request to get TransactionHistory : {}", id);
        Optional<TransactionHistory> transactionHistory = transactionHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transactionHistory);
    }

    /**
     * {@code DELETE  /transaction-histories/:id} : delete the "id" transactionHistory.
     *
     * @param id the id of the transactionHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-histories/{id}")
    public ResponseEntity<Void> deleteTransactionHistory(@PathVariable Long id) {
        log.debug("REST request to delete TransactionHistory : {}", id);
        transactionHistoryRepository.deleteById(id);
        transactionHistorySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/transaction-histories?query=:query} : search for the transactionHistory corresponding
     * to the query.
     *
     * @param query the query of the transactionHistory search.
     * @return the result of the search.
     */
    @GetMapping("/_search/transaction-histories")
    public List<TransactionHistory> searchTransactionHistories(@RequestParam String query) {
        log.debug("REST request to search TransactionHistories for query {}", query);
        return StreamSupport
            .stream(transactionHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
