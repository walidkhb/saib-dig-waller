package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.TransactionInfo;
import sa.com.saib.dig.wlt.repository.TransactionInfoRepository;
import sa.com.saib.dig.wlt.repository.search.TransactionInfoSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.TransactionInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransactionInfoResource {

    private final Logger log = LoggerFactory.getLogger(TransactionInfoResource.class);

    private static final String ENTITY_NAME = "transactionInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionInfoRepository transactionInfoRepository;

    private final TransactionInfoSearchRepository transactionInfoSearchRepository;

    public TransactionInfoResource(TransactionInfoRepository transactionInfoRepository, TransactionInfoSearchRepository transactionInfoSearchRepository) {
        this.transactionInfoRepository = transactionInfoRepository;
        this.transactionInfoSearchRepository = transactionInfoSearchRepository;
    }

    /**
     * {@code POST  /transaction-infos} : Create a new transactionInfo.
     *
     * @param transactionInfo the transactionInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionInfo, or with status {@code 400 (Bad Request)} if the transactionInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-infos")
    public ResponseEntity<TransactionInfo> createTransactionInfo(@RequestBody TransactionInfo transactionInfo) throws URISyntaxException {
        log.debug("REST request to save TransactionInfo : {}", transactionInfo);
        if (transactionInfo.getId() != null) {
            throw new BadRequestAlertException("A new transactionInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionInfo result = transactionInfoRepository.save(transactionInfo);
        transactionInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transaction-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-infos} : Updates an existing transactionInfo.
     *
     * @param transactionInfo the transactionInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionInfo,
     * or with status {@code 400 (Bad Request)} if the transactionInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-infos")
    public ResponseEntity<TransactionInfo> updateTransactionInfo(@RequestBody TransactionInfo transactionInfo) throws URISyntaxException {
        log.debug("REST request to update TransactionInfo : {}", transactionInfo);
        if (transactionInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionInfo result = transactionInfoRepository.save(transactionInfo);
        transactionInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-infos} : get all the transactionInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionInfos in body.
     */
    @GetMapping("/transaction-infos")
    public List<TransactionInfo> getAllTransactionInfos() {
        log.debug("REST request to get all TransactionInfos");
        return transactionInfoRepository.findAll();
    }

    /**
     * {@code GET  /transaction-infos/:id} : get the "id" transactionInfo.
     *
     * @param id the id of the transactionInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-infos/{id}")
    public ResponseEntity<TransactionInfo> getTransactionInfo(@PathVariable Long id) {
        log.debug("REST request to get TransactionInfo : {}", id);
        Optional<TransactionInfo> transactionInfo = transactionInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transactionInfo);
    }

    /**
     * {@code DELETE  /transaction-infos/:id} : delete the "id" transactionInfo.
     *
     * @param id the id of the transactionInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-infos/{id}")
    public ResponseEntity<Void> deleteTransactionInfo(@PathVariable Long id) {
        log.debug("REST request to delete TransactionInfo : {}", id);
        transactionInfoRepository.deleteById(id);
        transactionInfoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/transaction-infos?query=:query} : search for the transactionInfo corresponding
     * to the query.
     *
     * @param query the query of the transactionInfo search.
     * @return the result of the search.
     */
    @GetMapping("/_search/transaction-infos")
    public List<TransactionInfo> searchTransactionInfos(@RequestParam String query) {
        log.debug("REST request to search TransactionInfos for query {}", query);
        return StreamSupport
            .stream(transactionInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
