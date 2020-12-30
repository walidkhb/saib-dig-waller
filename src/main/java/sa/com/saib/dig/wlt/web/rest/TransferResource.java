package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.Transfer;
import sa.com.saib.dig.wlt.repository.TransferRepository;
import sa.com.saib.dig.wlt.repository.search.TransferSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.Transfer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransferResource {

    private final Logger log = LoggerFactory.getLogger(TransferResource.class);

    private static final String ENTITY_NAME = "transfer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferRepository transferRepository;

    private final TransferSearchRepository transferSearchRepository;

    public TransferResource(TransferRepository transferRepository, TransferSearchRepository transferSearchRepository) {
        this.transferRepository = transferRepository;
        this.transferSearchRepository = transferSearchRepository;
    }

    /**
     * {@code POST  /transfers} : Create a new transfer.
     *
     * @param transfer the transfer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transfer, or with status {@code 400 (Bad Request)} if the transfer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transfers")
    public ResponseEntity<Transfer> createTransfer(@RequestBody Transfer transfer) throws URISyntaxException {
        log.debug("REST request to save Transfer : {}", transfer);
        if (transfer.getId() != null) {
            throw new BadRequestAlertException("A new transfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transfer result = transferRepository.save(transfer);
        transferSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transfers} : Updates an existing transfer.
     *
     * @param transfer the transfer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transfer,
     * or with status {@code 400 (Bad Request)} if the transfer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transfer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transfers")
    public ResponseEntity<Transfer> updateTransfer(@RequestBody Transfer transfer) throws URISyntaxException {
        log.debug("REST request to update Transfer : {}", transfer);
        if (transfer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transfer result = transferRepository.save(transfer);
        transferSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transfer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transfers} : get all the transfers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transfers in body.
     */
    @GetMapping("/transfers")
    public List<Transfer> getAllTransfers() {
        log.debug("REST request to get all Transfers");
        return transferRepository.findAll();
    }

    /**
     * {@code GET  /transfers/:id} : get the "id" transfer.
     *
     * @param id the id of the transfer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transfer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transfers/{id}")
    public ResponseEntity<Transfer> getTransfer(@PathVariable Long id) {
        log.debug("REST request to get Transfer : {}", id);
        Optional<Transfer> transfer = transferRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transfer);
    }

    /**
     * {@code DELETE  /transfers/:id} : delete the "id" transfer.
     *
     * @param id the id of the transfer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transfers/{id}")
    public ResponseEntity<Void> deleteTransfer(@PathVariable Long id) {
        log.debug("REST request to delete Transfer : {}", id);
        transferRepository.deleteById(id);
        transferSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/transfers?query=:query} : search for the transfer corresponding
     * to the query.
     *
     * @param query the query of the transfer search.
     * @return the result of the search.
     */
    @GetMapping("/_search/transfers")
    public List<Transfer> searchTransfers(@RequestParam String query) {
        log.debug("REST request to search Transfers for query {}", query);
        return StreamSupport
            .stream(transferSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
