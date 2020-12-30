package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.Debtor;
import sa.com.saib.dig.wlt.repository.DebtorRepository;
import sa.com.saib.dig.wlt.repository.search.DebtorSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.Debtor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DebtorResource {

    private final Logger log = LoggerFactory.getLogger(DebtorResource.class);

    private static final String ENTITY_NAME = "debtor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DebtorRepository debtorRepository;

    private final DebtorSearchRepository debtorSearchRepository;

    public DebtorResource(DebtorRepository debtorRepository, DebtorSearchRepository debtorSearchRepository) {
        this.debtorRepository = debtorRepository;
        this.debtorSearchRepository = debtorSearchRepository;
    }

    /**
     * {@code POST  /debtors} : Create a new debtor.
     *
     * @param debtor the debtor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new debtor, or with status {@code 400 (Bad Request)} if the debtor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/debtors")
    public ResponseEntity<Debtor> createDebtor(@RequestBody Debtor debtor) throws URISyntaxException {
        log.debug("REST request to save Debtor : {}", debtor);
        if (debtor.getId() != null) {
            throw new BadRequestAlertException("A new debtor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Debtor result = debtorRepository.save(debtor);
        debtorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/debtors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /debtors} : Updates an existing debtor.
     *
     * @param debtor the debtor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated debtor,
     * or with status {@code 400 (Bad Request)} if the debtor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the debtor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/debtors")
    public ResponseEntity<Debtor> updateDebtor(@RequestBody Debtor debtor) throws URISyntaxException {
        log.debug("REST request to update Debtor : {}", debtor);
        if (debtor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Debtor result = debtorRepository.save(debtor);
        debtorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, debtor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /debtors} : get all the debtors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of debtors in body.
     */
    @GetMapping("/debtors")
    public List<Debtor> getAllDebtors() {
        log.debug("REST request to get all Debtors");
        return debtorRepository.findAll();
    }

    /**
     * {@code GET  /debtors/:id} : get the "id" debtor.
     *
     * @param id the id of the debtor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the debtor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/debtors/{id}")
    public ResponseEntity<Debtor> getDebtor(@PathVariable Long id) {
        log.debug("REST request to get Debtor : {}", id);
        Optional<Debtor> debtor = debtorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(debtor);
    }

    /**
     * {@code DELETE  /debtors/:id} : delete the "id" debtor.
     *
     * @param id the id of the debtor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/debtors/{id}")
    public ResponseEntity<Void> deleteDebtor(@PathVariable Long id) {
        log.debug("REST request to delete Debtor : {}", id);
        debtorRepository.deleteById(id);
        debtorSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/debtors?query=:query} : search for the debtor corresponding
     * to the query.
     *
     * @param query the query of the debtor search.
     * @return the result of the search.
     */
    @GetMapping("/_search/debtors")
    public List<Debtor> searchDebtors(@RequestParam String query) {
        log.debug("REST request to search Debtors for query {}", query);
        return StreamSupport
            .stream(debtorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
