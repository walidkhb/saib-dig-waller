package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.VersionList;
import sa.com.saib.dig.wlt.repository.VersionListRepository;
import sa.com.saib.dig.wlt.repository.search.VersionListSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.VersionList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VersionListResource {

    private final Logger log = LoggerFactory.getLogger(VersionListResource.class);

    private static final String ENTITY_NAME = "versionList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VersionListRepository versionListRepository;

    private final VersionListSearchRepository versionListSearchRepository;

    public VersionListResource(VersionListRepository versionListRepository, VersionListSearchRepository versionListSearchRepository) {
        this.versionListRepository = versionListRepository;
        this.versionListSearchRepository = versionListSearchRepository;
    }

    /**
     * {@code POST  /version-lists} : Create a new versionList.
     *
     * @param versionList the versionList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new versionList, or with status {@code 400 (Bad Request)} if the versionList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/version-lists")
    public ResponseEntity<VersionList> createVersionList(@RequestBody VersionList versionList) throws URISyntaxException {
        log.debug("REST request to save VersionList : {}", versionList);
        if (versionList.getId() != null) {
            throw new BadRequestAlertException("A new versionList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VersionList result = versionListRepository.save(versionList);
        versionListSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/version-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /version-lists} : Updates an existing versionList.
     *
     * @param versionList the versionList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated versionList,
     * or with status {@code 400 (Bad Request)} if the versionList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the versionList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/version-lists")
    public ResponseEntity<VersionList> updateVersionList(@RequestBody VersionList versionList) throws URISyntaxException {
        log.debug("REST request to update VersionList : {}", versionList);
        if (versionList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VersionList result = versionListRepository.save(versionList);
        versionListSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, versionList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /version-lists} : get all the versionLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of versionLists in body.
     */
    @GetMapping("/version-lists")
    public List<VersionList> getAllVersionLists() {
        log.debug("REST request to get all VersionLists");
        return versionListRepository.findAll();
    }

    /**
     * {@code GET  /version-lists/:id} : get the "id" versionList.
     *
     * @param id the id of the versionList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the versionList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/version-lists/{id}")
    public ResponseEntity<VersionList> getVersionList(@PathVariable Long id) {
        log.debug("REST request to get VersionList : {}", id);
        Optional<VersionList> versionList = versionListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(versionList);
    }

    /**
     * {@code DELETE  /version-lists/:id} : delete the "id" versionList.
     *
     * @param id the id of the versionList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/version-lists/{id}")
    public ResponseEntity<Void> deleteVersionList(@PathVariable Long id) {
        log.debug("REST request to delete VersionList : {}", id);
        versionListRepository.deleteById(id);
        versionListSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/version-lists?query=:query} : search for the versionList corresponding
     * to the query.
     *
     * @param query the query of the versionList search.
     * @return the result of the search.
     */
    @GetMapping("/_search/version-lists")
    public List<VersionList> searchVersionLists(@RequestParam String query) {
        log.debug("REST request to search VersionLists for query {}", query);
        return StreamSupport
            .stream(versionListSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
