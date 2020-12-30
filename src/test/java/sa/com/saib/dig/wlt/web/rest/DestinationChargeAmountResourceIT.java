package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.DestinationChargeAmount;
import sa.com.saib.dig.wlt.repository.DestinationChargeAmountRepository;
import sa.com.saib.dig.wlt.repository.search.DestinationChargeAmountSearchRepository;

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
 * Integration tests for the {@link DestinationChargeAmountResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DestinationChargeAmountResourceIT {

    private static final String DEFAULT_V_AT_ESTIMATED = "AAAAAAAAAA";
    private static final String UPDATED_V_AT_ESTIMATED = "BBBBBBBBBB";

    private static final String DEFAULT_AMOUNT_ESTIMATED = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT_ESTIMATED = "BBBBBBBBBB";

    @Autowired
    private DestinationChargeAmountRepository destinationChargeAmountRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.DestinationChargeAmountSearchRepositoryMockConfiguration
     */
    @Autowired
    private DestinationChargeAmountSearchRepository mockDestinationChargeAmountSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDestinationChargeAmountMockMvc;

    private DestinationChargeAmount destinationChargeAmount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DestinationChargeAmount createEntity(EntityManager em) {
        DestinationChargeAmount destinationChargeAmount = new DestinationChargeAmount()
            .vATEstimated(DEFAULT_V_AT_ESTIMATED)
            .amountEstimated(DEFAULT_AMOUNT_ESTIMATED);
        return destinationChargeAmount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DestinationChargeAmount createUpdatedEntity(EntityManager em) {
        DestinationChargeAmount destinationChargeAmount = new DestinationChargeAmount()
            .vATEstimated(UPDATED_V_AT_ESTIMATED)
            .amountEstimated(UPDATED_AMOUNT_ESTIMATED);
        return destinationChargeAmount;
    }

    @BeforeEach
    public void initTest() {
        destinationChargeAmount = createEntity(em);
    }

    @Test
    @Transactional
    public void createDestinationChargeAmount() throws Exception {
        int databaseSizeBeforeCreate = destinationChargeAmountRepository.findAll().size();
        // Create the DestinationChargeAmount
        restDestinationChargeAmountMockMvc.perform(post("/api/destination-charge-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(destinationChargeAmount)))
            .andExpect(status().isCreated());

        // Validate the DestinationChargeAmount in the database
        List<DestinationChargeAmount> destinationChargeAmountList = destinationChargeAmountRepository.findAll();
        assertThat(destinationChargeAmountList).hasSize(databaseSizeBeforeCreate + 1);
        DestinationChargeAmount testDestinationChargeAmount = destinationChargeAmountList.get(destinationChargeAmountList.size() - 1);
        assertThat(testDestinationChargeAmount.getvATEstimated()).isEqualTo(DEFAULT_V_AT_ESTIMATED);
        assertThat(testDestinationChargeAmount.getAmountEstimated()).isEqualTo(DEFAULT_AMOUNT_ESTIMATED);

        // Validate the DestinationChargeAmount in Elasticsearch
        verify(mockDestinationChargeAmountSearchRepository, times(1)).save(testDestinationChargeAmount);
    }

    @Test
    @Transactional
    public void createDestinationChargeAmountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = destinationChargeAmountRepository.findAll().size();

        // Create the DestinationChargeAmount with an existing ID
        destinationChargeAmount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDestinationChargeAmountMockMvc.perform(post("/api/destination-charge-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(destinationChargeAmount)))
            .andExpect(status().isBadRequest());

        // Validate the DestinationChargeAmount in the database
        List<DestinationChargeAmount> destinationChargeAmountList = destinationChargeAmountRepository.findAll();
        assertThat(destinationChargeAmountList).hasSize(databaseSizeBeforeCreate);

        // Validate the DestinationChargeAmount in Elasticsearch
        verify(mockDestinationChargeAmountSearchRepository, times(0)).save(destinationChargeAmount);
    }


    @Test
    @Transactional
    public void getAllDestinationChargeAmounts() throws Exception {
        // Initialize the database
        destinationChargeAmountRepository.saveAndFlush(destinationChargeAmount);

        // Get all the destinationChargeAmountList
        restDestinationChargeAmountMockMvc.perform(get("/api/destination-charge-amounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(destinationChargeAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].vATEstimated").value(hasItem(DEFAULT_V_AT_ESTIMATED)))
            .andExpect(jsonPath("$.[*].amountEstimated").value(hasItem(DEFAULT_AMOUNT_ESTIMATED)));
    }
    
    @Test
    @Transactional
    public void getDestinationChargeAmount() throws Exception {
        // Initialize the database
        destinationChargeAmountRepository.saveAndFlush(destinationChargeAmount);

        // Get the destinationChargeAmount
        restDestinationChargeAmountMockMvc.perform(get("/api/destination-charge-amounts/{id}", destinationChargeAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(destinationChargeAmount.getId().intValue()))
            .andExpect(jsonPath("$.vATEstimated").value(DEFAULT_V_AT_ESTIMATED))
            .andExpect(jsonPath("$.amountEstimated").value(DEFAULT_AMOUNT_ESTIMATED));
    }
    @Test
    @Transactional
    public void getNonExistingDestinationChargeAmount() throws Exception {
        // Get the destinationChargeAmount
        restDestinationChargeAmountMockMvc.perform(get("/api/destination-charge-amounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDestinationChargeAmount() throws Exception {
        // Initialize the database
        destinationChargeAmountRepository.saveAndFlush(destinationChargeAmount);

        int databaseSizeBeforeUpdate = destinationChargeAmountRepository.findAll().size();

        // Update the destinationChargeAmount
        DestinationChargeAmount updatedDestinationChargeAmount = destinationChargeAmountRepository.findById(destinationChargeAmount.getId()).get();
        // Disconnect from session so that the updates on updatedDestinationChargeAmount are not directly saved in db
        em.detach(updatedDestinationChargeAmount);
        updatedDestinationChargeAmount
            .vATEstimated(UPDATED_V_AT_ESTIMATED)
            .amountEstimated(UPDATED_AMOUNT_ESTIMATED);

        restDestinationChargeAmountMockMvc.perform(put("/api/destination-charge-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDestinationChargeAmount)))
            .andExpect(status().isOk());

        // Validate the DestinationChargeAmount in the database
        List<DestinationChargeAmount> destinationChargeAmountList = destinationChargeAmountRepository.findAll();
        assertThat(destinationChargeAmountList).hasSize(databaseSizeBeforeUpdate);
        DestinationChargeAmount testDestinationChargeAmount = destinationChargeAmountList.get(destinationChargeAmountList.size() - 1);
        assertThat(testDestinationChargeAmount.getvATEstimated()).isEqualTo(UPDATED_V_AT_ESTIMATED);
        assertThat(testDestinationChargeAmount.getAmountEstimated()).isEqualTo(UPDATED_AMOUNT_ESTIMATED);

        // Validate the DestinationChargeAmount in Elasticsearch
        verify(mockDestinationChargeAmountSearchRepository, times(1)).save(testDestinationChargeAmount);
    }

    @Test
    @Transactional
    public void updateNonExistingDestinationChargeAmount() throws Exception {
        int databaseSizeBeforeUpdate = destinationChargeAmountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDestinationChargeAmountMockMvc.perform(put("/api/destination-charge-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(destinationChargeAmount)))
            .andExpect(status().isBadRequest());

        // Validate the DestinationChargeAmount in the database
        List<DestinationChargeAmount> destinationChargeAmountList = destinationChargeAmountRepository.findAll();
        assertThat(destinationChargeAmountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DestinationChargeAmount in Elasticsearch
        verify(mockDestinationChargeAmountSearchRepository, times(0)).save(destinationChargeAmount);
    }

    @Test
    @Transactional
    public void deleteDestinationChargeAmount() throws Exception {
        // Initialize the database
        destinationChargeAmountRepository.saveAndFlush(destinationChargeAmount);

        int databaseSizeBeforeDelete = destinationChargeAmountRepository.findAll().size();

        // Delete the destinationChargeAmount
        restDestinationChargeAmountMockMvc.perform(delete("/api/destination-charge-amounts/{id}", destinationChargeAmount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DestinationChargeAmount> destinationChargeAmountList = destinationChargeAmountRepository.findAll();
        assertThat(destinationChargeAmountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DestinationChargeAmount in Elasticsearch
        verify(mockDestinationChargeAmountSearchRepository, times(1)).deleteById(destinationChargeAmount.getId());
    }

    @Test
    @Transactional
    public void searchDestinationChargeAmount() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        destinationChargeAmountRepository.saveAndFlush(destinationChargeAmount);
        when(mockDestinationChargeAmountSearchRepository.search(queryStringQuery("id:" + destinationChargeAmount.getId())))
            .thenReturn(Collections.singletonList(destinationChargeAmount));

        // Search the destinationChargeAmount
        restDestinationChargeAmountMockMvc.perform(get("/api/_search/destination-charge-amounts?query=id:" + destinationChargeAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(destinationChargeAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].vATEstimated").value(hasItem(DEFAULT_V_AT_ESTIMATED)))
            .andExpect(jsonPath("$.[*].amountEstimated").value(hasItem(DEFAULT_AMOUNT_ESTIMATED)));
    }
}
