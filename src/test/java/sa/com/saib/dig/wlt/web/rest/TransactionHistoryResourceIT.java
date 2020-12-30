package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.TransactionHistory;
import sa.com.saib.dig.wlt.repository.TransactionHistoryRepository;
import sa.com.saib.dig.wlt.repository.search.TransactionHistorySearchRepository;

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
 * Integration tests for the {@link TransactionHistoryResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionHistoryResourceIT {

    private static final String DEFAULT_DATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_DATE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_T_R_REFERENCE_NO = "AAAAAAAAAA";
    private static final String UPDATED_T_R_REFERENCE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_MODE = "AAAAAAAAAA";
    private static final String UPDATED_PAY_MODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_OUT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_PAY_OUT_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_OUT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_PAY_OUT_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE_RATE = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_RATE = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_IN_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_PAY_IN_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_IN_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_PAY_IN_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_COMMISSION = "AAAAAAAAAA";
    private static final String UPDATED_COMMISSION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE_OF_TRANSFER = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE_OF_TRANSFER = "BBBBBBBBBB";

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.TransactionHistorySearchRepositoryMockConfiguration
     */
    @Autowired
    private TransactionHistorySearchRepository mockTransactionHistorySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionHistoryMockMvc;

    private TransactionHistory transactionHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionHistory createEntity(EntityManager em) {
        TransactionHistory transactionHistory = new TransactionHistory()
            .dateTime(DEFAULT_DATE_TIME)
            .tRReferenceNo(DEFAULT_T_R_REFERENCE_NO)
            .beneficiaryName(DEFAULT_BENEFICIARY_NAME)
            .payMode(DEFAULT_PAY_MODE)
            .bankName(DEFAULT_BANK_NAME)
            .payOutAmount(DEFAULT_PAY_OUT_AMOUNT)
            .payOutCurrency(DEFAULT_PAY_OUT_CURRENCY)
            .exchangeRate(DEFAULT_EXCHANGE_RATE)
            .payInAmount(DEFAULT_PAY_IN_AMOUNT)
            .payInCurrency(DEFAULT_PAY_IN_CURRENCY)
            .commission(DEFAULT_COMMISSION)
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION)
            .purposeCode(DEFAULT_PURPOSE_CODE)
            .purposeOfTransfer(DEFAULT_PURPOSE_OF_TRANSFER);
        return transactionHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionHistory createUpdatedEntity(EntityManager em) {
        TransactionHistory transactionHistory = new TransactionHistory()
            .dateTime(UPDATED_DATE_TIME)
            .tRReferenceNo(UPDATED_T_R_REFERENCE_NO)
            .beneficiaryName(UPDATED_BENEFICIARY_NAME)
            .payMode(UPDATED_PAY_MODE)
            .bankName(UPDATED_BANK_NAME)
            .payOutAmount(UPDATED_PAY_OUT_AMOUNT)
            .payOutCurrency(UPDATED_PAY_OUT_CURRENCY)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .payInAmount(UPDATED_PAY_IN_AMOUNT)
            .payInCurrency(UPDATED_PAY_IN_CURRENCY)
            .commission(UPDATED_COMMISSION)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .purposeCode(UPDATED_PURPOSE_CODE)
            .purposeOfTransfer(UPDATED_PURPOSE_OF_TRANSFER);
        return transactionHistory;
    }

    @BeforeEach
    public void initTest() {
        transactionHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionHistory() throws Exception {
        int databaseSizeBeforeCreate = transactionHistoryRepository.findAll().size();
        // Create the TransactionHistory
        restTransactionHistoryMockMvc.perform(post("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistory)))
            .andExpect(status().isCreated());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionHistory testTransactionHistory = transactionHistoryList.get(transactionHistoryList.size() - 1);
        assertThat(testTransactionHistory.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testTransactionHistory.gettRReferenceNo()).isEqualTo(DEFAULT_T_R_REFERENCE_NO);
        assertThat(testTransactionHistory.getBeneficiaryName()).isEqualTo(DEFAULT_BENEFICIARY_NAME);
        assertThat(testTransactionHistory.getPayMode()).isEqualTo(DEFAULT_PAY_MODE);
        assertThat(testTransactionHistory.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testTransactionHistory.getPayOutAmount()).isEqualTo(DEFAULT_PAY_OUT_AMOUNT);
        assertThat(testTransactionHistory.getPayOutCurrency()).isEqualTo(DEFAULT_PAY_OUT_CURRENCY);
        assertThat(testTransactionHistory.getExchangeRate()).isEqualTo(DEFAULT_EXCHANGE_RATE);
        assertThat(testTransactionHistory.getPayInAmount()).isEqualTo(DEFAULT_PAY_IN_AMOUNT);
        assertThat(testTransactionHistory.getPayInCurrency()).isEqualTo(DEFAULT_PAY_IN_CURRENCY);
        assertThat(testTransactionHistory.getCommission()).isEqualTo(DEFAULT_COMMISSION);
        assertThat(testTransactionHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransactionHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTransactionHistory.getPurposeCode()).isEqualTo(DEFAULT_PURPOSE_CODE);
        assertThat(testTransactionHistory.getPurposeOfTransfer()).isEqualTo(DEFAULT_PURPOSE_OF_TRANSFER);

        // Validate the TransactionHistory in Elasticsearch
        verify(mockTransactionHistorySearchRepository, times(1)).save(testTransactionHistory);
    }

    @Test
    @Transactional
    public void createTransactionHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionHistoryRepository.findAll().size();

        // Create the TransactionHistory with an existing ID
        transactionHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionHistoryMockMvc.perform(post("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistory)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the TransactionHistory in Elasticsearch
        verify(mockTransactionHistorySearchRepository, times(0)).save(transactionHistory);
    }


    @Test
    @Transactional
    public void getAllTransactionHistories() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList
        restTransactionHistoryMockMvc.perform(get("/api/transaction-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME)))
            .andExpect(jsonPath("$.[*].tRReferenceNo").value(hasItem(DEFAULT_T_R_REFERENCE_NO)))
            .andExpect(jsonPath("$.[*].beneficiaryName").value(hasItem(DEFAULT_BENEFICIARY_NAME)))
            .andExpect(jsonPath("$.[*].payMode").value(hasItem(DEFAULT_PAY_MODE)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].payOutAmount").value(hasItem(DEFAULT_PAY_OUT_AMOUNT)))
            .andExpect(jsonPath("$.[*].payOutCurrency").value(hasItem(DEFAULT_PAY_OUT_CURRENCY)))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE)))
            .andExpect(jsonPath("$.[*].payInAmount").value(hasItem(DEFAULT_PAY_IN_AMOUNT)))
            .andExpect(jsonPath("$.[*].payInCurrency").value(hasItem(DEFAULT_PAY_IN_CURRENCY)))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].purposeCode").value(hasItem(DEFAULT_PURPOSE_CODE)))
            .andExpect(jsonPath("$.[*].purposeOfTransfer").value(hasItem(DEFAULT_PURPOSE_OF_TRANSFER)));
    }
    
    @Test
    @Transactional
    public void getTransactionHistory() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get the transactionHistory
        restTransactionHistoryMockMvc.perform(get("/api/transaction-histories/{id}", transactionHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionHistory.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME))
            .andExpect(jsonPath("$.tRReferenceNo").value(DEFAULT_T_R_REFERENCE_NO))
            .andExpect(jsonPath("$.beneficiaryName").value(DEFAULT_BENEFICIARY_NAME))
            .andExpect(jsonPath("$.payMode").value(DEFAULT_PAY_MODE))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.payOutAmount").value(DEFAULT_PAY_OUT_AMOUNT))
            .andExpect(jsonPath("$.payOutCurrency").value(DEFAULT_PAY_OUT_CURRENCY))
            .andExpect(jsonPath("$.exchangeRate").value(DEFAULT_EXCHANGE_RATE))
            .andExpect(jsonPath("$.payInAmount").value(DEFAULT_PAY_IN_AMOUNT))
            .andExpect(jsonPath("$.payInCurrency").value(DEFAULT_PAY_IN_CURRENCY))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.purposeCode").value(DEFAULT_PURPOSE_CODE))
            .andExpect(jsonPath("$.purposeOfTransfer").value(DEFAULT_PURPOSE_OF_TRANSFER));
    }
    @Test
    @Transactional
    public void getNonExistingTransactionHistory() throws Exception {
        // Get the transactionHistory
        restTransactionHistoryMockMvc.perform(get("/api/transaction-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionHistory() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();

        // Update the transactionHistory
        TransactionHistory updatedTransactionHistory = transactionHistoryRepository.findById(transactionHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionHistory are not directly saved in db
        em.detach(updatedTransactionHistory);
        updatedTransactionHistory
            .dateTime(UPDATED_DATE_TIME)
            .tRReferenceNo(UPDATED_T_R_REFERENCE_NO)
            .beneficiaryName(UPDATED_BENEFICIARY_NAME)
            .payMode(UPDATED_PAY_MODE)
            .bankName(UPDATED_BANK_NAME)
            .payOutAmount(UPDATED_PAY_OUT_AMOUNT)
            .payOutCurrency(UPDATED_PAY_OUT_CURRENCY)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .payInAmount(UPDATED_PAY_IN_AMOUNT)
            .payInCurrency(UPDATED_PAY_IN_CURRENCY)
            .commission(UPDATED_COMMISSION)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .purposeCode(UPDATED_PURPOSE_CODE)
            .purposeOfTransfer(UPDATED_PURPOSE_OF_TRANSFER);

        restTransactionHistoryMockMvc.perform(put("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionHistory)))
            .andExpect(status().isOk());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
        TransactionHistory testTransactionHistory = transactionHistoryList.get(transactionHistoryList.size() - 1);
        assertThat(testTransactionHistory.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testTransactionHistory.gettRReferenceNo()).isEqualTo(UPDATED_T_R_REFERENCE_NO);
        assertThat(testTransactionHistory.getBeneficiaryName()).isEqualTo(UPDATED_BENEFICIARY_NAME);
        assertThat(testTransactionHistory.getPayMode()).isEqualTo(UPDATED_PAY_MODE);
        assertThat(testTransactionHistory.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testTransactionHistory.getPayOutAmount()).isEqualTo(UPDATED_PAY_OUT_AMOUNT);
        assertThat(testTransactionHistory.getPayOutCurrency()).isEqualTo(UPDATED_PAY_OUT_CURRENCY);
        assertThat(testTransactionHistory.getExchangeRate()).isEqualTo(UPDATED_EXCHANGE_RATE);
        assertThat(testTransactionHistory.getPayInAmount()).isEqualTo(UPDATED_PAY_IN_AMOUNT);
        assertThat(testTransactionHistory.getPayInCurrency()).isEqualTo(UPDATED_PAY_IN_CURRENCY);
        assertThat(testTransactionHistory.getCommission()).isEqualTo(UPDATED_COMMISSION);
        assertThat(testTransactionHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransactionHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTransactionHistory.getPurposeCode()).isEqualTo(UPDATED_PURPOSE_CODE);
        assertThat(testTransactionHistory.getPurposeOfTransfer()).isEqualTo(UPDATED_PURPOSE_OF_TRANSFER);

        // Validate the TransactionHistory in Elasticsearch
        verify(mockTransactionHistorySearchRepository, times(1)).save(testTransactionHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionHistory() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionHistoryMockMvc.perform(put("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistory)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TransactionHistory in Elasticsearch
        verify(mockTransactionHistorySearchRepository, times(0)).save(transactionHistory);
    }

    @Test
    @Transactional
    public void deleteTransactionHistory() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        int databaseSizeBeforeDelete = transactionHistoryRepository.findAll().size();

        // Delete the transactionHistory
        restTransactionHistoryMockMvc.perform(delete("/api/transaction-histories/{id}", transactionHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TransactionHistory in Elasticsearch
        verify(mockTransactionHistorySearchRepository, times(1)).deleteById(transactionHistory.getId());
    }

    @Test
    @Transactional
    public void searchTransactionHistory() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);
        when(mockTransactionHistorySearchRepository.search(queryStringQuery("id:" + transactionHistory.getId())))
            .thenReturn(Collections.singletonList(transactionHistory));

        // Search the transactionHistory
        restTransactionHistoryMockMvc.perform(get("/api/_search/transaction-histories?query=id:" + transactionHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME)))
            .andExpect(jsonPath("$.[*].tRReferenceNo").value(hasItem(DEFAULT_T_R_REFERENCE_NO)))
            .andExpect(jsonPath("$.[*].beneficiaryName").value(hasItem(DEFAULT_BENEFICIARY_NAME)))
            .andExpect(jsonPath("$.[*].payMode").value(hasItem(DEFAULT_PAY_MODE)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].payOutAmount").value(hasItem(DEFAULT_PAY_OUT_AMOUNT)))
            .andExpect(jsonPath("$.[*].payOutCurrency").value(hasItem(DEFAULT_PAY_OUT_CURRENCY)))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE)))
            .andExpect(jsonPath("$.[*].payInAmount").value(hasItem(DEFAULT_PAY_IN_AMOUNT)))
            .andExpect(jsonPath("$.[*].payInCurrency").value(hasItem(DEFAULT_PAY_IN_CURRENCY)))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].purposeCode").value(hasItem(DEFAULT_PURPOSE_CODE)))
            .andExpect(jsonPath("$.[*].purposeOfTransfer").value(hasItem(DEFAULT_PURPOSE_OF_TRANSFER)));
    }
}
