package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.AccountScheme;
import sa.com.saib.dig.wlt.repository.AccountSchemeRepository;
import sa.com.saib.dig.wlt.repository.search.AccountSchemeSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AccountSchemeResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccountSchemeResourceIT {

    private static final String DEFAULT_SCHEME_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHEME_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION = "BBBBBBBBBB";

    @Autowired
    private AccountSchemeRepository accountSchemeRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.AccountSchemeSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccountSchemeSearchRepository mockAccountSchemeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountSchemeMockMvc;

    private AccountScheme accountScheme;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountScheme createEntity(EntityManager em) {
        AccountScheme accountScheme = new AccountScheme()
            .schemeName(DEFAULT_SCHEME_NAME)
            .identification(DEFAULT_IDENTIFICATION);
        return accountScheme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountScheme createUpdatedEntity(EntityManager em) {
        AccountScheme accountScheme = new AccountScheme()
            .schemeName(UPDATED_SCHEME_NAME)
            .identification(UPDATED_IDENTIFICATION);
        return accountScheme;
    }

    @BeforeEach
    public void initTest() {
        accountScheme = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountScheme() throws Exception {
        int databaseSizeBeforeCreate = accountSchemeRepository.findAll().size();
        // Create the AccountScheme
        restAccountSchemeMockMvc.perform(post("/api/account-schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountScheme)))
            .andExpect(status().isCreated());

        // Validate the AccountScheme in the database
        List<AccountScheme> accountSchemeList = accountSchemeRepository.findAll();
        assertThat(accountSchemeList).hasSize(databaseSizeBeforeCreate + 1);
        AccountScheme testAccountScheme = accountSchemeList.get(accountSchemeList.size() - 1);
        assertThat(testAccountScheme.getSchemeName()).isEqualTo(DEFAULT_SCHEME_NAME);
        assertThat(testAccountScheme.getIdentification()).isEqualTo(DEFAULT_IDENTIFICATION);

        // Validate the AccountScheme in Elasticsearch
        verify(mockAccountSchemeSearchRepository, times(1)).save(testAccountScheme);
    }

    @Test
    @Transactional
    public void createAccountSchemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountSchemeRepository.findAll().size();

        // Create the AccountScheme with an existing ID
        accountScheme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountSchemeMockMvc.perform(post("/api/account-schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountScheme)))
            .andExpect(status().isBadRequest());

        // Validate the AccountScheme in the database
        List<AccountScheme> accountSchemeList = accountSchemeRepository.findAll();
        assertThat(accountSchemeList).hasSize(databaseSizeBeforeCreate);

        // Validate the AccountScheme in Elasticsearch
        verify(mockAccountSchemeSearchRepository, times(0)).save(accountScheme);
    }


    @Test
    @Transactional
    public void getAllAccountSchemes() throws Exception {
        // Initialize the database
        accountSchemeRepository.saveAndFlush(accountScheme);

        // Get all the accountSchemeList
        restAccountSchemeMockMvc.perform(get("/api/account-schemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountScheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].schemeName").value(hasItem(DEFAULT_SCHEME_NAME)))
            .andExpect(jsonPath("$.[*].identification").value(hasItem(DEFAULT_IDENTIFICATION)));
    }
    
    @Test
    @Transactional
    public void getAccountScheme() throws Exception {
        // Initialize the database
        accountSchemeRepository.saveAndFlush(accountScheme);

        // Get the accountScheme
        restAccountSchemeMockMvc.perform(get("/api/account-schemes/{id}", accountScheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountScheme.getId().intValue()))
            .andExpect(jsonPath("$.schemeName").value(DEFAULT_SCHEME_NAME))
            .andExpect(jsonPath("$.identification").value(DEFAULT_IDENTIFICATION));
    }
    @Test
    @Transactional
    public void getNonExistingAccountScheme() throws Exception {
        // Get the accountScheme
        restAccountSchemeMockMvc.perform(get("/api/account-schemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountScheme() throws Exception {
        // Initialize the database
        accountSchemeRepository.saveAndFlush(accountScheme);

        int databaseSizeBeforeUpdate = accountSchemeRepository.findAll().size();

        // Update the accountScheme
        AccountScheme updatedAccountScheme = accountSchemeRepository.findById(accountScheme.getId()).get();
        // Disconnect from session so that the updates on updatedAccountScheme are not directly saved in db
        em.detach(updatedAccountScheme);
        updatedAccountScheme
            .schemeName(UPDATED_SCHEME_NAME)
            .identification(UPDATED_IDENTIFICATION);

        restAccountSchemeMockMvc.perform(put("/api/account-schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccountScheme)))
            .andExpect(status().isOk());

        // Validate the AccountScheme in the database
        List<AccountScheme> accountSchemeList = accountSchemeRepository.findAll();
        assertThat(accountSchemeList).hasSize(databaseSizeBeforeUpdate);
        AccountScheme testAccountScheme = accountSchemeList.get(accountSchemeList.size() - 1);
        assertThat(testAccountScheme.getSchemeName()).isEqualTo(UPDATED_SCHEME_NAME);
        assertThat(testAccountScheme.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);

        // Validate the AccountScheme in Elasticsearch
        verify(mockAccountSchemeSearchRepository, times(1)).save(testAccountScheme);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountScheme() throws Exception {
        int databaseSizeBeforeUpdate = accountSchemeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountSchemeMockMvc.perform(put("/api/account-schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountScheme)))
            .andExpect(status().isBadRequest());

        // Validate the AccountScheme in the database
        List<AccountScheme> accountSchemeList = accountSchemeRepository.findAll();
        assertThat(accountSchemeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccountScheme in Elasticsearch
        verify(mockAccountSchemeSearchRepository, times(0)).save(accountScheme);
    }

    @Test
    @Transactional
    public void deleteAccountScheme() throws Exception {
        // Initialize the database
        accountSchemeRepository.saveAndFlush(accountScheme);

        int databaseSizeBeforeDelete = accountSchemeRepository.findAll().size();

        // Delete the accountScheme
        restAccountSchemeMockMvc.perform(delete("/api/account-schemes/{id}", accountScheme.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountScheme> accountSchemeList = accountSchemeRepository.findAll();
        assertThat(accountSchemeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AccountScheme in Elasticsearch
        verify(mockAccountSchemeSearchRepository, times(1)).deleteById(accountScheme.getId());
    }

    @Test
    @Transactional
    public void searchAccountScheme() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        accountSchemeRepository.saveAndFlush(accountScheme);
        when(mockAccountSchemeSearchRepository.search(queryStringQuery("id:" + accountScheme.getId())))
            .thenReturn(Collections.singletonList(accountScheme));

        // Search the accountScheme
        restAccountSchemeMockMvc.perform(get("/api/_search/account-schemes?query=id:" + accountScheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountScheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].schemeName").value(hasItem(DEFAULT_SCHEME_NAME)))
            .andExpect(jsonPath("$.[*].identification").value(hasItem(DEFAULT_IDENTIFICATION)));
    }
}
