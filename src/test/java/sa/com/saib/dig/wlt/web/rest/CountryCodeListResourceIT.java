package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.CountryCodeList;
import sa.com.saib.dig.wlt.repository.CountryCodeListRepository;
import sa.com.saib.dig.wlt.repository.search.CountryCodeListSearchRepository;

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
 * Integration tests for the {@link CountryCodeListResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CountryCodeListResourceIT {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_ISD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_ISD_CODE = "BBBBBBBBBB";

    @Autowired
    private CountryCodeListRepository countryCodeListRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.CountryCodeListSearchRepositoryMockConfiguration
     */
    @Autowired
    private CountryCodeListSearchRepository mockCountryCodeListSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryCodeListMockMvc;

    private CountryCodeList countryCodeList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryCodeList createEntity(EntityManager em) {
        CountryCodeList countryCodeList = new CountryCodeList()
            .countryName(DEFAULT_COUNTRY_NAME)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .countryISDCode(DEFAULT_COUNTRY_ISD_CODE);
        return countryCodeList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryCodeList createUpdatedEntity(EntityManager em) {
        CountryCodeList countryCodeList = new CountryCodeList()
            .countryName(UPDATED_COUNTRY_NAME)
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryISDCode(UPDATED_COUNTRY_ISD_CODE);
        return countryCodeList;
    }

    @BeforeEach
    public void initTest() {
        countryCodeList = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountryCodeList() throws Exception {
        int databaseSizeBeforeCreate = countryCodeListRepository.findAll().size();
        // Create the CountryCodeList
        restCountryCodeListMockMvc.perform(post("/api/country-code-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryCodeList)))
            .andExpect(status().isCreated());

        // Validate the CountryCodeList in the database
        List<CountryCodeList> countryCodeListList = countryCodeListRepository.findAll();
        assertThat(countryCodeListList).hasSize(databaseSizeBeforeCreate + 1);
        CountryCodeList testCountryCodeList = countryCodeListList.get(countryCodeListList.size() - 1);
        assertThat(testCountryCodeList.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCountryCodeList.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testCountryCodeList.getCountryISDCode()).isEqualTo(DEFAULT_COUNTRY_ISD_CODE);

        // Validate the CountryCodeList in Elasticsearch
        verify(mockCountryCodeListSearchRepository, times(1)).save(testCountryCodeList);
    }

    @Test
    @Transactional
    public void createCountryCodeListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryCodeListRepository.findAll().size();

        // Create the CountryCodeList with an existing ID
        countryCodeList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryCodeListMockMvc.perform(post("/api/country-code-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryCodeList)))
            .andExpect(status().isBadRequest());

        // Validate the CountryCodeList in the database
        List<CountryCodeList> countryCodeListList = countryCodeListRepository.findAll();
        assertThat(countryCodeListList).hasSize(databaseSizeBeforeCreate);

        // Validate the CountryCodeList in Elasticsearch
        verify(mockCountryCodeListSearchRepository, times(0)).save(countryCodeList);
    }


    @Test
    @Transactional
    public void getAllCountryCodeLists() throws Exception {
        // Initialize the database
        countryCodeListRepository.saveAndFlush(countryCodeList);

        // Get all the countryCodeListList
        restCountryCodeListMockMvc.perform(get("/api/country-code-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryCodeList.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].countryISDCode").value(hasItem(DEFAULT_COUNTRY_ISD_CODE)));
    }
    
    @Test
    @Transactional
    public void getCountryCodeList() throws Exception {
        // Initialize the database
        countryCodeListRepository.saveAndFlush(countryCodeList);

        // Get the countryCodeList
        restCountryCodeListMockMvc.perform(get("/api/country-code-lists/{id}", countryCodeList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countryCodeList.getId().intValue()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.countryISDCode").value(DEFAULT_COUNTRY_ISD_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingCountryCodeList() throws Exception {
        // Get the countryCodeList
        restCountryCodeListMockMvc.perform(get("/api/country-code-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountryCodeList() throws Exception {
        // Initialize the database
        countryCodeListRepository.saveAndFlush(countryCodeList);

        int databaseSizeBeforeUpdate = countryCodeListRepository.findAll().size();

        // Update the countryCodeList
        CountryCodeList updatedCountryCodeList = countryCodeListRepository.findById(countryCodeList.getId()).get();
        // Disconnect from session so that the updates on updatedCountryCodeList are not directly saved in db
        em.detach(updatedCountryCodeList);
        updatedCountryCodeList
            .countryName(UPDATED_COUNTRY_NAME)
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryISDCode(UPDATED_COUNTRY_ISD_CODE);

        restCountryCodeListMockMvc.perform(put("/api/country-code-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCountryCodeList)))
            .andExpect(status().isOk());

        // Validate the CountryCodeList in the database
        List<CountryCodeList> countryCodeListList = countryCodeListRepository.findAll();
        assertThat(countryCodeListList).hasSize(databaseSizeBeforeUpdate);
        CountryCodeList testCountryCodeList = countryCodeListList.get(countryCodeListList.size() - 1);
        assertThat(testCountryCodeList.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountryCodeList.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testCountryCodeList.getCountryISDCode()).isEqualTo(UPDATED_COUNTRY_ISD_CODE);

        // Validate the CountryCodeList in Elasticsearch
        verify(mockCountryCodeListSearchRepository, times(1)).save(testCountryCodeList);
    }

    @Test
    @Transactional
    public void updateNonExistingCountryCodeList() throws Exception {
        int databaseSizeBeforeUpdate = countryCodeListRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryCodeListMockMvc.perform(put("/api/country-code-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryCodeList)))
            .andExpect(status().isBadRequest());

        // Validate the CountryCodeList in the database
        List<CountryCodeList> countryCodeListList = countryCodeListRepository.findAll();
        assertThat(countryCodeListList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CountryCodeList in Elasticsearch
        verify(mockCountryCodeListSearchRepository, times(0)).save(countryCodeList);
    }

    @Test
    @Transactional
    public void deleteCountryCodeList() throws Exception {
        // Initialize the database
        countryCodeListRepository.saveAndFlush(countryCodeList);

        int databaseSizeBeforeDelete = countryCodeListRepository.findAll().size();

        // Delete the countryCodeList
        restCountryCodeListMockMvc.perform(delete("/api/country-code-lists/{id}", countryCodeList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CountryCodeList> countryCodeListList = countryCodeListRepository.findAll();
        assertThat(countryCodeListList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CountryCodeList in Elasticsearch
        verify(mockCountryCodeListSearchRepository, times(1)).deleteById(countryCodeList.getId());
    }

    @Test
    @Transactional
    public void searchCountryCodeList() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        countryCodeListRepository.saveAndFlush(countryCodeList);
        when(mockCountryCodeListSearchRepository.search(queryStringQuery("id:" + countryCodeList.getId())))
            .thenReturn(Collections.singletonList(countryCodeList));

        // Search the countryCodeList
        restCountryCodeListMockMvc.perform(get("/api/_search/country-code-lists?query=id:" + countryCodeList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryCodeList.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].countryISDCode").value(hasItem(DEFAULT_COUNTRY_ISD_CODE)));
    }
}
