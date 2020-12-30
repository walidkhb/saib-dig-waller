package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.CalculationInfoDetails;
import sa.com.saib.dig.wlt.repository.CalculationInfoDetailsRepository;
import sa.com.saib.dig.wlt.repository.search.CalculationInfoDetailsSearchRepository;

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
 * Integration tests for the {@link CalculationInfoDetailsResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CalculationInfoDetailsResourceIT {

    private static final String DEFAULT_TOTAL_DEBIT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_DEBIT_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION_EXCHANGE_RATE = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION_EXCHANGE_RATE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION_CURRENCY_INDICATOR = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION_CURRENCY_INDICATOR = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_AMOUNT = "BBBBBBBBBB";

    @Autowired
    private CalculationInfoDetailsRepository calculationInfoDetailsRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.CalculationInfoDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private CalculationInfoDetailsSearchRepository mockCalculationInfoDetailsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalculationInfoDetailsMockMvc;

    private CalculationInfoDetails calculationInfoDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalculationInfoDetails createEntity(EntityManager em) {
        CalculationInfoDetails calculationInfoDetails = new CalculationInfoDetails()
            .totalDebitAmount(DEFAULT_TOTAL_DEBIT_AMOUNT)
            .destinationAmount(DEFAULT_DESTINATION_AMOUNT)
            .destinationExchangeRate(DEFAULT_DESTINATION_EXCHANGE_RATE)
            .destinationCurrencyIndicator(DEFAULT_DESTINATION_CURRENCY_INDICATOR)
            .discountAmount(DEFAULT_DISCOUNT_AMOUNT);
        return calculationInfoDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalculationInfoDetails createUpdatedEntity(EntityManager em) {
        CalculationInfoDetails calculationInfoDetails = new CalculationInfoDetails()
            .totalDebitAmount(UPDATED_TOTAL_DEBIT_AMOUNT)
            .destinationAmount(UPDATED_DESTINATION_AMOUNT)
            .destinationExchangeRate(UPDATED_DESTINATION_EXCHANGE_RATE)
            .destinationCurrencyIndicator(UPDATED_DESTINATION_CURRENCY_INDICATOR)
            .discountAmount(UPDATED_DISCOUNT_AMOUNT);
        return calculationInfoDetails;
    }

    @BeforeEach
    public void initTest() {
        calculationInfoDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalculationInfoDetails() throws Exception {
        int databaseSizeBeforeCreate = calculationInfoDetailsRepository.findAll().size();
        // Create the CalculationInfoDetails
        restCalculationInfoDetailsMockMvc.perform(post("/api/calculation-info-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calculationInfoDetails)))
            .andExpect(status().isCreated());

        // Validate the CalculationInfoDetails in the database
        List<CalculationInfoDetails> calculationInfoDetailsList = calculationInfoDetailsRepository.findAll();
        assertThat(calculationInfoDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        CalculationInfoDetails testCalculationInfoDetails = calculationInfoDetailsList.get(calculationInfoDetailsList.size() - 1);
        assertThat(testCalculationInfoDetails.getTotalDebitAmount()).isEqualTo(DEFAULT_TOTAL_DEBIT_AMOUNT);
        assertThat(testCalculationInfoDetails.getDestinationAmount()).isEqualTo(DEFAULT_DESTINATION_AMOUNT);
        assertThat(testCalculationInfoDetails.getDestinationExchangeRate()).isEqualTo(DEFAULT_DESTINATION_EXCHANGE_RATE);
        assertThat(testCalculationInfoDetails.getDestinationCurrencyIndicator()).isEqualTo(DEFAULT_DESTINATION_CURRENCY_INDICATOR);
        assertThat(testCalculationInfoDetails.getDiscountAmount()).isEqualTo(DEFAULT_DISCOUNT_AMOUNT);

        // Validate the CalculationInfoDetails in Elasticsearch
        verify(mockCalculationInfoDetailsSearchRepository, times(1)).save(testCalculationInfoDetails);
    }

    @Test
    @Transactional
    public void createCalculationInfoDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calculationInfoDetailsRepository.findAll().size();

        // Create the CalculationInfoDetails with an existing ID
        calculationInfoDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalculationInfoDetailsMockMvc.perform(post("/api/calculation-info-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calculationInfoDetails)))
            .andExpect(status().isBadRequest());

        // Validate the CalculationInfoDetails in the database
        List<CalculationInfoDetails> calculationInfoDetailsList = calculationInfoDetailsRepository.findAll();
        assertThat(calculationInfoDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the CalculationInfoDetails in Elasticsearch
        verify(mockCalculationInfoDetailsSearchRepository, times(0)).save(calculationInfoDetails);
    }


    @Test
    @Transactional
    public void getAllCalculationInfoDetails() throws Exception {
        // Initialize the database
        calculationInfoDetailsRepository.saveAndFlush(calculationInfoDetails);

        // Get all the calculationInfoDetailsList
        restCalculationInfoDetailsMockMvc.perform(get("/api/calculation-info-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calculationInfoDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalDebitAmount").value(hasItem(DEFAULT_TOTAL_DEBIT_AMOUNT)))
            .andExpect(jsonPath("$.[*].destinationAmount").value(hasItem(DEFAULT_DESTINATION_AMOUNT)))
            .andExpect(jsonPath("$.[*].destinationExchangeRate").value(hasItem(DEFAULT_DESTINATION_EXCHANGE_RATE)))
            .andExpect(jsonPath("$.[*].destinationCurrencyIndicator").value(hasItem(DEFAULT_DESTINATION_CURRENCY_INDICATOR)))
            .andExpect(jsonPath("$.[*].discountAmount").value(hasItem(DEFAULT_DISCOUNT_AMOUNT)));
    }
    
    @Test
    @Transactional
    public void getCalculationInfoDetails() throws Exception {
        // Initialize the database
        calculationInfoDetailsRepository.saveAndFlush(calculationInfoDetails);

        // Get the calculationInfoDetails
        restCalculationInfoDetailsMockMvc.perform(get("/api/calculation-info-details/{id}", calculationInfoDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calculationInfoDetails.getId().intValue()))
            .andExpect(jsonPath("$.totalDebitAmount").value(DEFAULT_TOTAL_DEBIT_AMOUNT))
            .andExpect(jsonPath("$.destinationAmount").value(DEFAULT_DESTINATION_AMOUNT))
            .andExpect(jsonPath("$.destinationExchangeRate").value(DEFAULT_DESTINATION_EXCHANGE_RATE))
            .andExpect(jsonPath("$.destinationCurrencyIndicator").value(DEFAULT_DESTINATION_CURRENCY_INDICATOR))
            .andExpect(jsonPath("$.discountAmount").value(DEFAULT_DISCOUNT_AMOUNT));
    }
    @Test
    @Transactional
    public void getNonExistingCalculationInfoDetails() throws Exception {
        // Get the calculationInfoDetails
        restCalculationInfoDetailsMockMvc.perform(get("/api/calculation-info-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalculationInfoDetails() throws Exception {
        // Initialize the database
        calculationInfoDetailsRepository.saveAndFlush(calculationInfoDetails);

        int databaseSizeBeforeUpdate = calculationInfoDetailsRepository.findAll().size();

        // Update the calculationInfoDetails
        CalculationInfoDetails updatedCalculationInfoDetails = calculationInfoDetailsRepository.findById(calculationInfoDetails.getId()).get();
        // Disconnect from session so that the updates on updatedCalculationInfoDetails are not directly saved in db
        em.detach(updatedCalculationInfoDetails);
        updatedCalculationInfoDetails
            .totalDebitAmount(UPDATED_TOTAL_DEBIT_AMOUNT)
            .destinationAmount(UPDATED_DESTINATION_AMOUNT)
            .destinationExchangeRate(UPDATED_DESTINATION_EXCHANGE_RATE)
            .destinationCurrencyIndicator(UPDATED_DESTINATION_CURRENCY_INDICATOR)
            .discountAmount(UPDATED_DISCOUNT_AMOUNT);

        restCalculationInfoDetailsMockMvc.perform(put("/api/calculation-info-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalculationInfoDetails)))
            .andExpect(status().isOk());

        // Validate the CalculationInfoDetails in the database
        List<CalculationInfoDetails> calculationInfoDetailsList = calculationInfoDetailsRepository.findAll();
        assertThat(calculationInfoDetailsList).hasSize(databaseSizeBeforeUpdate);
        CalculationInfoDetails testCalculationInfoDetails = calculationInfoDetailsList.get(calculationInfoDetailsList.size() - 1);
        assertThat(testCalculationInfoDetails.getTotalDebitAmount()).isEqualTo(UPDATED_TOTAL_DEBIT_AMOUNT);
        assertThat(testCalculationInfoDetails.getDestinationAmount()).isEqualTo(UPDATED_DESTINATION_AMOUNT);
        assertThat(testCalculationInfoDetails.getDestinationExchangeRate()).isEqualTo(UPDATED_DESTINATION_EXCHANGE_RATE);
        assertThat(testCalculationInfoDetails.getDestinationCurrencyIndicator()).isEqualTo(UPDATED_DESTINATION_CURRENCY_INDICATOR);
        assertThat(testCalculationInfoDetails.getDiscountAmount()).isEqualTo(UPDATED_DISCOUNT_AMOUNT);

        // Validate the CalculationInfoDetails in Elasticsearch
        verify(mockCalculationInfoDetailsSearchRepository, times(1)).save(testCalculationInfoDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingCalculationInfoDetails() throws Exception {
        int databaseSizeBeforeUpdate = calculationInfoDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalculationInfoDetailsMockMvc.perform(put("/api/calculation-info-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calculationInfoDetails)))
            .andExpect(status().isBadRequest());

        // Validate the CalculationInfoDetails in the database
        List<CalculationInfoDetails> calculationInfoDetailsList = calculationInfoDetailsRepository.findAll();
        assertThat(calculationInfoDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CalculationInfoDetails in Elasticsearch
        verify(mockCalculationInfoDetailsSearchRepository, times(0)).save(calculationInfoDetails);
    }

    @Test
    @Transactional
    public void deleteCalculationInfoDetails() throws Exception {
        // Initialize the database
        calculationInfoDetailsRepository.saveAndFlush(calculationInfoDetails);

        int databaseSizeBeforeDelete = calculationInfoDetailsRepository.findAll().size();

        // Delete the calculationInfoDetails
        restCalculationInfoDetailsMockMvc.perform(delete("/api/calculation-info-details/{id}", calculationInfoDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CalculationInfoDetails> calculationInfoDetailsList = calculationInfoDetailsRepository.findAll();
        assertThat(calculationInfoDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CalculationInfoDetails in Elasticsearch
        verify(mockCalculationInfoDetailsSearchRepository, times(1)).deleteById(calculationInfoDetails.getId());
    }

    @Test
    @Transactional
    public void searchCalculationInfoDetails() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        calculationInfoDetailsRepository.saveAndFlush(calculationInfoDetails);
        when(mockCalculationInfoDetailsSearchRepository.search(queryStringQuery("id:" + calculationInfoDetails.getId())))
            .thenReturn(Collections.singletonList(calculationInfoDetails));

        // Search the calculationInfoDetails
        restCalculationInfoDetailsMockMvc.perform(get("/api/_search/calculation-info-details?query=id:" + calculationInfoDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calculationInfoDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalDebitAmount").value(hasItem(DEFAULT_TOTAL_DEBIT_AMOUNT)))
            .andExpect(jsonPath("$.[*].destinationAmount").value(hasItem(DEFAULT_DESTINATION_AMOUNT)))
            .andExpect(jsonPath("$.[*].destinationExchangeRate").value(hasItem(DEFAULT_DESTINATION_EXCHANGE_RATE)))
            .andExpect(jsonPath("$.[*].destinationCurrencyIndicator").value(hasItem(DEFAULT_DESTINATION_CURRENCY_INDICATOR)))
            .andExpect(jsonPath("$.[*].discountAmount").value(hasItem(DEFAULT_DISCOUNT_AMOUNT)));
    }
}
