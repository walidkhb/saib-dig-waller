package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.TransactionDetails;
import sa.com.saib.dig.wlt.repository.TransactionDetailsRepository;
import sa.com.saib.dig.wlt.repository.search.TransactionDetailsSearchRepository;

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
 * Integration tests for the {@link TransactionDetailsResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionDetailsResourceIT {

    private static final String DEFAULT_DEBIT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DEBIT_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DEBIT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_DEBIT_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE_RATE = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_RATE = "BBBBBBBBBB";

    private static final String DEFAULT_FEES = "AAAAAAAAAA";
    private static final String UPDATED_FEES = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE_OF_TRANSFER = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE_OF_TRANSFER = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_REFERENCE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.TransactionDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransactionDetailsSearchRepository mockTransactionDetailsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionDetailsMockMvc;

    private TransactionDetails transactionDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionDetails createEntity(EntityManager em) {
        TransactionDetails transactionDetails = new TransactionDetails()
            .debitAmount(DEFAULT_DEBIT_AMOUNT)
            .debitCurrency(DEFAULT_DEBIT_CURRENCY)
            .creditAmount(DEFAULT_CREDIT_AMOUNT)
            .creditCurrency(DEFAULT_CREDIT_CURRENCY)
            .exchangeRate(DEFAULT_EXCHANGE_RATE)
            .fees(DEFAULT_FEES)
            .purposeOfTransfer(DEFAULT_PURPOSE_OF_TRANSFER)
            .partnerReferenceNumber(DEFAULT_PARTNER_REFERENCE_NUMBER);
        return transactionDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionDetails createUpdatedEntity(EntityManager em) {
        TransactionDetails transactionDetails = new TransactionDetails()
            .debitAmount(UPDATED_DEBIT_AMOUNT)
            .debitCurrency(UPDATED_DEBIT_CURRENCY)
            .creditAmount(UPDATED_CREDIT_AMOUNT)
            .creditCurrency(UPDATED_CREDIT_CURRENCY)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .fees(UPDATED_FEES)
            .purposeOfTransfer(UPDATED_PURPOSE_OF_TRANSFER)
            .partnerReferenceNumber(UPDATED_PARTNER_REFERENCE_NUMBER);
        return transactionDetails;
    }

    @BeforeEach
    public void initTest() {
        transactionDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionDetails() throws Exception {
        int databaseSizeBeforeCreate = transactionDetailsRepository.findAll().size();
        // Create the TransactionDetails
        restTransactionDetailsMockMvc.perform(post("/api/transaction-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDetails)))
            .andExpect(status().isCreated());

        // Validate the TransactionDetails in the database
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        assertThat(transactionDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionDetails testTransactionDetails = transactionDetailsList.get(transactionDetailsList.size() - 1);
        assertThat(testTransactionDetails.getDebitAmount()).isEqualTo(DEFAULT_DEBIT_AMOUNT);
        assertThat(testTransactionDetails.getDebitCurrency()).isEqualTo(DEFAULT_DEBIT_CURRENCY);
        assertThat(testTransactionDetails.getCreditAmount()).isEqualTo(DEFAULT_CREDIT_AMOUNT);
        assertThat(testTransactionDetails.getCreditCurrency()).isEqualTo(DEFAULT_CREDIT_CURRENCY);
        assertThat(testTransactionDetails.getExchangeRate()).isEqualTo(DEFAULT_EXCHANGE_RATE);
        assertThat(testTransactionDetails.getFees()).isEqualTo(DEFAULT_FEES);
        assertThat(testTransactionDetails.getPurposeOfTransfer()).isEqualTo(DEFAULT_PURPOSE_OF_TRANSFER);
        assertThat(testTransactionDetails.getPartnerReferenceNumber()).isEqualTo(DEFAULT_PARTNER_REFERENCE_NUMBER);

        // Validate the TransactionDetails in Elasticsearch
        verify(mockTransactionDetailsSearchRepository, times(1)).save(testTransactionDetails);
    }

    @Test
    @Transactional
    public void createTransactionDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionDetailsRepository.findAll().size();

        // Create the TransactionDetails with an existing ID
        transactionDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionDetailsMockMvc.perform(post("/api/transaction-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDetails)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionDetails in the database
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        assertThat(transactionDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the TransactionDetails in Elasticsearch
        verify(mockTransactionDetailsSearchRepository, times(0)).save(transactionDetails);
    }


    @Test
    @Transactional
    public void getAllTransactionDetails() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList
        restTransactionDetailsMockMvc.perform(get("/api/transaction-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].debitAmount").value(hasItem(DEFAULT_DEBIT_AMOUNT)))
            .andExpect(jsonPath("$.[*].debitCurrency").value(hasItem(DEFAULT_DEBIT_CURRENCY)))
            .andExpect(jsonPath("$.[*].creditAmount").value(hasItem(DEFAULT_CREDIT_AMOUNT)))
            .andExpect(jsonPath("$.[*].creditCurrency").value(hasItem(DEFAULT_CREDIT_CURRENCY)))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE)))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES)))
            .andExpect(jsonPath("$.[*].purposeOfTransfer").value(hasItem(DEFAULT_PURPOSE_OF_TRANSFER)))
            .andExpect(jsonPath("$.[*].partnerReferenceNumber").value(hasItem(DEFAULT_PARTNER_REFERENCE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getTransactionDetails() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get the transactionDetails
        restTransactionDetailsMockMvc.perform(get("/api/transaction-details/{id}", transactionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionDetails.getId().intValue()))
            .andExpect(jsonPath("$.debitAmount").value(DEFAULT_DEBIT_AMOUNT))
            .andExpect(jsonPath("$.debitCurrency").value(DEFAULT_DEBIT_CURRENCY))
            .andExpect(jsonPath("$.creditAmount").value(DEFAULT_CREDIT_AMOUNT))
            .andExpect(jsonPath("$.creditCurrency").value(DEFAULT_CREDIT_CURRENCY))
            .andExpect(jsonPath("$.exchangeRate").value(DEFAULT_EXCHANGE_RATE))
            .andExpect(jsonPath("$.fees").value(DEFAULT_FEES))
            .andExpect(jsonPath("$.purposeOfTransfer").value(DEFAULT_PURPOSE_OF_TRANSFER))
            .andExpect(jsonPath("$.partnerReferenceNumber").value(DEFAULT_PARTNER_REFERENCE_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingTransactionDetails() throws Exception {
        // Get the transactionDetails
        restTransactionDetailsMockMvc.perform(get("/api/transaction-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionDetails() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        int databaseSizeBeforeUpdate = transactionDetailsRepository.findAll().size();

        // Update the transactionDetails
        TransactionDetails updatedTransactionDetails = transactionDetailsRepository.findById(transactionDetails.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionDetails are not directly saved in db
        em.detach(updatedTransactionDetails);
        updatedTransactionDetails
            .debitAmount(UPDATED_DEBIT_AMOUNT)
            .debitCurrency(UPDATED_DEBIT_CURRENCY)
            .creditAmount(UPDATED_CREDIT_AMOUNT)
            .creditCurrency(UPDATED_CREDIT_CURRENCY)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .fees(UPDATED_FEES)
            .purposeOfTransfer(UPDATED_PURPOSE_OF_TRANSFER)
            .partnerReferenceNumber(UPDATED_PARTNER_REFERENCE_NUMBER);

        restTransactionDetailsMockMvc.perform(put("/api/transaction-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionDetails)))
            .andExpect(status().isOk());

        // Validate the TransactionDetails in the database
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        assertThat(transactionDetailsList).hasSize(databaseSizeBeforeUpdate);
        TransactionDetails testTransactionDetails = transactionDetailsList.get(transactionDetailsList.size() - 1);
        assertThat(testTransactionDetails.getDebitAmount()).isEqualTo(UPDATED_DEBIT_AMOUNT);
        assertThat(testTransactionDetails.getDebitCurrency()).isEqualTo(UPDATED_DEBIT_CURRENCY);
        assertThat(testTransactionDetails.getCreditAmount()).isEqualTo(UPDATED_CREDIT_AMOUNT);
        assertThat(testTransactionDetails.getCreditCurrency()).isEqualTo(UPDATED_CREDIT_CURRENCY);
        assertThat(testTransactionDetails.getExchangeRate()).isEqualTo(UPDATED_EXCHANGE_RATE);
        assertThat(testTransactionDetails.getFees()).isEqualTo(UPDATED_FEES);
        assertThat(testTransactionDetails.getPurposeOfTransfer()).isEqualTo(UPDATED_PURPOSE_OF_TRANSFER);
        assertThat(testTransactionDetails.getPartnerReferenceNumber()).isEqualTo(UPDATED_PARTNER_REFERENCE_NUMBER);

        // Validate the TransactionDetails in Elasticsearch
        verify(mockTransactionDetailsSearchRepository, times(1)).save(testTransactionDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionDetails() throws Exception {
        int databaseSizeBeforeUpdate = transactionDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionDetailsMockMvc.perform(put("/api/transaction-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDetails)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionDetails in the database
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        assertThat(transactionDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TransactionDetails in Elasticsearch
        verify(mockTransactionDetailsSearchRepository, times(0)).save(transactionDetails);
    }

    @Test
    @Transactional
    public void deleteTransactionDetails() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        int databaseSizeBeforeDelete = transactionDetailsRepository.findAll().size();

        // Delete the transactionDetails
        restTransactionDetailsMockMvc.perform(delete("/api/transaction-details/{id}", transactionDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        assertThat(transactionDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TransactionDetails in Elasticsearch
        verify(mockTransactionDetailsSearchRepository, times(1)).deleteById(transactionDetails.getId());
    }

    @Test
    @Transactional
    public void searchTransactionDetails() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);
        when(mockTransactionDetailsSearchRepository.search(queryStringQuery("id:" + transactionDetails.getId())))
            .thenReturn(Collections.singletonList(transactionDetails));

        // Search the transactionDetails
        restTransactionDetailsMockMvc.perform(get("/api/_search/transaction-details?query=id:" + transactionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].debitAmount").value(hasItem(DEFAULT_DEBIT_AMOUNT)))
            .andExpect(jsonPath("$.[*].debitCurrency").value(hasItem(DEFAULT_DEBIT_CURRENCY)))
            .andExpect(jsonPath("$.[*].creditAmount").value(hasItem(DEFAULT_CREDIT_AMOUNT)))
            .andExpect(jsonPath("$.[*].creditCurrency").value(hasItem(DEFAULT_CREDIT_CURRENCY)))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE)))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES)))
            .andExpect(jsonPath("$.[*].purposeOfTransfer").value(hasItem(DEFAULT_PURPOSE_OF_TRANSFER)))
            .andExpect(jsonPath("$.[*].partnerReferenceNumber").value(hasItem(DEFAULT_PARTNER_REFERENCE_NUMBER)));
    }
}
