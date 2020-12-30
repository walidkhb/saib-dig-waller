package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.CountryCodeList;
import sa.com.saib.dig.wlt.repository.CountryCodeListRepository;
import sa.com.saib.dig.wlt.repository.search.CountryCodeListSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.CountryCodeList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CountryCodeListResource {

    private final Logger log = LoggerFactory.getLogger(CountryCodeListResource.class);

    private static final String ENTITY_NAME = "countryCodeList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountryCodeListRepository countryCodeListRepository;

    private final CountryCodeListSearchRepository countryCodeListSearchRepository;

    public CountryCodeListResource(CountryCodeListRepository countryCodeListRepository, CountryCodeListSearchRepository countryCodeListSearchRepository) {
        this.countryCodeListRepository = countryCodeListRepository;
        this.countryCodeListSearchRepository = countryCodeListSearchRepository;
    }

    /**
     * {@code POST  /country-code-lists} : Create a new countryCodeList.
     *
     * @param countryCodeList the countryCodeList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countryCodeList, or with status {@code 400 (Bad Request)} if the countryCodeList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/country-code-lists")
    public ResponseEntity<CountryCodeList> createCountryCodeList(@RequestBody CountryCodeList countryCodeList) throws URISyntaxException {
        log.debug("REST request to save CountryCodeList : {}", countryCodeList);
        if (countryCodeList.getId() != null) {
            throw new BadRequestAlertException("A new countryCodeList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountryCodeList result = countryCodeListRepository.save(countryCodeList);
        countryCodeListSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/country-code-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /country-code-lists} : Updates an existing countryCodeList.
     *
     * @param countryCodeList the countryCodeList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryCodeList,
     * or with status {@code 400 (Bad Request)} if the countryCodeList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countryCodeList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/country-code-lists")
    public ResponseEntity<CountryCodeList> updateCountryCodeList(@RequestBody CountryCodeList countryCodeList) throws URISyntaxException {
        log.debug("REST request to update CountryCodeList : {}", countryCodeList);
        if (countryCodeList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CountryCodeList result = countryCodeListRepository.save(countryCodeList);
        countryCodeListSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryCodeList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /country-code-lists} : get all the countryCodeLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countryCodeLists in body.
     */
    @GetMapping("/country-code-lists")
    public List<CountryCodeList> getAllCountryCodeLists() {
        log.debug("REST request to get all CountryCodeLists");
        return countryCodeListRepository.findAll();
    }

    /**
     * {@code GET  /country-code-lists/:id} : get the "id" countryCodeList.
     *
     * @param id the id of the countryCodeList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countryCodeList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/country-code-lists/{id}")
    public ResponseEntity<CountryCodeList> getCountryCodeList(@PathVariable Long id) {
        log.debug("REST request to get CountryCodeList : {}", id);
        Optional<CountryCodeList> countryCodeList = countryCodeListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(countryCodeList);
    }

    /**
     * {@code DELETE  /country-code-lists/:id} : delete the "id" countryCodeList.
     *
     * @param id the id of the countryCodeList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/country-code-lists/{id}")
    public ResponseEntity<Void> deleteCountryCodeList(@PathVariable Long id) {
        log.debug("REST request to delete CountryCodeList : {}", id);
        countryCodeListRepository.deleteById(id);
        countryCodeListSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/country-code-lists?query=:query} : search for the countryCodeList corresponding
     * to the query.
     *
     * @param query the query of the countryCodeList search.
     * @return the result of the search.
     */
    @GetMapping("/_search/country-code-lists")
    public List<CountryCodeList> searchCountryCodeLists(@RequestParam String query) {
        log.debug("REST request to search CountryCodeLists for query {}", query);
        return StreamSupport
            .stream(countryCodeListSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
