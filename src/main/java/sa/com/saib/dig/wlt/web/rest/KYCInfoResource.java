package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.KYCInfo;
import sa.com.saib.dig.wlt.repository.KYCInfoRepository;
import sa.com.saib.dig.wlt.repository.search.KYCInfoSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.KYCInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KYCInfoResource {

    private final Logger log = LoggerFactory.getLogger(KYCInfoResource.class);

    private static final String ENTITY_NAME = "kYCInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KYCInfoRepository kYCInfoRepository;

    private final KYCInfoSearchRepository kYCInfoSearchRepository;

    public KYCInfoResource(KYCInfoRepository kYCInfoRepository, KYCInfoSearchRepository kYCInfoSearchRepository) {
        this.kYCInfoRepository = kYCInfoRepository;
        this.kYCInfoSearchRepository = kYCInfoSearchRepository;
    }

    /**
     * {@code POST  /kyc-infos} : Create a new kYCInfo.
     *
     * @param kYCInfo the kYCInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kYCInfo, or with status {@code 400 (Bad Request)} if the kYCInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kyc-infos")
    public ResponseEntity<KYCInfo> createKYCInfo(@RequestBody KYCInfo kYCInfo) throws URISyntaxException {
        log.debug("REST request to save KYCInfo : {}", kYCInfo);
        if (kYCInfo.getId() != null) {
            throw new BadRequestAlertException("A new kYCInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KYCInfo result = kYCInfoRepository.save(kYCInfo);
        kYCInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/kyc-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kyc-infos} : Updates an existing kYCInfo.
     *
     * @param kYCInfo the kYCInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kYCInfo,
     * or with status {@code 400 (Bad Request)} if the kYCInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kYCInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kyc-infos")
    public ResponseEntity<KYCInfo> updateKYCInfo(@RequestBody KYCInfo kYCInfo) throws URISyntaxException {
        log.debug("REST request to update KYCInfo : {}", kYCInfo);
        if (kYCInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KYCInfo result = kYCInfoRepository.save(kYCInfo);
        kYCInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kYCInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kyc-infos} : get all the kYCInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kYCInfos in body.
     */
    @GetMapping("/kyc-infos")
    public List<KYCInfo> getAllKYCInfos() {
        log.debug("REST request to get all KYCInfos");
        return kYCInfoRepository.findAll();
    }

    /**
     * {@code GET  /kyc-infos/:id} : get the "id" kYCInfo.
     *
     * @param id the id of the kYCInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kYCInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kyc-infos/{id}")
    public ResponseEntity<KYCInfo> getKYCInfo(@PathVariable Long id) {
        log.debug("REST request to get KYCInfo : {}", id);
        Optional<KYCInfo> kYCInfo = kYCInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kYCInfo);
    }

    /**
     * {@code DELETE  /kyc-infos/:id} : delete the "id" kYCInfo.
     *
     * @param id the id of the kYCInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kyc-infos/{id}")
    public ResponseEntity<Void> deleteKYCInfo(@PathVariable Long id) {
        log.debug("REST request to delete KYCInfo : {}", id);
        kYCInfoRepository.deleteById(id);
        kYCInfoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/kyc-infos?query=:query} : search for the kYCInfo corresponding
     * to the query.
     *
     * @param query the query of the kYCInfo search.
     * @return the result of the search.
     */
    @GetMapping("/_search/kyc-infos")
    public List<KYCInfo> searchKYCInfos(@RequestParam String query) {
        log.debug("REST request to search KYCInfos for query {}", query);
        return StreamSupport
            .stream(kYCInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
