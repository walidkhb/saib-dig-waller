package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.ChargeAmount;
import sa.com.saib.dig.wlt.repository.ChargeAmountRepository;
import sa.com.saib.dig.wlt.repository.search.ChargeAmountSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.ChargeAmount}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChargeAmountResource {

    private final Logger log = LoggerFactory.getLogger(ChargeAmountResource.class);

    private static final String ENTITY_NAME = "chargeAmount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChargeAmountRepository chargeAmountRepository;

    private final ChargeAmountSearchRepository chargeAmountSearchRepository;

    public ChargeAmountResource(ChargeAmountRepository chargeAmountRepository, ChargeAmountSearchRepository chargeAmountSearchRepository) {
        this.chargeAmountRepository = chargeAmountRepository;
        this.chargeAmountSearchRepository = chargeAmountSearchRepository;
    }

    /**
     * {@code POST  /charge-amounts} : Create a new chargeAmount.
     *
     * @param chargeAmount the chargeAmount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chargeAmount, or with status {@code 400 (Bad Request)} if the chargeAmount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/charge-amounts")
    public ResponseEntity<ChargeAmount> createChargeAmount(@RequestBody ChargeAmount chargeAmount) throws URISyntaxException {
        log.debug("REST request to save ChargeAmount : {}", chargeAmount);
        if (chargeAmount.getId() != null) {
            throw new BadRequestAlertException("A new chargeAmount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChargeAmount result = chargeAmountRepository.save(chargeAmount);
        chargeAmountSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/charge-amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /charge-amounts} : Updates an existing chargeAmount.
     *
     * @param chargeAmount the chargeAmount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chargeAmount,
     * or with status {@code 400 (Bad Request)} if the chargeAmount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chargeAmount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/charge-amounts")
    public ResponseEntity<ChargeAmount> updateChargeAmount(@RequestBody ChargeAmount chargeAmount) throws URISyntaxException {
        log.debug("REST request to update ChargeAmount : {}", chargeAmount);
        if (chargeAmount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChargeAmount result = chargeAmountRepository.save(chargeAmount);
        chargeAmountSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chargeAmount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /charge-amounts} : get all the chargeAmounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chargeAmounts in body.
     */
    @GetMapping("/charge-amounts")
    public List<ChargeAmount> getAllChargeAmounts() {
        log.debug("REST request to get all ChargeAmounts");
        return chargeAmountRepository.findAll();
    }

    /**
     * {@code GET  /charge-amounts/:id} : get the "id" chargeAmount.
     *
     * @param id the id of the chargeAmount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chargeAmount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/charge-amounts/{id}")
    public ResponseEntity<ChargeAmount> getChargeAmount(@PathVariable Long id) {
        log.debug("REST request to get ChargeAmount : {}", id);
        Optional<ChargeAmount> chargeAmount = chargeAmountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chargeAmount);
    }

    /**
     * {@code DELETE  /charge-amounts/:id} : delete the "id" chargeAmount.
     *
     * @param id the id of the chargeAmount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/charge-amounts/{id}")
    public ResponseEntity<Void> deleteChargeAmount(@PathVariable Long id) {
        log.debug("REST request to delete ChargeAmount : {}", id);
        chargeAmountRepository.deleteById(id);
        chargeAmountSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/charge-amounts?query=:query} : search for the chargeAmount corresponding
     * to the query.
     *
     * @param query the query of the chargeAmount search.
     * @return the result of the search.
     */
    @GetMapping("/_search/charge-amounts")
    public List<ChargeAmount> searchChargeAmounts(@RequestParam String query) {
        log.debug("REST request to search ChargeAmounts for query {}", query);
        return StreamSupport
            .stream(chargeAmountSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
