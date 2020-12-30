package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.BankList;
import sa.com.saib.dig.wlt.repository.BankListRepository;
import sa.com.saib.dig.wlt.repository.search.BankListSearchRepository;

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
 * Integration tests for the {@link BankListResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BankListResourceIT {

    private static final String DEFAULT_BANK_ID = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_INDICATOR = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_INDICATOR = "BBBBBBBBBB";

    private static final String DEFAULT_FLAG_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_FLAG_LABEL = "BBBBBBBBBB";

    @Autowired
    private BankListRepository bankListRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.BankListSearchRepositoryMockConfiguration
     */
    @Autowired
    private BankListSearchRepository mockBankListSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankListMockMvc;

    private BankList bankList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankList createEntity(EntityManager em) {
        BankList bankList = new BankList()
            .bankId(DEFAULT_BANK_ID)
            .bankName(DEFAULT_BANK_NAME)
            .branchIndicator(DEFAULT_BRANCH_INDICATOR)
            .flagLabel(DEFAULT_FLAG_LABEL);
        return bankList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankList createUpdatedEntity(EntityManager em) {
        BankList bankList = new BankList()
            .bankId(UPDATED_BANK_ID)
            .bankName(UPDATED_BANK_NAME)
            .branchIndicator(UPDATED_BRANCH_INDICATOR)
            .flagLabel(UPDATED_FLAG_LABEL);
        return bankList;
    }

    @BeforeEach
    public void initTest() {
        bankList = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankList() throws Exception {
        int databaseSizeBeforeCreate = bankListRepository.findAll().size();
        // Create the BankList
        restBankListMockMvc.perform(post("/api/bank-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankList)))
            .andExpect(status().isCreated());

        // Validate the BankList in the database
        List<BankList> bankListList = bankListRepository.findAll();
        assertThat(bankListList).hasSize(databaseSizeBeforeCreate + 1);
        BankList testBankList = bankListList.get(bankListList.size() - 1);
        assertThat(testBankList.getBankId()).isEqualTo(DEFAULT_BANK_ID);
        assertThat(testBankList.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBankList.getBranchIndicator()).isEqualTo(DEFAULT_BRANCH_INDICATOR);
        assertThat(testBankList.getFlagLabel()).isEqualTo(DEFAULT_FLAG_LABEL);

        // Validate the BankList in Elasticsearch
        verify(mockBankListSearchRepository, times(1)).save(testBankList);
    }

    @Test
    @Transactional
    public void createBankListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankListRepository.findAll().size();

        // Create the BankList with an existing ID
        bankList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankListMockMvc.perform(post("/api/bank-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankList)))
            .andExpect(status().isBadRequest());

        // Validate the BankList in the database
        List<BankList> bankListList = bankListRepository.findAll();
        assertThat(bankListList).hasSize(databaseSizeBeforeCreate);

        // Validate the BankList in Elasticsearch
        verify(mockBankListSearchRepository, times(0)).save(bankList);
    }


    @Test
    @Transactional
    public void getAllBankLists() throws Exception {
        // Initialize the database
        bankListRepository.saveAndFlush(bankList);

        // Get all the bankListList
        restBankListMockMvc.perform(get("/api/bank-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankList.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankId").value(hasItem(DEFAULT_BANK_ID)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchIndicator").value(hasItem(DEFAULT_BRANCH_INDICATOR)))
            .andExpect(jsonPath("$.[*].flagLabel").value(hasItem(DEFAULT_FLAG_LABEL)));
    }
    
    @Test
    @Transactional
    public void getBankList() throws Exception {
        // Initialize the database
        bankListRepository.saveAndFlush(bankList);

        // Get the bankList
        restBankListMockMvc.perform(get("/api/bank-lists/{id}", bankList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankList.getId().intValue()))
            .andExpect(jsonPath("$.bankId").value(DEFAULT_BANK_ID))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.branchIndicator").value(DEFAULT_BRANCH_INDICATOR))
            .andExpect(jsonPath("$.flagLabel").value(DEFAULT_FLAG_LABEL));
    }
    @Test
    @Transactional
    public void getNonExistingBankList() throws Exception {
        // Get the bankList
        restBankListMockMvc.perform(get("/api/bank-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankList() throws Exception {
        // Initialize the database
        bankListRepository.saveAndFlush(bankList);

        int databaseSizeBeforeUpdate = bankListRepository.findAll().size();

        // Update the bankList
        BankList updatedBankList = bankListRepository.findById(bankList.getId()).get();
        // Disconnect from session so that the updates on updatedBankList are not directly saved in db
        em.detach(updatedBankList);
        updatedBankList
            .bankId(UPDATED_BANK_ID)
            .bankName(UPDATED_BANK_NAME)
            .branchIndicator(UPDATED_BRANCH_INDICATOR)
            .flagLabel(UPDATED_FLAG_LABEL);

        restBankListMockMvc.perform(put("/api/bank-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBankList)))
            .andExpect(status().isOk());

        // Validate the BankList in the database
        List<BankList> bankListList = bankListRepository.findAll();
        assertThat(bankListList).hasSize(databaseSizeBeforeUpdate);
        BankList testBankList = bankListList.get(bankListList.size() - 1);
        assertThat(testBankList.getBankId()).isEqualTo(UPDATED_BANK_ID);
        assertThat(testBankList.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBankList.getBranchIndicator()).isEqualTo(UPDATED_BRANCH_INDICATOR);
        assertThat(testBankList.getFlagLabel()).isEqualTo(UPDATED_FLAG_LABEL);

        // Validate the BankList in Elasticsearch
        verify(mockBankListSearchRepository, times(1)).save(testBankList);
    }

    @Test
    @Transactional
    public void updateNonExistingBankList() throws Exception {
        int databaseSizeBeforeUpdate = bankListRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankListMockMvc.perform(put("/api/bank-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankList)))
            .andExpect(status().isBadRequest());

        // Validate the BankList in the database
        List<BankList> bankListList = bankListRepository.findAll();
        assertThat(bankListList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BankList in Elasticsearch
        verify(mockBankListSearchRepository, times(0)).save(bankList);
    }

    @Test
    @Transactional
    public void deleteBankList() throws Exception {
        // Initialize the database
        bankListRepository.saveAndFlush(bankList);

        int databaseSizeBeforeDelete = bankListRepository.findAll().size();

        // Delete the bankList
        restBankListMockMvc.perform(delete("/api/bank-lists/{id}", bankList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankList> bankListList = bankListRepository.findAll();
        assertThat(bankListList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BankList in Elasticsearch
        verify(mockBankListSearchRepository, times(1)).deleteById(bankList.getId());
    }

    @Test
    @Transactional
    public void searchBankList() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        bankListRepository.saveAndFlush(bankList);
        when(mockBankListSearchRepository.search(queryStringQuery("id:" + bankList.getId())))
            .thenReturn(Collections.singletonList(bankList));

        // Search the bankList
        restBankListMockMvc.perform(get("/api/_search/bank-lists?query=id:" + bankList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankList.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankId").value(hasItem(DEFAULT_BANK_ID)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchIndicator").value(hasItem(DEFAULT_BRANCH_INDICATOR)))
            .andExpect(jsonPath("$.[*].flagLabel").value(hasItem(DEFAULT_FLAG_LABEL)));
    }
}
