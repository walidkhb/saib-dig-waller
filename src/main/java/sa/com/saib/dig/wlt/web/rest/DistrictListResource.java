package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.DistrictList;
import sa.com.saib.dig.wlt.repository.DistrictListRepository;
import sa.com.saib.dig.wlt.repository.search.DistrictListSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.DistrictList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DistrictListResource {

    private final Logger log = LoggerFactory.getLogger(DistrictListResource.class);

    private static final String ENTITY_NAME = "districtList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictListRepository districtListRepository;

    private final DistrictListSearchRepository districtListSearchRepository;

    public DistrictListResource(DistrictListRepository districtListRepository, DistrictListSearchRepository districtListSearchRepository) {
        this.districtListRepository = districtListRepository;
        this.districtListSearchRepository = districtListSearchRepository;
    }

    /**
     * {@code POST  /district-lists} : Create a new districtList.
     *
     * @param districtList the districtList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districtList, or with status {@code 400 (Bad Request)} if the districtList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/district-lists")
    public ResponseEntity<DistrictList> createDistrictList(@RequestBody DistrictList districtList) throws URISyntaxException {
        log.debug("REST request to save DistrictList : {}", districtList);
        if (districtList.getId() != null) {
            throw new BadRequestAlertException("A new districtList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistrictList result = districtListRepository.save(districtList);
        districtListSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/district-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /district-lists} : Updates an existing districtList.
     *
     * @param districtList the districtList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtList,
     * or with status {@code 400 (Bad Request)} if the districtList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districtList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/district-lists")
    public ResponseEntity<DistrictList> updateDistrictList(@RequestBody DistrictList districtList) throws URISyntaxException {
        log.debug("REST request to update DistrictList : {}", districtList);
        if (districtList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DistrictList result = districtListRepository.save(districtList);
        districtListSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, districtList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /district-lists} : get all the districtLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districtLists in body.
     */
    @GetMapping("/district-lists")
    public List<DistrictList> getAllDistrictLists() {
        log.debug("REST request to get all DistrictLists");
        return districtListRepository.findAll();
    }

    /**
     * {@code GET  /district-lists/:id} : get the "id" districtList.
     *
     * @param id the id of the districtList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the districtList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/district-lists/{id}")
    public ResponseEntity<DistrictList> getDistrictList(@PathVariable Long id) {
        log.debug("REST request to get DistrictList : {}", id);
        Optional<DistrictList> districtList = districtListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(districtList);
    }

    /**
     * {@code DELETE  /district-lists/:id} : delete the "id" districtList.
     *
     * @param id the id of the districtList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/district-lists/{id}")
    public ResponseEntity<Void> deleteDistrictList(@PathVariable Long id) {
        log.debug("REST request to delete DistrictList : {}", id);
        districtListRepository.deleteById(id);
        districtListSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/district-lists?query=:query} : search for the districtList corresponding
     * to the query.
     *
     * @param query the query of the districtList search.
     * @return the result of the search.
     */
    @GetMapping("/_search/district-lists")
    public List<DistrictList> searchDistrictLists(@RequestParam String query) {
        log.debug("REST request to search DistrictLists for query {}", query);
        return StreamSupport
            .stream(districtListSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
