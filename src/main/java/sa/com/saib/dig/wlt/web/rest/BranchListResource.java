package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.BranchList;
import sa.com.saib.dig.wlt.repository.BranchListRepository;
import sa.com.saib.dig.wlt.repository.search.BranchListSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.BranchList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BranchListResource {

    private final Logger log = LoggerFactory.getLogger(BranchListResource.class);

    private static final String ENTITY_NAME = "branchList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BranchListRepository branchListRepository;

    private final BranchListSearchRepository branchListSearchRepository;

    public BranchListResource(BranchListRepository branchListRepository, BranchListSearchRepository branchListSearchRepository) {
        this.branchListRepository = branchListRepository;
        this.branchListSearchRepository = branchListSearchRepository;
    }

    /**
     * {@code POST  /branch-lists} : Create a new branchList.
     *
     * @param branchList the branchList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new branchList, or with status {@code 400 (Bad Request)} if the branchList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/branch-lists")
    public ResponseEntity<BranchList> createBranchList(@RequestBody BranchList branchList) throws URISyntaxException {
        log.debug("REST request to save BranchList : {}", branchList);
        if (branchList.getId() != null) {
            throw new BadRequestAlertException("A new branchList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BranchList result = branchListRepository.save(branchList);
        branchListSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/branch-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /branch-lists} : Updates an existing branchList.
     *
     * @param branchList the branchList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated branchList,
     * or with status {@code 400 (Bad Request)} if the branchList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the branchList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/branch-lists")
    public ResponseEntity<BranchList> updateBranchList(@RequestBody BranchList branchList) throws URISyntaxException {
        log.debug("REST request to update BranchList : {}", branchList);
        if (branchList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BranchList result = branchListRepository.save(branchList);
        branchListSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, branchList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /branch-lists} : get all the branchLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of branchLists in body.
     */
    @GetMapping("/branch-lists")
    public List<BranchList> getAllBranchLists() {
        log.debug("REST request to get all BranchLists");
        return branchListRepository.findAll();
    }

    /**
     * {@code GET  /branch-lists/:id} : get the "id" branchList.
     *
     * @param id the id of the branchList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the branchList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/branch-lists/{id}")
    public ResponseEntity<BranchList> getBranchList(@PathVariable Long id) {
        log.debug("REST request to get BranchList : {}", id);
        Optional<BranchList> branchList = branchListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(branchList);
    }

    /**
     * {@code DELETE  /branch-lists/:id} : delete the "id" branchList.
     *
     * @param id the id of the branchList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/branch-lists/{id}")
    public ResponseEntity<Void> deleteBranchList(@PathVariable Long id) {
        log.debug("REST request to delete BranchList : {}", id);
        branchListRepository.deleteById(id);
        branchListSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/branch-lists?query=:query} : search for the branchList corresponding
     * to the query.
     *
     * @param query the query of the branchList search.
     * @return the result of the search.
     */
    @GetMapping("/_search/branch-lists")
    public List<BranchList> searchBranchLists(@RequestParam String query) {
        log.debug("REST request to search BranchLists for query {}", query);
        return StreamSupport
            .stream(branchListSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
