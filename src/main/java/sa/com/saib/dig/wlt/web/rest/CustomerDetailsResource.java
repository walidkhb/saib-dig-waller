package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.CustomerDetails;
import sa.com.saib.dig.wlt.repository.CustomerDetailsRepository;
import sa.com.saib.dig.wlt.repository.search.CustomerDetailsSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.CustomerDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CustomerDetailsResource.class);

    private static final String ENTITY_NAME = "customerDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerDetailsRepository customerDetailsRepository;

    private final CustomerDetailsSearchRepository customerDetailsSearchRepository;

    public CustomerDetailsResource(CustomerDetailsRepository customerDetailsRepository, CustomerDetailsSearchRepository customerDetailsSearchRepository) {
        this.customerDetailsRepository = customerDetailsRepository;
        this.customerDetailsSearchRepository = customerDetailsSearchRepository;
    }

    /**
     * {@code POST  /customer-details} : Create a new customerDetails.
     *
     * @param customerDetails the customerDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerDetails, or with status {@code 400 (Bad Request)} if the customerDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-details")
    public ResponseEntity<CustomerDetails> createCustomerDetails(@Valid @RequestBody CustomerDetails customerDetails) throws URISyntaxException {
        log.debug("REST request to save CustomerDetails : {}", customerDetails);
        if (customerDetails.getId() != null) {
            throw new BadRequestAlertException("A new customerDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerDetails result = customerDetailsRepository.save(customerDetails);
        customerDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/customer-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-details} : Updates an existing customerDetails.
     *
     * @param customerDetails the customerDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerDetails,
     * or with status {@code 400 (Bad Request)} if the customerDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-details")
    public ResponseEntity<CustomerDetails> updateCustomerDetails(@Valid @RequestBody CustomerDetails customerDetails) throws URISyntaxException {
        log.debug("REST request to update CustomerDetails : {}", customerDetails);
        if (customerDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerDetails result = customerDetailsRepository.save(customerDetails);
        customerDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-details} : get all the customerDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerDetails in body.
     */
    @GetMapping("/customer-details")
    public List<CustomerDetails> getAllCustomerDetails() {
        log.debug("REST request to get all CustomerDetails");
        return customerDetailsRepository.findAll();
    }

    /**
     * {@code GET  /customer-details/:id} : get the "id" customerDetails.
     *
     * @param id the id of the customerDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-details/{id}")
    public ResponseEntity<CustomerDetails> getCustomerDetails(@PathVariable Long id) {
        log.debug("REST request to get CustomerDetails : {}", id);
        Optional<CustomerDetails> customerDetails = customerDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerDetails);
    }

    /**
     * {@code DELETE  /customer-details/:id} : delete the "id" customerDetails.
     *
     * @param id the id of the customerDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-details/{id}")
    public ResponseEntity<Void> deleteCustomerDetails(@PathVariable Long id) {
        log.debug("REST request to delete CustomerDetails : {}", id);
        customerDetailsRepository.deleteById(id);
        customerDetailsSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/customer-details?query=:query} : search for the customerDetails corresponding
     * to the query.
     *
     * @param query the query of the customerDetails search.
     * @return the result of the search.
     */
    @GetMapping("/_search/customer-details")
    public List<CustomerDetails> searchCustomerDetails(@RequestParam String query) {
        log.debug("REST request to search CustomerDetails for query {}", query);
        return StreamSupport
            .stream(customerDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
