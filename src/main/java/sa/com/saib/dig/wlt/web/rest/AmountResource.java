package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.Amount;
import sa.com.saib.dig.wlt.repository.AmountRepository;
import sa.com.saib.dig.wlt.repository.search.AmountSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.Amount}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AmountResource {

    private final Logger log = LoggerFactory.getLogger(AmountResource.class);

    private static final String ENTITY_NAME = "amount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmountRepository amountRepository;

    private final AmountSearchRepository amountSearchRepository;

    public AmountResource(AmountRepository amountRepository, AmountSearchRepository amountSearchRepository) {
        this.amountRepository = amountRepository;
        this.amountSearchRepository = amountSearchRepository;
    }

    /**
     * {@code POST  /amounts} : Create a new amount.
     *
     * @param amount the amount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amount, or with status {@code 400 (Bad Request)} if the amount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amounts")
    public ResponseEntity<Amount> createAmount(@RequestBody Amount amount) throws URISyntaxException {
        log.debug("REST request to save Amount : {}", amount);
        if (amount.getId() != null) {
            throw new BadRequestAlertException("A new amount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Amount result = amountRepository.save(amount);
        amountSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /amounts} : Updates an existing amount.
     *
     * @param amount the amount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amount,
     * or with status {@code 400 (Bad Request)} if the amount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amounts")
    public ResponseEntity<Amount> updateAmount(@RequestBody Amount amount) throws URISyntaxException {
        log.debug("REST request to update Amount : {}", amount);
        if (amount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Amount result = amountRepository.save(amount);
        amountSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, amount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /amounts} : get all the amounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amounts in body.
     */
    @GetMapping("/amounts")
    public List<Amount> getAllAmounts() {
        log.debug("REST request to get all Amounts");
        return amountRepository.findAll();
    }

    /**
     * {@code GET  /amounts/:id} : get the "id" amount.
     *
     * @param id the id of the amount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amounts/{id}")
    public ResponseEntity<Amount> getAmount(@PathVariable Long id) {
        log.debug("REST request to get Amount : {}", id);
        Optional<Amount> amount = amountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(amount);
    }

    /**
     * {@code DELETE  /amounts/:id} : delete the "id" amount.
     *
     * @param id the id of the amount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amounts/{id}")
    public ResponseEntity<Void> deleteAmount(@PathVariable Long id) {
        log.debug("REST request to delete Amount : {}", id);
        amountRepository.deleteById(id);
        amountSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/amounts?query=:query} : search for the amount corresponding
     * to the query.
     *
     * @param query the query of the amount search.
     * @return the result of the search.
     */
    @GetMapping("/_search/amounts")
    public List<Amount> searchAmounts(@RequestParam String query) {
        log.debug("REST request to search Amounts for query {}", query);
        return StreamSupport
            .stream(amountSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
