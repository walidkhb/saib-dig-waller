package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.CountryList;
import sa.com.saib.dig.wlt.repository.CountryListRepository;
import sa.com.saib.dig.wlt.repository.search.CountryListSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.CountryList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CountryListResource {

    private final Logger log = LoggerFactory.getLogger(CountryListResource.class);

    private static final String ENTITY_NAME = "countryList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountryListRepository countryListRepository;

    private final CountryListSearchRepository countryListSearchRepository;

    public CountryListResource(CountryListRepository countryListRepository, CountryListSearchRepository countryListSearchRepository) {
        this.countryListRepository = countryListRepository;
        this.countryListSearchRepository = countryListSearchRepository;
    }

    /**
     * {@code POST  /country-lists} : Create a new countryList.
     *
     * @param countryList the countryList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countryList, or with status {@code 400 (Bad Request)} if the countryList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/country-lists")
    public ResponseEntity<CountryList> createCountryList(@RequestBody CountryList countryList) throws URISyntaxException {
        log.debug("REST request to save CountryList : {}", countryList);
        if (countryList.getId() != null) {
            throw new BadRequestAlertException("A new countryList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountryList result = countryListRepository.save(countryList);
        countryListSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/country-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /country-lists} : Updates an existing countryList.
     *
     * @param countryList the countryList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryList,
     * or with status {@code 400 (Bad Request)} if the countryList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countryList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/country-lists")
    public ResponseEntity<CountryList> updateCountryList(@RequestBody CountryList countryList) throws URISyntaxException {
        log.debug("REST request to update CountryList : {}", countryList);
        if (countryList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CountryList result = countryListRepository.save(countryList);
        countryListSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /country-lists} : get all the countryLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countryLists in body.
     */
    @GetMapping("/country-lists")
    public List<CountryList> getAllCountryLists() {
        log.debug("REST request to get all CountryLists");
        return countryListRepository.findAll();
    }

    /**
     * {@code GET  /country-lists/:id} : get the "id" countryList.
     *
     * @param id the id of the countryList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countryList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/country-lists/{id}")
    public ResponseEntity<CountryList> getCountryList(@PathVariable Long id) {
        log.debug("REST request to get CountryList : {}", id);
        Optional<CountryList> countryList = countryListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(countryList);
    }

    /**
     * {@code DELETE  /country-lists/:id} : delete the "id" countryList.
     *
     * @param id the id of the countryList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/country-lists/{id}")
    public ResponseEntity<Void> deleteCountryList(@PathVariable Long id) {
        log.debug("REST request to delete CountryList : {}", id);
        countryListRepository.deleteById(id);
        countryListSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/country-lists?query=:query} : search for the countryList corresponding
     * to the query.
     *
     * @param query the query of the countryList search.
     * @return the result of the search.
     */
    @GetMapping("/_search/country-lists")
    public List<CountryList> searchCountryLists(@RequestParam String query) {
        log.debug("REST request to search CountryLists for query {}", query);
        return StreamSupport
            .stream(countryListSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
