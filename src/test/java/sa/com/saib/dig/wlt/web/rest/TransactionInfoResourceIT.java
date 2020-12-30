package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.TransactionInfo;
import sa.com.saib.dig.wlt.repository.TransactionInfoRepository;
import sa.com.saib.dig.wlt.repository.search.TransactionInfoSearchRepository;

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
 * Integration tests for the {@link TransactionInfoResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionInfoResourceIT {

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_DEBIT_INDICATOR = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_DEBIT_INDICATOR = "BBBBBBBBBB";

    private static final String DEFAULT_CREATION_DATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CREATION_DATE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private TransactionInfoRepository transactionInfoRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.TransactionInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransactionInfoSearchRepository mockTransactionInfoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionInfoMockMvc;

    private TransactionInfo transactionInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionInfo createEntity(EntityManager em) {
        TransactionInfo transactionInfo = new TransactionInfo()
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .creditDebitIndicator(DEFAULT_CREDIT_DEBIT_INDICATOR)
            .creationDateTime(DEFAULT_CREATION_DATE_TIME)
            .status(DEFAULT_STATUS);
        return transactionInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionInfo createUpdatedEntity(EntityManager em) {
        TransactionInfo transactionInfo = new TransactionInfo()
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .creditDebitIndicator(UPDATED_CREDIT_DEBIT_INDICATOR)
            .creationDateTime(UPDATED_CREATION_DATE_TIME)
            .status(UPDATED_STATUS);
        return transactionInfo;
    }

    @BeforeEach
    public void initTest() {
        transactionInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionInfo() throws Exception {
        int databaseSizeBeforeCreate = transactionInfoRepository.findAll().size();
        // Create the TransactionInfo
        restTransactionInfoMockMvc.perform(post("/api/transaction-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionInfo)))
            .andExpect(status().isCreated());

        // Validate the TransactionInfo in the database
        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionInfo testTransactionInfo = transactionInfoList.get(transactionInfoList.size() - 1);
        assertThat(testTransactionInfo.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testTransactionInfo.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testTransactionInfo.getCreditDebitIndicator()).isEqualTo(DEFAULT_CREDIT_DEBIT_INDICATOR);
        assertThat(testTransactionInfo.getCreationDateTime()).isEqualTo(DEFAULT_CREATION_DATE_TIME);
        assertThat(testTransactionInfo.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the TransactionInfo in Elasticsearch
        verify(mockTransactionInfoSearchRepository, times(1)).save(testTransactionInfo);
    }

    @Test
    @Transactional
    public void createTransactionInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionInfoRepository.findAll().size();

        // Create the TransactionInfo with an existing ID
        transactionInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionInfoMockMvc.perform(post("/api/transaction-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionInfo)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionInfo in the database
        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TransactionInfo in Elasticsearch
        verify(mockTransactionInfoSearchRepository, times(0)).save(transactionInfo);
    }


    @Test
    @Transactional
    public void getAllTransactionInfos() throws Exception {
        // Initialize the database
        transactionInfoRepository.saveAndFlush(transactionInfo);

        // Get all the transactionInfoList
        restTransactionInfoMockMvc.perform(get("/api/transaction-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].creditDebitIndicator").value(hasItem(DEFAULT_CREDIT_DEBIT_INDICATOR)))
            .andExpect(jsonPath("$.[*].creationDateTime").value(hasItem(DEFAULT_CREATION_DATE_TIME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getTransactionInfo() throws Exception {
        // Initialize the database
        transactionInfoRepository.saveAndFlush(transactionInfo);

        // Get the transactionInfo
        restTransactionInfoMockMvc.perform(get("/api/transaction-infos/{id}", transactionInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionInfo.getId().intValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.creditDebitIndicator").value(DEFAULT_CREDIT_DEBIT_INDICATOR))
            .andExpect(jsonPath("$.creationDateTime").value(DEFAULT_CREATION_DATE_TIME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }
    @Test
    @Transactional
    public void getNonExistingTransactionInfo() throws Exception {
        // Get the transactionInfo
        restTransactionInfoMockMvc.perform(get("/api/transaction-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionInfo() throws Exception {
        // Initialize the database
        transactionInfoRepository.saveAndFlush(transactionInfo);

        int databaseSizeBeforeUpdate = transactionInfoRepository.findAll().size();

        // Update the transactionInfo
        TransactionInfo updatedTransactionInfo = transactionInfoRepository.findById(transactionInfo.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionInfo are not directly saved in db
        em.detach(updatedTransactionInfo);
        updatedTransactionInfo
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .creditDebitIndicator(UPDATED_CREDIT_DEBIT_INDICATOR)
            .creationDateTime(UPDATED_CREATION_DATE_TIME)
            .status(UPDATED_STATUS);

        restTransactionInfoMockMvc.perform(put("/api/transaction-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionInfo)))
            .andExpect(status().isOk());

        // Validate the TransactionInfo in the database
        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeUpdate);
        TransactionInfo testTransactionInfo = transactionInfoList.get(transactionInfoList.size() - 1);
        assertThat(testTransactionInfo.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testTransactionInfo.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testTransactionInfo.getCreditDebitIndicator()).isEqualTo(UPDATED_CREDIT_DEBIT_INDICATOR);
        assertThat(testTransactionInfo.getCreationDateTime()).isEqualTo(UPDATED_CREATION_DATE_TIME);
        assertThat(testTransactionInfo.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the TransactionInfo in Elasticsearch
        verify(mockTransactionInfoSearchRepository, times(1)).save(testTransactionInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionInfo() throws Exception {
        int databaseSizeBeforeUpdate = transactionInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionInfoMockMvc.perform(put("/api/transaction-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionInfo)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionInfo in the database
        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TransactionInfo in Elasticsearch
        verify(mockTransactionInfoSearchRepository, times(0)).save(transactionInfo);
    }

    @Test
    @Transactional
    public void deleteTransactionInfo() throws Exception {
        // Initialize the database
        transactionInfoRepository.saveAndFlush(transactionInfo);

        int databaseSizeBeforeDelete = transactionInfoRepository.findAll().size();

        // Delete the transactionInfo
        restTransactionInfoMockMvc.perform(delete("/api/transaction-infos/{id}", transactionInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TransactionInfo in Elasticsearch
        verify(mockTransactionInfoSearchRepository, times(1)).deleteById(transactionInfo.getId());
    }

    @Test
    @Transactional
    public void searchTransactionInfo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        transactionInfoRepository.saveAndFlush(transactionInfo);
        when(mockTransactionInfoSearchRepository.search(queryStringQuery("id:" + transactionInfo.getId())))
            .thenReturn(Collections.singletonList(transactionInfo));

        // Search the transactionInfo
        restTransactionInfoMockMvc.perform(get("/api/_search/transaction-infos?query=id:" + transactionInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].creditDebitIndicator").value(hasItem(DEFAULT_CREDIT_DEBIT_INDICATOR)))
            .andExpect(jsonPath("$.[*].creationDateTime").value(hasItem(DEFAULT_CREATION_DATE_TIME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
}
