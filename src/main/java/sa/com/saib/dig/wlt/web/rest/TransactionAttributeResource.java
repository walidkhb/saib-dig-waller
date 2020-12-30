package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.TransactionAttribute;
import sa.com.saib.dig.wlt.repository.TransactionAttributeRepository;
import sa.com.saib.dig.wlt.repository.search.TransactionAttributeSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.TransactionAttribute}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransactionAttributeResource {

    private final Logger log = LoggerFactory.getLogger(TransactionAttributeResource.class);

    private static final String ENTITY_NAME = "transactionAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionAttributeRepository transactionAttributeRepository;

    private final TransactionAttributeSearchRepository transactionAttributeSearchRepository;

    public TransactionAttributeResource(TransactionAttributeRepository transactionAttributeRepository, TransactionAttributeSearchRepository transactionAttributeSearchRepository) {
        this.transactionAttributeRepository = transactionAttributeRepository;
        this.transactionAttributeSearchRepository = transactionAttributeSearchRepository;
    }

    /**
     * {@code POST  /transaction-attributes} : Create a new transactionAttribute.
     *
     * @param transactionAttribute the transactionAttribute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionAttribute, or with status {@code 400 (Bad Request)} if the transactionAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-attributes")
    public ResponseEntity<TransactionAttribute> createTransactionAttribute(@RequestBody TransactionAttribute transactionAttribute) throws URISyntaxException {
        log.debug("REST request to save TransactionAttribute : {}", transactionAttribute);
        if (transactionAttribute.getId() != null) {
            throw new BadRequestAlertException("A new transactionAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionAttribute result = transactionAttributeRepository.save(transactionAttribute);
        transactionAttributeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transaction-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-attributes} : Updates an existing transactionAttribute.
     *
     * @param transactionAttribute the transactionAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionAttribute,
     * or with status {@code 400 (Bad Request)} if the transactionAttribute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-attributes")
    public ResponseEntity<TransactionAttribute> updateTransactionAttribute(@RequestBody TransactionAttribute transactionAttribute) throws URISyntaxException {
        log.debug("REST request to update TransactionAttribute : {}", transactionAttribute);
        if (transactionAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionAttribute result = transactionAttributeRepository.save(transactionAttribute);
        transactionAttributeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionAttribute.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-attributes} : get all the transactionAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionAttributes in body.
     */
    @GetMapping("/transaction-attributes")
    public List<TransactionAttribute> getAllTransactionAttributes() {
        log.debug("REST request to get all TransactionAttributes");
        return transactionAttributeRepository.findAll();
    }

    /**
     * {@code GET  /transaction-attributes/:id} : get the "id" transactionAttribute.
     *
     * @param id the id of the transactionAttribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionAttribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-attributes/{id}")
    public ResponseEntity<TransactionAttribute> getTransactionAttribute(@PathVariable Long id) {
        log.debug("REST request to get TransactionAttribute : {}", id);
        Optional<TransactionAttribute> transactionAttribute = transactionAttributeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transactionAttribute);
    }

    /**
     * {@code DELETE  /transaction-attributes/:id} : delete the "id" transactionAttribute.
     *
     * @param id the id of the transactionAttribute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-attributes/{id}")
    public ResponseEntity<Void> deleteTransactionAttribute(@PathVariable Long id) {
        log.debug("REST request to delete TransactionAttribute : {}", id);
        transactionAttributeRepository.deleteById(id);
        transactionAttributeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/transaction-attributes?query=:query} : search for the transactionAttribute corresponding
     * to the query.
     *
     * @param query the query of the transactionAttribute search.
     * @return the result of the search.
     */
    @GetMapping("/_search/transaction-attributes")
    public List<TransactionAttribute> searchTransactionAttributes(@RequestParam String query) {
        log.debug("REST request to search TransactionAttributes for query {}", query);
        return StreamSupport
            .stream(transactionAttributeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
