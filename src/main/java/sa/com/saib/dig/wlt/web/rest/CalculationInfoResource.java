package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.CalculationInfo;
import sa.com.saib.dig.wlt.repository.CalculationInfoRepository;
import sa.com.saib.dig.wlt.repository.search.CalculationInfoSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.CalculationInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CalculationInfoResource {

    private final Logger log = LoggerFactory.getLogger(CalculationInfoResource.class);

    private static final String ENTITY_NAME = "calculationInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalculationInfoRepository calculationInfoRepository;

    private final CalculationInfoSearchRepository calculationInfoSearchRepository;

    public CalculationInfoResource(CalculationInfoRepository calculationInfoRepository, CalculationInfoSearchRepository calculationInfoSearchRepository) {
        this.calculationInfoRepository = calculationInfoRepository;
        this.calculationInfoSearchRepository = calculationInfoSearchRepository;
    }

    /**
     * {@code POST  /calculation-infos} : Create a new calculationInfo.
     *
     * @param calculationInfo the calculationInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calculationInfo, or with status {@code 400 (Bad Request)} if the calculationInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calculation-infos")
    public ResponseEntity<CalculationInfo> createCalculationInfo(@RequestBody CalculationInfo calculationInfo) throws URISyntaxException {
        log.debug("REST request to save CalculationInfo : {}", calculationInfo);
        if (calculationInfo.getId() != null) {
            throw new BadRequestAlertException("A new calculationInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalculationInfo result = calculationInfoRepository.save(calculationInfo);
        calculationInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/calculation-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calculation-infos} : Updates an existing calculationInfo.
     *
     * @param calculationInfo the calculationInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calculationInfo,
     * or with status {@code 400 (Bad Request)} if the calculationInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calculationInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calculation-infos")
    public ResponseEntity<CalculationInfo> updateCalculationInfo(@RequestBody CalculationInfo calculationInfo) throws URISyntaxException {
        log.debug("REST request to update CalculationInfo : {}", calculationInfo);
        if (calculationInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalculationInfo result = calculationInfoRepository.save(calculationInfo);
        calculationInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calculationInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /calculation-infos} : get all the calculationInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calculationInfos in body.
     */
    @GetMapping("/calculation-infos")
    public List<CalculationInfo> getAllCalculationInfos() {
        log.debug("REST request to get all CalculationInfos");
        return calculationInfoRepository.findAll();
    }

    /**
     * {@code GET  /calculation-infos/:id} : get the "id" calculationInfo.
     *
     * @param id the id of the calculationInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calculationInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calculation-infos/{id}")
    public ResponseEntity<CalculationInfo> getCalculationInfo(@PathVariable Long id) {
        log.debug("REST request to get CalculationInfo : {}", id);
        Optional<CalculationInfo> calculationInfo = calculationInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(calculationInfo);
    }

    /**
     * {@code DELETE  /calculation-infos/:id} : delete the "id" calculationInfo.
     *
     * @param id the id of the calculationInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calculation-infos/{id}")
    public ResponseEntity<Void> deleteCalculationInfo(@PathVariable Long id) {
        log.debug("REST request to delete CalculationInfo : {}", id);
        calculationInfoRepository.deleteById(id);
        calculationInfoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/calculation-infos?query=:query} : search for the calculationInfo corresponding
     * to the query.
     *
     * @param query the query of the calculationInfo search.
     * @return the result of the search.
     */
    @GetMapping("/_search/calculation-infos")
    public List<CalculationInfo> searchCalculationInfos(@RequestParam String query) {
        log.debug("REST request to search CalculationInfos for query {}", query);
        return StreamSupport
            .stream(calculationInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
