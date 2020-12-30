package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.CountryList;
import sa.com.saib.dig.wlt.repository.CountryListRepository;
import sa.com.saib.dig.wlt.repository.search.CountryListSearchRepository;

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
 * Integration tests for the {@link CountryListResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CountryListResourceIT {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    @Autowired
    private CountryListRepository countryListRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.CountryListSearchRepositoryMockConfiguration
     */
    @Autowired
    private CountryListSearchRepository mockCountryListSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryListMockMvc;

    private CountryList countryList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryList createEntity(EntityManager em) {
        CountryList countryList = new CountryList()
            .countryName(DEFAULT_COUNTRY_NAME)
            .countryCode(DEFAULT_COUNTRY_CODE);
        return countryList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryList createUpdatedEntity(EntityManager em) {
        CountryList countryList = new CountryList()
            .countryName(UPDATED_COUNTRY_NAME)
            .countryCode(UPDATED_COUNTRY_CODE);
        return countryList;
    }

    @BeforeEach
    public void initTest() {
        countryList = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountryList() throws Exception {
        int databaseSizeBeforeCreate = countryListRepository.findAll().size();
        // Create the CountryList
        restCountryListMockMvc.perform(post("/api/country-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryList)))
            .andExpect(status().isCreated());

        // Validate the CountryList in the database
        List<CountryList> countryListList = countryListRepository.findAll();
        assertThat(countryListList).hasSize(databaseSizeBeforeCreate + 1);
        CountryList testCountryList = countryListList.get(countryListList.size() - 1);
        assertThat(testCountryList.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCountryList.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);

        // Validate the CountryList in Elasticsearch
        verify(mockCountryListSearchRepository, times(1)).save(testCountryList);
    }

    @Test
    @Transactional
    public void createCountryListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryListRepository.findAll().size();

        // Create the CountryList with an existing ID
        countryList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryListMockMvc.perform(post("/api/country-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryList)))
            .andExpect(status().isBadRequest());

        // Validate the CountryList in the database
        List<CountryList> countryListList = countryListRepository.findAll();
        assertThat(countryListList).hasSize(databaseSizeBeforeCreate);

        // Validate the CountryList in Elasticsearch
        verify(mockCountryListSearchRepository, times(0)).save(countryList);
    }


    @Test
    @Transactional
    public void getAllCountryLists() throws Exception {
        // Initialize the database
        countryListRepository.saveAndFlush(countryList);

        // Get all the countryListList
        restCountryListMockMvc.perform(get("/api/country-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryList.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)));
    }
    
    @Test
    @Transactional
    public void getCountryList() throws Exception {
        // Initialize the database
        countryListRepository.saveAndFlush(countryList);

        // Get the countryList
        restCountryListMockMvc.perform(get("/api/country-lists/{id}", countryList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countryList.getId().intValue()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingCountryList() throws Exception {
        // Get the countryList
        restCountryListMockMvc.perform(get("/api/country-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountryList() throws Exception {
        // Initialize the database
        countryListRepository.saveAndFlush(countryList);

        int databaseSizeBeforeUpdate = countryListRepository.findAll().size();

        // Update the countryList
        CountryList updatedCountryList = countryListRepository.findById(countryList.getId()).get();
        // Disconnect from session so that the updates on updatedCountryList are not directly saved in db
        em.detach(updatedCountryList);
        updatedCountryList
            .countryName(UPDATED_COUNTRY_NAME)
            .countryCode(UPDATED_COUNTRY_CODE);

        restCountryListMockMvc.perform(put("/api/country-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCountryList)))
            .andExpect(status().isOk());

        // Validate the CountryList in the database
        List<CountryList> countryListList = countryListRepository.findAll();
        assertThat(countryListList).hasSize(databaseSizeBeforeUpdate);
        CountryList testCountryList = countryListList.get(countryListList.size() - 1);
        assertThat(testCountryList.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountryList.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);

        // Validate the CountryList in Elasticsearch
        verify(mockCountryListSearchRepository, times(1)).save(testCountryList);
    }

    @Test
    @Transactional
    public void updateNonExistingCountryList() throws Exception {
        int databaseSizeBeforeUpdate = countryListRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryListMockMvc.perform(put("/api/country-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryList)))
            .andExpect(status().isBadRequest());

        // Validate the CountryList in the database
        List<CountryList> countryListList = countryListRepository.findAll();
        assertThat(countryListList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CountryList in Elasticsearch
        verify(mockCountryListSearchRepository, times(0)).save(countryList);
    }

    @Test
    @Transactional
    public void deleteCountryList() throws Exception {
        // Initialize the database
        countryListRepository.saveAndFlush(countryList);

        int databaseSizeBeforeDelete = countryListRepository.findAll().size();

        // Delete the countryList
        restCountryListMockMvc.perform(delete("/api/country-lists/{id}", countryList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CountryList> countryListList = countryListRepository.findAll();
        assertThat(countryListList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CountryList in Elasticsearch
        verify(mockCountryListSearchRepository, times(1)).deleteById(countryList.getId());
    }

    @Test
    @Transactional
    public void searchCountryList() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        countryListRepository.saveAndFlush(countryList);
        when(mockCountryListSearchRepository.search(queryStringQuery("id:" + countryList.getId())))
            .thenReturn(Collections.singletonList(countryList));

        // Search the countryList
        restCountryListMockMvc.perform(get("/api/_search/country-lists?query=id:" + countryList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryList.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)));
    }
}
