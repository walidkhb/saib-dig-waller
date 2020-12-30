package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.BeneficiaryBank;
import sa.com.saib.dig.wlt.repository.BeneficiaryBankRepository;
import sa.com.saib.dig.wlt.repository.search.BeneficiaryBankSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.BeneficiaryBank}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BeneficiaryBankResource {

    private final Logger log = LoggerFactory.getLogger(BeneficiaryBankResource.class);

    private static final String ENTITY_NAME = "beneficiaryBank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeneficiaryBankRepository beneficiaryBankRepository;

    private final BeneficiaryBankSearchRepository beneficiaryBankSearchRepository;

    public BeneficiaryBankResource(BeneficiaryBankRepository beneficiaryBankRepository, BeneficiaryBankSearchRepository beneficiaryBankSearchRepository) {
        this.beneficiaryBankRepository = beneficiaryBankRepository;
        this.beneficiaryBankSearchRepository = beneficiaryBankSearchRepository;
    }

    /**
     * {@code POST  /beneficiary-banks} : Create a new beneficiaryBank.
     *
     * @param beneficiaryBank the beneficiaryBank to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beneficiaryBank, or with status {@code 400 (Bad Request)} if the beneficiaryBank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beneficiary-banks")
    public ResponseEntity<BeneficiaryBank> createBeneficiaryBank(@RequestBody BeneficiaryBank beneficiaryBank) throws URISyntaxException {
        log.debug("REST request to save BeneficiaryBank : {}", beneficiaryBank);
        if (beneficiaryBank.getId() != null) {
            throw new BadRequestAlertException("A new beneficiaryBank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BeneficiaryBank result = beneficiaryBankRepository.save(beneficiaryBank);
        beneficiaryBankSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/beneficiary-banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beneficiary-banks} : Updates an existing beneficiaryBank.
     *
     * @param beneficiaryBank the beneficiaryBank to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beneficiaryBank,
     * or with status {@code 400 (Bad Request)} if the beneficiaryBank is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beneficiaryBank couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beneficiary-banks")
    public ResponseEntity<BeneficiaryBank> updateBeneficiaryBank(@RequestBody BeneficiaryBank beneficiaryBank) throws URISyntaxException {
        log.debug("REST request to update BeneficiaryBank : {}", beneficiaryBank);
        if (beneficiaryBank.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BeneficiaryBank result = beneficiaryBankRepository.save(beneficiaryBank);
        beneficiaryBankSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiaryBank.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /beneficiary-banks} : get all the beneficiaryBanks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beneficiaryBanks in body.
     */
    @GetMapping("/beneficiary-banks")
    public List<BeneficiaryBank> getAllBeneficiaryBanks() {
        log.debug("REST request to get all BeneficiaryBanks");
        return beneficiaryBankRepository.findAll();
    }

    /**
     * {@code GET  /beneficiary-banks/:id} : get the "id" beneficiaryBank.
     *
     * @param id the id of the beneficiaryBank to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beneficiaryBank, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beneficiary-banks/{id}")
    public ResponseEntity<BeneficiaryBank> getBeneficiaryBank(@PathVariable Long id) {
        log.debug("REST request to get BeneficiaryBank : {}", id);
        Optional<BeneficiaryBank> beneficiaryBank = beneficiaryBankRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beneficiaryBank);
    }

    /**
     * {@code DELETE  /beneficiary-banks/:id} : delete the "id" beneficiaryBank.
     *
     * @param id the id of the beneficiaryBank to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beneficiary-banks/{id}")
    public ResponseEntity<Void> deleteBeneficiaryBank(@PathVariable Long id) {
        log.debug("REST request to delete BeneficiaryBank : {}", id);
        beneficiaryBankRepository.deleteById(id);
        beneficiaryBankSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/beneficiary-banks?query=:query} : search for the beneficiaryBank corresponding
     * to the query.
     *
     * @param query the query of the beneficiaryBank search.
     * @return the result of the search.
     */
    @GetMapping("/_search/beneficiary-banks")
    public List<BeneficiaryBank> searchBeneficiaryBanks(@RequestParam String query) {
        log.debug("REST request to search BeneficiaryBanks for query {}", query);
        return StreamSupport
            .stream(beneficiaryBankSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
