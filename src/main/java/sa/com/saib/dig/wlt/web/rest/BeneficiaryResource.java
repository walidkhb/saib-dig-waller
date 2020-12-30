package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.Beneficiary;
import sa.com.saib.dig.wlt.repository.BeneficiaryRepository;
import sa.com.saib.dig.wlt.repository.search.BeneficiarySearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.Beneficiary}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BeneficiaryResource {

    private final Logger log = LoggerFactory.getLogger(BeneficiaryResource.class);

    private static final String ENTITY_NAME = "beneficiary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeneficiaryRepository beneficiaryRepository;

    private final BeneficiarySearchRepository beneficiarySearchRepository;

    public BeneficiaryResource(BeneficiaryRepository beneficiaryRepository, BeneficiarySearchRepository beneficiarySearchRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
        this.beneficiarySearchRepository = beneficiarySearchRepository;
    }

    /**
     * {@code POST  /beneficiaries} : Create a new beneficiary.
     *
     * @param beneficiary the beneficiary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beneficiary, or with status {@code 400 (Bad Request)} if the beneficiary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beneficiaries")
    public ResponseEntity<Beneficiary> createBeneficiary(@RequestBody Beneficiary beneficiary) throws URISyntaxException {
        log.debug("REST request to save Beneficiary : {}", beneficiary);
        if (beneficiary.getId() != null) {
            throw new BadRequestAlertException("A new beneficiary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Beneficiary result = beneficiaryRepository.save(beneficiary);
        beneficiarySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/beneficiaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beneficiaries} : Updates an existing beneficiary.
     *
     * @param beneficiary the beneficiary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beneficiary,
     * or with status {@code 400 (Bad Request)} if the beneficiary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beneficiary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beneficiaries")
    public ResponseEntity<Beneficiary> updateBeneficiary(@RequestBody Beneficiary beneficiary) throws URISyntaxException {
        log.debug("REST request to update Beneficiary : {}", beneficiary);
        if (beneficiary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Beneficiary result = beneficiaryRepository.save(beneficiary);
        beneficiarySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiary.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /beneficiaries} : get all the beneficiaries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beneficiaries in body.
     */
    @GetMapping("/beneficiaries")
    public List<Beneficiary> getAllBeneficiaries() {
        log.debug("REST request to get all Beneficiaries");
        return beneficiaryRepository.findAll();
    }

    /**
     * {@code GET  /beneficiaries/:id} : get the "id" beneficiary.
     *
     * @param id the id of the beneficiary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beneficiary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beneficiaries/{id}")
    public ResponseEntity<Beneficiary> getBeneficiary(@PathVariable Long id) {
        log.debug("REST request to get Beneficiary : {}", id);
        Optional<Beneficiary> beneficiary = beneficiaryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beneficiary);
    }

    /**
     * {@code DELETE  /beneficiaries/:id} : delete the "id" beneficiary.
     *
     * @param id the id of the beneficiary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beneficiaries/{id}")
    public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id) {
        log.debug("REST request to delete Beneficiary : {}", id);
        beneficiaryRepository.deleteById(id);
        beneficiarySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/beneficiaries?query=:query} : search for the beneficiary corresponding
     * to the query.
     *
     * @param query the query of the beneficiary search.
     * @return the result of the search.
     */
    @GetMapping("/_search/beneficiaries")
    public List<Beneficiary> searchBeneficiaries(@RequestParam String query) {
        log.debug("REST request to search Beneficiaries for query {}", query);
        return StreamSupport
            .stream(beneficiarySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
