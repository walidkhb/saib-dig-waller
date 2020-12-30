package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.domain.AccountScheme;
import sa.com.saib.dig.wlt.repository.AccountSchemeRepository;
import sa.com.saib.dig.wlt.repository.search.AccountSchemeSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.dig.wlt.domain.AccountScheme}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AccountSchemeResource {

    private final Logger log = LoggerFactory.getLogger(AccountSchemeResource.class);

    private static final String ENTITY_NAME = "accountScheme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountSchemeRepository accountSchemeRepository;

    private final AccountSchemeSearchRepository accountSchemeSearchRepository;

    public AccountSchemeResource(AccountSchemeRepository accountSchemeRepository, AccountSchemeSearchRepository accountSchemeSearchRepository) {
        this.accountSchemeRepository = accountSchemeRepository;
        this.accountSchemeSearchRepository = accountSchemeSearchRepository;
    }

    /**
     * {@code POST  /account-schemes} : Create a new accountScheme.
     *
     * @param accountScheme the accountScheme to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountScheme, or with status {@code 400 (Bad Request)} if the accountScheme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-schemes")
    public ResponseEntity<AccountScheme> createAccountScheme(@RequestBody AccountScheme accountScheme) throws URISyntaxException {
        log.debug("REST request to save AccountScheme : {}", accountScheme);
        if (accountScheme.getId() != null) {
            throw new BadRequestAlertException("A new accountScheme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountScheme result = accountSchemeRepository.save(accountScheme);
        accountSchemeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/account-schemes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-schemes} : Updates an existing accountScheme.
     *
     * @param accountScheme the accountScheme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountScheme,
     * or with status {@code 400 (Bad Request)} if the accountScheme is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountScheme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-schemes")
    public ResponseEntity<AccountScheme> updateAccountScheme(@RequestBody AccountScheme accountScheme) throws URISyntaxException {
        log.debug("REST request to update AccountScheme : {}", accountScheme);
        if (accountScheme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountScheme result = accountSchemeRepository.save(accountScheme);
        accountSchemeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountScheme.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-schemes} : get all the accountSchemes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountSchemes in body.
     */
    @GetMapping("/account-schemes")
    public List<AccountScheme> getAllAccountSchemes() {
        log.debug("REST request to get all AccountSchemes");
        return accountSchemeRepository.findAll();
    }

    /**
     * {@code GET  /account-schemes/:id} : get the "id" accountScheme.
     *
     * @param id the id of the accountScheme to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountScheme, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-schemes/{id}")
    public ResponseEntity<AccountScheme> getAccountScheme(@PathVariable Long id) {
        log.debug("REST request to get AccountScheme : {}", id);
        Optional<AccountScheme> accountScheme = accountSchemeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(accountScheme);
    }

    /**
     * {@code DELETE  /account-schemes/:id} : delete the "id" accountScheme.
     *
     * @param id the id of the accountScheme to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-schemes/{id}")
    public ResponseEntity<Void> deleteAccountScheme(@PathVariable Long id) {
        log.debug("REST request to delete AccountScheme : {}", id);
        accountSchemeRepository.deleteById(id);
        accountSchemeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/account-schemes?query=:query} : search for the accountScheme corresponding
     * to the query.
     *
     * @param query the query of the accountScheme search.
     * @return the result of the search.
     */
    @GetMapping("/_search/account-schemes")
    public List<AccountScheme> searchAccountSchemes(@RequestParam String query) {
        log.debug("REST request to search AccountSchemes for query {}", query);
        return StreamSupport
            .stream(accountSchemeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
