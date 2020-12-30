package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.CalculationInfo;
import sa.com.saib.dig.wlt.repository.CalculationInfoRepository;
import sa.com.saib.dig.wlt.repository.search.CalculationInfoSearchRepository;

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
 * Integration tests for the {@link CalculationInfoResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CalculationInfoResourceIT {

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_BENEFICIARY_ID = 1;
    private static final Integer UPDATED_BENEFICIARY_ID = 2;

    private static final String DEFAULT_FROM_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_FROM_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_TO_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_TO_CURRENCY = "BBBBBBBBBB";

    private static final Float DEFAULT_TRANSACTION_AMOUNT = 1F;
    private static final Float UPDATED_TRANSACTION_AMOUNT = 2F;

    private static final String DEFAULT_TRANSACTION_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_CURRENCY = "BBBBBBBBBB";

    @Autowired
    private CalculationInfoRepository calculationInfoRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.CalculationInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CalculationInfoSearchRepository mockCalculationInfoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalculationInfoMockMvc;

    private CalculationInfo calculationInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalculationInfo createEntity(EntityManager em) {
        CalculationInfo calculationInfo = new CalculationInfo()
            .customerId(DEFAULT_CUSTOMER_ID)
            .beneficiaryId(DEFAULT_BENEFICIARY_ID)
            .fromCurrency(DEFAULT_FROM_CURRENCY)
            .toCurrency(DEFAULT_TO_CURRENCY)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .transactionCurrency(DEFAULT_TRANSACTION_CURRENCY);
        return calculationInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalculationInfo createUpdatedEntity(EntityManager em) {
        CalculationInfo calculationInfo = new CalculationInfo()
            .customerId(UPDATED_CUSTOMER_ID)
            .beneficiaryId(UPDATED_BENEFICIARY_ID)
            .fromCurrency(UPDATED_FROM_CURRENCY)
            .toCurrency(UPDATED_TO_CURRENCY)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .transactionCurrency(UPDATED_TRANSACTION_CURRENCY);
        return calculationInfo;
    }

    @BeforeEach
    public void initTest() {
        calculationInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalculationInfo() throws Exception {
        int databaseSizeBeforeCreate = calculationInfoRepository.findAll().size();
        // Create the CalculationInfo
        restCalculationInfoMockMvc.perform(post("/api/calculation-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calculationInfo)))
            .andExpect(status().isCreated());

        // Validate the CalculationInfo in the database
        List<CalculationInfo> calculationInfoList = calculationInfoRepository.findAll();
        assertThat(calculationInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CalculationInfo testCalculationInfo = calculationInfoList.get(calculationInfoList.size() - 1);
        assertThat(testCalculationInfo.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testCalculationInfo.getBeneficiaryId()).isEqualTo(DEFAULT_BENEFICIARY_ID);
        assertThat(testCalculationInfo.getFromCurrency()).isEqualTo(DEFAULT_FROM_CURRENCY);
        assertThat(testCalculationInfo.getToCurrency()).isEqualTo(DEFAULT_TO_CURRENCY);
        assertThat(testCalculationInfo.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testCalculationInfo.getTransactionCurrency()).isEqualTo(DEFAULT_TRANSACTION_CURRENCY);

        // Validate the CalculationInfo in Elasticsearch
        verify(mockCalculationInfoSearchRepository, times(1)).save(testCalculationInfo);
    }

    @Test
    @Transactional
    public void createCalculationInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calculationInfoRepository.findAll().size();

        // Create the CalculationInfo with an existing ID
        calculationInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalculationInfoMockMvc.perform(post("/api/calculation-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calculationInfo)))
            .andExpect(status().isBadRequest());

        // Validate the CalculationInfo in the database
        List<CalculationInfo> calculationInfoList = calculationInfoRepository.findAll();
        assertThat(calculationInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CalculationInfo in Elasticsearch
        verify(mockCalculationInfoSearchRepository, times(0)).save(calculationInfo);
    }


    @Test
    @Transactional
    public void getAllCalculationInfos() throws Exception {
        // Initialize the database
        calculationInfoRepository.saveAndFlush(calculationInfo);

        // Get all the calculationInfoList
        restCalculationInfoMockMvc.perform(get("/api/calculation-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calculationInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].beneficiaryId").value(hasItem(DEFAULT_BENEFICIARY_ID)))
            .andExpect(jsonPath("$.[*].fromCurrency").value(hasItem(DEFAULT_FROM_CURRENCY)))
            .andExpect(jsonPath("$.[*].toCurrency").value(hasItem(DEFAULT_TO_CURRENCY)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionCurrency").value(hasItem(DEFAULT_TRANSACTION_CURRENCY)));
    }
    
    @Test
    @Transactional
    public void getCalculationInfo() throws Exception {
        // Initialize the database
        calculationInfoRepository.saveAndFlush(calculationInfo);

        // Get the calculationInfo
        restCalculationInfoMockMvc.perform(get("/api/calculation-infos/{id}", calculationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calculationInfo.getId().intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.beneficiaryId").value(DEFAULT_BENEFICIARY_ID))
            .andExpect(jsonPath("$.fromCurrency").value(DEFAULT_FROM_CURRENCY))
            .andExpect(jsonPath("$.toCurrency").value(DEFAULT_TO_CURRENCY))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.transactionCurrency").value(DEFAULT_TRANSACTION_CURRENCY));
    }
    @Test
    @Transactional
    public void getNonExistingCalculationInfo() throws Exception {
        // Get the calculationInfo
        restCalculationInfoMockMvc.perform(get("/api/calculation-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalculationInfo() throws Exception {
        // Initialize the database
        calculationInfoRepository.saveAndFlush(calculationInfo);

        int databaseSizeBeforeUpdate = calculationInfoRepository.findAll().size();

        // Update the calculationInfo
        CalculationInfo updatedCalculationInfo = calculationInfoRepository.findById(calculationInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCalculationInfo are not directly saved in db
        em.detach(updatedCalculationInfo);
        updatedCalculationInfo
            .customerId(UPDATED_CUSTOMER_ID)
            .beneficiaryId(UPDATED_BENEFICIARY_ID)
            .fromCurrency(UPDATED_FROM_CURRENCY)
            .toCurrency(UPDATED_TO_CURRENCY)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .transactionCurrency(UPDATED_TRANSACTION_CURRENCY);

        restCalculationInfoMockMvc.perform(put("/api/calculation-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalculationInfo)))
            .andExpect(status().isOk());

        // Validate the CalculationInfo in the database
        List<CalculationInfo> calculationInfoList = calculationInfoRepository.findAll();
        assertThat(calculationInfoList).hasSize(databaseSizeBeforeUpdate);
        CalculationInfo testCalculationInfo = calculationInfoList.get(calculationInfoList.size() - 1);
        assertThat(testCalculationInfo.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCalculationInfo.getBeneficiaryId()).isEqualTo(UPDATED_BENEFICIARY_ID);
        assertThat(testCalculationInfo.getFromCurrency()).isEqualTo(UPDATED_FROM_CURRENCY);
        assertThat(testCalculationInfo.getToCurrency()).isEqualTo(UPDATED_TO_CURRENCY);
        assertThat(testCalculationInfo.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testCalculationInfo.getTransactionCurrency()).isEqualTo(UPDATED_TRANSACTION_CURRENCY);

        // Validate the CalculationInfo in Elasticsearch
        verify(mockCalculationInfoSearchRepository, times(1)).save(testCalculationInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingCalculationInfo() throws Exception {
        int databaseSizeBeforeUpdate = calculationInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalculationInfoMockMvc.perform(put("/api/calculation-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calculationInfo)))
            .andExpect(status().isBadRequest());

        // Validate the CalculationInfo in the database
        List<CalculationInfo> calculationInfoList = calculationInfoRepository.findAll();
        assertThat(calculationInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CalculationInfo in Elasticsearch
        verify(mockCalculationInfoSearchRepository, times(0)).save(calculationInfo);
    }

    @Test
    @Transactional
    public void deleteCalculationInfo() throws Exception {
        // Initialize the database
        calculationInfoRepository.saveAndFlush(calculationInfo);

        int databaseSizeBeforeDelete = calculationInfoRepository.findAll().size();

        // Delete the calculationInfo
        restCalculationInfoMockMvc.perform(delete("/api/calculation-infos/{id}", calculationInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CalculationInfo> calculationInfoList = calculationInfoRepository.findAll();
        assertThat(calculationInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CalculationInfo in Elasticsearch
        verify(mockCalculationInfoSearchRepository, times(1)).deleteById(calculationInfo.getId());
    }

    @Test
    @Transactional
    public void searchCalculationInfo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        calculationInfoRepository.saveAndFlush(calculationInfo);
        when(mockCalculationInfoSearchRepository.search(queryStringQuery("id:" + calculationInfo.getId())))
            .thenReturn(Collections.singletonList(calculationInfo));

        // Search the calculationInfo
        restCalculationInfoMockMvc.perform(get("/api/_search/calculation-infos?query=id:" + calculationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calculationInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].beneficiaryId").value(hasItem(DEFAULT_BENEFICIARY_ID)))
            .andExpect(jsonPath("$.[*].fromCurrency").value(hasItem(DEFAULT_FROM_CURRENCY)))
            .andExpect(jsonPath("$.[*].toCurrency").value(hasItem(DEFAULT_TO_CURRENCY)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionCurrency").value(hasItem(DEFAULT_TRANSACTION_CURRENCY)));
    }
}
