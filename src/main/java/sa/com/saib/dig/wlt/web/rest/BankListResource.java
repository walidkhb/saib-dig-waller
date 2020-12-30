package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.BankList;
import sa.com.saib.dig.wlt.repository.BankListRepository;
import sa.com.saib.dig.wlt.repository.search.BankListSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.BankList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BankListResource {

    private final Logger log = LoggerFactory.getLogger(BankListResource.class);

    private static final String ENTITY_NAME = "bankList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankListRepository bankListRepository;

    private final BankListSearchRepository bankListSearchRepository;

    public BankListResource(BankListRepository bankListRepository, BankListSearchRepository bankListSearchRepository) {
        this.bankListRepository = bankListRepository;
        this.bankListSearchRepository = bankListSearchRepository;
    }

    /**
     * {@code POST  /bank-lists} : Create a new bankList.
     *
     * @param bankList the bankList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankList, or with status {@code 400 (Bad Request)} if the bankList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-lists")
    public ResponseEntity<BankList> createBankList(@RequestBody BankList bankList) throws URISyntaxException {
        log.debug("REST request to save BankList : {}", bankList);
        if (bankList.getId() != null) {
            throw new BadRequestAlertException("A new bankList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankList result = bankListRepository.save(bankList);
        bankListSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bank-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-lists} : Updates an existing bankList.
     *
     * @param bankList the bankList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankList,
     * or with status {@code 400 (Bad Request)} if the bankList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-lists")
    public ResponseEntity<BankList> updateBankList(@RequestBody BankList bankList) throws URISyntaxException {
        log.debug("REST request to update BankList : {}", bankList);
        if (bankList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankList result = bankListRepository.save(bankList);
        bankListSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bank-lists} : get all the bankLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankLists in body.
     */
    @GetMapping("/bank-lists")
    public List<BankList> getAllBankLists() {
        log.debug("REST request to get all BankLists");
        return bankListRepository.findAll();
    }

    /**
     * {@code GET  /bank-lists/:id} : get the "id" bankList.
     *
     * @param id the id of the bankList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-lists/{id}")
    public ResponseEntity<BankList> getBankList(@PathVariable Long id) {
        log.debug("REST request to get BankList : {}", id);
        Optional<BankList> bankList = bankListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankList);
    }

    /**
     * {@code DELETE  /bank-lists/:id} : delete the "id" bankList.
     *
     * @param id the id of the bankList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-lists/{id}")
    public ResponseEntity<Void> deleteBankList(@PathVariable Long id) {
        log.debug("REST request to delete BankList : {}", id);
        bankListRepository.deleteById(id);
        bankListSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/bank-lists?query=:query} : search for the bankList corresponding
     * to the query.
     *
     * @param query the query of the bankList search.
     * @return the result of the search.
     */
    @GetMapping("/_search/bank-lists")
    public List<BankList> searchBankLists(@RequestParam String query) {
        log.debug("REST request to search BankLists for query {}", query);
        return StreamSupport
            .stream(bankListSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
