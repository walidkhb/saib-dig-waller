package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.FingerDetails;
import sa.com.saib.dig.wlt.repository.FingerDetailsRepository;
import sa.com.saib.dig.wlt.repository.search.FingerDetailsSearchRepository;

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
 * Integration tests for the {@link FingerDetailsResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FingerDetailsResourceIT {

    private static final String DEFAULT_FINGER_PRINT = "AAAAAAAAAA";
    private static final String UPDATED_FINGER_PRINT = "BBBBBBBBBB";

    private static final String DEFAULT_FINGER_INDEX = "AAAAAAAAAA";
    private static final String UPDATED_FINGER_INDEX = "BBBBBBBBBB";

    @Autowired
    private FingerDetailsRepository fingerDetailsRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.FingerDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private FingerDetailsSearchRepository mockFingerDetailsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFingerDetailsMockMvc;

    private FingerDetails fingerDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FingerDetails createEntity(EntityManager em) {
        FingerDetails fingerDetails = new FingerDetails()
            .fingerPrint(DEFAULT_FINGER_PRINT)
            .fingerIndex(DEFAULT_FINGER_INDEX);
        return fingerDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FingerDetails createUpdatedEntity(EntityManager em) {
        FingerDetails fingerDetails = new FingerDetails()
            .fingerPrint(UPDATED_FINGER_PRINT)
            .fingerIndex(UPDATED_FINGER_INDEX);
        return fingerDetails;
    }

    @BeforeEach
    public void initTest() {
        fingerDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createFingerDetails() throws Exception {
        int databaseSizeBeforeCreate = fingerDetailsRepository.findAll().size();
        // Create the FingerDetails
        restFingerDetailsMockMvc.perform(post("/api/finger-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fingerDetails)))
            .andExpect(status().isCreated());

        // Validate the FingerDetails in the database
        List<FingerDetails> fingerDetailsList = fingerDetailsRepository.findAll();
        assertThat(fingerDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        FingerDetails testFingerDetails = fingerDetailsList.get(fingerDetailsList.size() - 1);
        assertThat(testFingerDetails.getFingerPrint()).isEqualTo(DEFAULT_FINGER_PRINT);
        assertThat(testFingerDetails.getFingerIndex()).isEqualTo(DEFAULT_FINGER_INDEX);

        // Validate the FingerDetails in Elasticsearch
        verify(mockFingerDetailsSearchRepository, times(1)).save(testFingerDetails);
    }

    @Test
    @Transactional
    public void createFingerDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fingerDetailsRepository.findAll().size();

        // Create the FingerDetails with an existing ID
        fingerDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFingerDetailsMockMvc.perform(post("/api/finger-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fingerDetails)))
            .andExpect(status().isBadRequest());

        // Validate the FingerDetails in the database
        List<FingerDetails> fingerDetailsList = fingerDetailsRepository.findAll();
        assertThat(fingerDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the FingerDetails in Elasticsearch
        verify(mockFingerDetailsSearchRepository, times(0)).save(fingerDetails);
    }


    @Test
    @Transactional
    public void getAllFingerDetails() throws Exception {
        // Initialize the database
        fingerDetailsRepository.saveAndFlush(fingerDetails);

        // Get all the fingerDetailsList
        restFingerDetailsMockMvc.perform(get("/api/finger-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fingerDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].fingerPrint").value(hasItem(DEFAULT_FINGER_PRINT)))
            .andExpect(jsonPath("$.[*].fingerIndex").value(hasItem(DEFAULT_FINGER_INDEX)));
    }
    
    @Test
    @Transactional
    public void getFingerDetails() throws Exception {
        // Initialize the database
        fingerDetailsRepository.saveAndFlush(fingerDetails);

        // Get the fingerDetails
        restFingerDetailsMockMvc.perform(get("/api/finger-details/{id}", fingerDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fingerDetails.getId().intValue()))
            .andExpect(jsonPath("$.fingerPrint").value(DEFAULT_FINGER_PRINT))
            .andExpect(jsonPath("$.fingerIndex").value(DEFAULT_FINGER_INDEX));
    }
    @Test
    @Transactional
    public void getNonExistingFingerDetails() throws Exception {
        // Get the fingerDetails
        restFingerDetailsMockMvc.perform(get("/api/finger-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFingerDetails() throws Exception {
        // Initialize the database
        fingerDetailsRepository.saveAndFlush(fingerDetails);

        int databaseSizeBeforeUpdate = fingerDetailsRepository.findAll().size();

        // Update the fingerDetails
        FingerDetails updatedFingerDetails = fingerDetailsRepository.findById(fingerDetails.getId()).get();
        // Disconnect from session so that the updates on updatedFingerDetails are not directly saved in db
        em.detach(updatedFingerDetails);
        updatedFingerDetails
            .fingerPrint(UPDATED_FINGER_PRINT)
            .fingerIndex(UPDATED_FINGER_INDEX);

        restFingerDetailsMockMvc.perform(put("/api/finger-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFingerDetails)))
            .andExpect(status().isOk());

        // Validate the FingerDetails in the database
        List<FingerDetails> fingerDetailsList = fingerDetailsRepository.findAll();
        assertThat(fingerDetailsList).hasSize(databaseSizeBeforeUpdate);
        FingerDetails testFingerDetails = fingerDetailsList.get(fingerDetailsList.size() - 1);
        assertThat(testFingerDetails.getFingerPrint()).isEqualTo(UPDATED_FINGER_PRINT);
        assertThat(testFingerDetails.getFingerIndex()).isEqualTo(UPDATED_FINGER_INDEX);

        // Validate the FingerDetails in Elasticsearch
        verify(mockFingerDetailsSearchRepository, times(1)).save(testFingerDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingFingerDetails() throws Exception {
        int databaseSizeBeforeUpdate = fingerDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFingerDetailsMockMvc.perform(put("/api/finger-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fingerDetails)))
            .andExpect(status().isBadRequest());

        // Validate the FingerDetails in the database
        List<FingerDetails> fingerDetailsList = fingerDetailsRepository.findAll();
        assertThat(fingerDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FingerDetails in Elasticsearch
        verify(mockFingerDetailsSearchRepository, times(0)).save(fingerDetails);
    }

    @Test
    @Transactional
    public void deleteFingerDetails() throws Exception {
        // Initialize the database
        fingerDetailsRepository.saveAndFlush(fingerDetails);

        int databaseSizeBeforeDelete = fingerDetailsRepository.findAll().size();

        // Delete the fingerDetails
        restFingerDetailsMockMvc.perform(delete("/api/finger-details/{id}", fingerDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FingerDetails> fingerDetailsList = fingerDetailsRepository.findAll();
        assertThat(fingerDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FingerDetails in Elasticsearch
        verify(mockFingerDetailsSearchRepository, times(1)).deleteById(fingerDetails.getId());
    }

    @Test
    @Transactional
    public void searchFingerDetails() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        fingerDetailsRepository.saveAndFlush(fingerDetails);
        when(mockFingerDetailsSearchRepository.search(queryStringQuery("id:" + fingerDetails.getId())))
            .thenReturn(Collections.singletonList(fingerDetails));

        // Search the fingerDetails
        restFingerDetailsMockMvc.perform(get("/api/_search/finger-details?query=id:" + fingerDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fingerDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].fingerPrint").value(hasItem(DEFAULT_FINGER_PRINT)))
            .andExpect(jsonPath("$.[*].fingerIndex").value(hasItem(DEFAULT_FINGER_INDEX)));
    }
}
