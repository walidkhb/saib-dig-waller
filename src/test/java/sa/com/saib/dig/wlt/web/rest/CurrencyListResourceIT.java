package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.CurrencyList;
import sa.com.saib.dig.wlt.repository.CurrencyListRepository;
import sa.com.saib.dig.wlt.repository.search.CurrencyListSearchRepository;

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
 * Integration tests for the {@link CurrencyListResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CurrencyListResourceIT {

    private static final String DEFAULT_CURRENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    @Autowired
    private CurrencyListRepository currencyListRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.CurrencyListSearchRepositoryMockConfiguration
     */
    @Autowired
    private CurrencyListSearchRepository mockCurrencyListSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrencyListMockMvc;

    private CurrencyList currencyList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrencyList createEntity(EntityManager em) {
        CurrencyList currencyList = new CurrencyList()
            .currencyName(DEFAULT_CURRENCY_NAME)
            .currencyCode(DEFAULT_CURRENCY_CODE);
        return currencyList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrencyList createUpdatedEntity(EntityManager em) {
        CurrencyList currencyList = new CurrencyList()
            .currencyName(UPDATED_CURRENCY_NAME)
            .currencyCode(UPDATED_CURRENCY_CODE);
        return currencyList;
    }

    @BeforeEach
    public void initTest() {
        currencyList = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrencyList() throws Exception {
        int databaseSizeBeforeCreate = currencyListRepository.findAll().size();
        // Create the CurrencyList
        restCurrencyListMockMvc.perform(post("/api/currency-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyList)))
            .andExpect(status().isCreated());

        // Validate the CurrencyList in the database
        List<CurrencyList> currencyListList = currencyListRepository.findAll();
        assertThat(currencyListList).hasSize(databaseSizeBeforeCreate + 1);
        CurrencyList testCurrencyList = currencyListList.get(currencyListList.size() - 1);
        assertThat(testCurrencyList.getCurrencyName()).isEqualTo(DEFAULT_CURRENCY_NAME);
        assertThat(testCurrencyList.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);

        // Validate the CurrencyList in Elasticsearch
        verify(mockCurrencyListSearchRepository, times(1)).save(testCurrencyList);
    }

    @Test
    @Transactional
    public void createCurrencyListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyListRepository.findAll().size();

        // Create the CurrencyList with an existing ID
        currencyList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyListMockMvc.perform(post("/api/currency-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyList)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyList in the database
        List<CurrencyList> currencyListList = currencyListRepository.findAll();
        assertThat(currencyListList).hasSize(databaseSizeBeforeCreate);

        // Validate the CurrencyList in Elasticsearch
        verify(mockCurrencyListSearchRepository, times(0)).save(currencyList);
    }


    @Test
    @Transactional
    public void getAllCurrencyLists() throws Exception {
        // Initialize the database
        currencyListRepository.saveAndFlush(currencyList);

        // Get all the currencyListList
        restCurrencyListMockMvc.perform(get("/api/currency-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyList.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)));
    }
    
    @Test
    @Transactional
    public void getCurrencyList() throws Exception {
        // Initialize the database
        currencyListRepository.saveAndFlush(currencyList);

        // Get the currencyList
        restCurrencyListMockMvc.perform(get("/api/currency-lists/{id}", currencyList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currencyList.getId().intValue()))
            .andExpect(jsonPath("$.currencyName").value(DEFAULT_CURRENCY_NAME))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingCurrencyList() throws Exception {
        // Get the currencyList
        restCurrencyListMockMvc.perform(get("/api/currency-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrencyList() throws Exception {
        // Initialize the database
        currencyListRepository.saveAndFlush(currencyList);

        int databaseSizeBeforeUpdate = currencyListRepository.findAll().size();

        // Update the currencyList
        CurrencyList updatedCurrencyList = currencyListRepository.findById(currencyList.getId()).get();
        // Disconnect from session so that the updates on updatedCurrencyList are not directly saved in db
        em.detach(updatedCurrencyList);
        updatedCurrencyList
            .currencyName(UPDATED_CURRENCY_NAME)
            .currencyCode(UPDATED_CURRENCY_CODE);

        restCurrencyListMockMvc.perform(put("/api/currency-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCurrencyList)))
            .andExpect(status().isOk());

        // Validate the CurrencyList in the database
        List<CurrencyList> currencyListList = currencyListRepository.findAll();
        assertThat(currencyListList).hasSize(databaseSizeBeforeUpdate);
        CurrencyList testCurrencyList = currencyListList.get(currencyListList.size() - 1);
        assertThat(testCurrencyList.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
        assertThat(testCurrencyList.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);

        // Validate the CurrencyList in Elasticsearch
        verify(mockCurrencyListSearchRepository, times(1)).save(testCurrencyList);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrencyList() throws Exception {
        int databaseSizeBeforeUpdate = currencyListRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyListMockMvc.perform(put("/api/currency-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyList)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyList in the database
        List<CurrencyList> currencyListList = currencyListRepository.findAll();
        assertThat(currencyListList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CurrencyList in Elasticsearch
        verify(mockCurrencyListSearchRepository, times(0)).save(currencyList);
    }

    @Test
    @Transactional
    public void deleteCurrencyList() throws Exception {
        // Initialize the database
        currencyListRepository.saveAndFlush(currencyList);

        int databaseSizeBeforeDelete = currencyListRepository.findAll().size();

        // Delete the currencyList
        restCurrencyListMockMvc.perform(delete("/api/currency-lists/{id}", currencyList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurrencyList> currencyListList = currencyListRepository.findAll();
        assertThat(currencyListList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CurrencyList in Elasticsearch
        verify(mockCurrencyListSearchRepository, times(1)).deleteById(currencyList.getId());
    }

    @Test
    @Transactional
    public void searchCurrencyList() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        currencyListRepository.saveAndFlush(currencyList);
        when(mockCurrencyListSearchRepository.search(queryStringQuery("id:" + currencyList.getId())))
            .thenReturn(Collections.singletonList(currencyList));

        // Search the currencyList
        restCurrencyListMockMvc.perform(get("/api/_search/currency-lists?query=id:" + currencyList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyList.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)));
    }
}
