package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.CurrencyList;
import sa.com.saib.dig.wlt.repository.CurrencyListRepository;
import sa.com.saib.dig.wlt.repository.search.CurrencyListSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.CurrencyList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CurrencyListResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyListResource.class);

    private static final String ENTITY_NAME = "currencyList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrencyListRepository currencyListRepository;

    private final CurrencyListSearchRepository currencyListSearchRepository;

    public CurrencyListResource(CurrencyListRepository currencyListRepository, CurrencyListSearchRepository currencyListSearchRepository) {
        this.currencyListRepository = currencyListRepository;
        this.currencyListSearchRepository = currencyListSearchRepository;
    }

    /**
     * {@code POST  /currency-lists} : Create a new currencyList.
     *
     * @param currencyList the currencyList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currencyList, or with status {@code 400 (Bad Request)} if the currencyList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/currency-lists")
    public ResponseEntity<CurrencyList> createCurrencyList(@RequestBody CurrencyList currencyList) throws URISyntaxException {
        log.debug("REST request to save CurrencyList : {}", currencyList);
        if (currencyList.getId() != null) {
            throw new BadRequestAlertException("A new currencyList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrencyList result = currencyListRepository.save(currencyList);
        currencyListSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/currency-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /currency-lists} : Updates an existing currencyList.
     *
     * @param currencyList the currencyList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencyList,
     * or with status {@code 400 (Bad Request)} if the currencyList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currencyList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/currency-lists")
    public ResponseEntity<CurrencyList> updateCurrencyList(@RequestBody CurrencyList currencyList) throws URISyntaxException {
        log.debug("REST request to update CurrencyList : {}", currencyList);
        if (currencyList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurrencyList result = currencyListRepository.save(currencyList);
        currencyListSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currencyList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /currency-lists} : get all the currencyLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currencyLists in body.
     */
    @GetMapping("/currency-lists")
    public List<CurrencyList> getAllCurrencyLists() {
        log.debug("REST request to get all CurrencyLists");
        return currencyListRepository.findAll();
    }

    /**
     * {@code GET  /currency-lists/:id} : get the "id" currencyList.
     *
     * @param id the id of the currencyList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currencyList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/currency-lists/{id}")
    public ResponseEntity<CurrencyList> getCurrencyList(@PathVariable Long id) {
        log.debug("REST request to get CurrencyList : {}", id);
        Optional<CurrencyList> currencyList = currencyListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(currencyList);
    }

    /**
     * {@code DELETE  /currency-lists/:id} : delete the "id" currencyList.
     *
     * @param id the id of the currencyList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/currency-lists/{id}")
    public ResponseEntity<Void> deleteCurrencyList(@PathVariable Long id) {
        log.debug("REST request to delete CurrencyList : {}", id);
        currencyListRepository.deleteById(id);
        currencyListSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/currency-lists?query=:query} : search for the currencyList corresponding
     * to the query.
     *
     * @param query the query of the currencyList search.
     * @return the result of the search.
     */
    @GetMapping("/_search/currency-lists")
    public List<CurrencyList> searchCurrencyLists(@RequestParam String query) {
        log.debug("REST request to search CurrencyLists for query {}", query);
        return StreamSupport
            .stream(currencyListSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
