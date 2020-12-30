package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.CustomerPreference;
import sa.com.saib.dig.wlt.repository.CustomerPreferenceRepository;
import sa.com.saib.dig.wlt.repository.search.CustomerPreferenceSearchRepository;
import sa.com.saib.dig.wlt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.CustomerPreference}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerPreferenceResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPreferenceResource.class);

    private static final String ENTITY_NAME = "customerPreference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPreferenceRepository customerPreferenceRepository;

    private final CustomerPreferenceSearchRepository customerPreferenceSearchRepository;

    public CustomerPreferenceResource(CustomerPreferenceRepository customerPreferenceRepository, CustomerPreferenceSearchRepository customerPreferenceSearchRepository) {
        this.customerPreferenceRepository = customerPreferenceRepository;
        this.customerPreferenceSearchRepository = customerPreferenceSearchRepository;
    }

    /**
     * {@code POST  /customer-preferences} : Create a new customerPreference.
     *
     * @param customerPreference the customerPreference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPreference, or with status {@code 400 (Bad Request)} if the customerPreference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-preferences")
    public ResponseEntity<CustomerPreference> createCustomerPreference(@Valid @RequestBody CustomerPreference customerPreference) throws URISyntaxException {
        log.debug("REST request to save CustomerPreference : {}", customerPreference);
        if (customerPreference.getId() != null) {
            throw new BadRequestAlertException("A new customerPreference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPreference result = customerPreferenceRepository.save(customerPreference);
        customerPreferenceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/customer-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-preferences} : Updates an existing customerPreference.
     *
     * @param customerPreference the customerPreference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPreference,
     * or with status {@code 400 (Bad Request)} if the customerPreference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPreference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-preferences")
    public ResponseEntity<CustomerPreference> updateCustomerPreference(@Valid @RequestBody CustomerPreference customerPreference) throws URISyntaxException {
        log.debug("REST request to update CustomerPreference : {}", customerPreference);
        if (customerPreference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerPreference result = customerPreferenceRepository.save(customerPreference);
        customerPreferenceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPreference.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-preferences} : get all the customerPreferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPreferences in body.
     */
    @GetMapping("/customer-preferences")
    public List<CustomerPreference> getAllCustomerPreferences() {
        log.debug("REST request to get all CustomerPreferences");
        return customerPreferenceRepository.findAll();
    }

    /**
     * {@code GET  /customer-preferences/:id} : get the "id" customerPreference.
     *
     * @param id the id of the customerPreference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPreference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-preferences/{id}")
    public ResponseEntity<CustomerPreference> getCustomerPreference(@PathVariable Long id) {
        log.debug("REST request to get CustomerPreference : {}", id);
        Optional<CustomerPreference> customerPreference = customerPreferenceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerPreference);
    }

    /**
     * {@code DELETE  /customer-preferences/:id} : delete the "id" customerPreference.
     *
     * @param id the id of the customerPreference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-preferences/{id}")
    public ResponseEntity<Void> deleteCustomerPreference(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPreference : {}", id);
        customerPreferenceRepository.deleteById(id);
        customerPreferenceSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/customer-preferences?query=:query} : search for the customerPreference corresponding
     * to the query.
     *
     * @param query the query of the customerPreference search.
     * @return the result of the search.
     */
    @GetMapping("/_search/customer-preferences")
    public List<CustomerPreference> searchCustomerPreferences(@RequestParam String query) {
        log.debug("REST request to search CustomerPreferences for query {}", query);
        return StreamSupport
            .stream(customerPreferenceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
