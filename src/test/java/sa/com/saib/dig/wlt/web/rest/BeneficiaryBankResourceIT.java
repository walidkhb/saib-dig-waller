package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.BeneficiaryBank;
import sa.com.saib.dig.wlt.repository.BeneficiaryBankRepository;
import sa.com.saib.dig.wlt.repository.search.BeneficiaryBankSearchRepository;

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
 * Integration tests for the {@link BeneficiaryBankResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BeneficiaryBankResourceIT {

    private static final String DEFAULT_BANK_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_BANK_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME_AND_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME_AND_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private BeneficiaryBankRepository beneficiaryBankRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.BeneficiaryBankSearchRepositoryMockConfiguration
     */
    @Autowired
    private BeneficiaryBankSearchRepository mockBeneficiaryBankSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeneficiaryBankMockMvc;

    private BeneficiaryBank beneficiaryBank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BeneficiaryBank createEntity(EntityManager em) {
        BeneficiaryBank beneficiaryBank = new BeneficiaryBank()
            .bankCode(DEFAULT_BANK_CODE)
            .bankName(DEFAULT_BANK_NAME)
            .bankCountry(DEFAULT_BANK_COUNTRY)
            .bankBranchCode(DEFAULT_BANK_BRANCH_CODE)
            .branchNameAndAddress(DEFAULT_BRANCH_NAME_AND_ADDRESS);
        return beneficiaryBank;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BeneficiaryBank createUpdatedEntity(EntityManager em) {
        BeneficiaryBank beneficiaryBank = new BeneficiaryBank()
            .bankCode(UPDATED_BANK_CODE)
            .bankName(UPDATED_BANK_NAME)
            .bankCountry(UPDATED_BANK_COUNTRY)
            .bankBranchCode(UPDATED_BANK_BRANCH_CODE)
            .branchNameAndAddress(UPDATED_BRANCH_NAME_AND_ADDRESS);
        return beneficiaryBank;
    }

    @BeforeEach
    public void initTest() {
        beneficiaryBank = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficiaryBank() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryBankRepository.findAll().size();
        // Create the BeneficiaryBank
        restBeneficiaryBankMockMvc.perform(post("/api/beneficiary-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryBank)))
            .andExpect(status().isCreated());

        // Validate the BeneficiaryBank in the database
        List<BeneficiaryBank> beneficiaryBankList = beneficiaryBankRepository.findAll();
        assertThat(beneficiaryBankList).hasSize(databaseSizeBeforeCreate + 1);
        BeneficiaryBank testBeneficiaryBank = beneficiaryBankList.get(beneficiaryBankList.size() - 1);
        assertThat(testBeneficiaryBank.getBankCode()).isEqualTo(DEFAULT_BANK_CODE);
        assertThat(testBeneficiaryBank.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBeneficiaryBank.getBankCountry()).isEqualTo(DEFAULT_BANK_COUNTRY);
        assertThat(testBeneficiaryBank.getBankBranchCode()).isEqualTo(DEFAULT_BANK_BRANCH_CODE);
        assertThat(testBeneficiaryBank.getBranchNameAndAddress()).isEqualTo(DEFAULT_BRANCH_NAME_AND_ADDRESS);

        // Validate the BeneficiaryBank in Elasticsearch
        verify(mockBeneficiaryBankSearchRepository, times(1)).save(testBeneficiaryBank);
    }

    @Test
    @Transactional
    public void createBeneficiaryBankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryBankRepository.findAll().size();

        // Create the BeneficiaryBank with an existing ID
        beneficiaryBank.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficiaryBankMockMvc.perform(post("/api/beneficiary-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryBank)))
            .andExpect(status().isBadRequest());

        // Validate the BeneficiaryBank in the database
        List<BeneficiaryBank> beneficiaryBankList = beneficiaryBankRepository.findAll();
        assertThat(beneficiaryBankList).hasSize(databaseSizeBeforeCreate);

        // Validate the BeneficiaryBank in Elasticsearch
        verify(mockBeneficiaryBankSearchRepository, times(0)).save(beneficiaryBank);
    }


    @Test
    @Transactional
    public void getAllBeneficiaryBanks() throws Exception {
        // Initialize the database
        beneficiaryBankRepository.saveAndFlush(beneficiaryBank);

        // Get all the beneficiaryBankList
        restBeneficiaryBankMockMvc.perform(get("/api/beneficiary-banks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiaryBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankCode").value(hasItem(DEFAULT_BANK_CODE)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].bankCountry").value(hasItem(DEFAULT_BANK_COUNTRY)))
            .andExpect(jsonPath("$.[*].bankBranchCode").value(hasItem(DEFAULT_BANK_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].branchNameAndAddress").value(hasItem(DEFAULT_BRANCH_NAME_AND_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getBeneficiaryBank() throws Exception {
        // Initialize the database
        beneficiaryBankRepository.saveAndFlush(beneficiaryBank);

        // Get the beneficiaryBank
        restBeneficiaryBankMockMvc.perform(get("/api/beneficiary-banks/{id}", beneficiaryBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiaryBank.getId().intValue()))
            .andExpect(jsonPath("$.bankCode").value(DEFAULT_BANK_CODE))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.bankCountry").value(DEFAULT_BANK_COUNTRY))
            .andExpect(jsonPath("$.bankBranchCode").value(DEFAULT_BANK_BRANCH_CODE))
            .andExpect(jsonPath("$.branchNameAndAddress").value(DEFAULT_BRANCH_NAME_AND_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingBeneficiaryBank() throws Exception {
        // Get the beneficiaryBank
        restBeneficiaryBankMockMvc.perform(get("/api/beneficiary-banks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficiaryBank() throws Exception {
        // Initialize the database
        beneficiaryBankRepository.saveAndFlush(beneficiaryBank);

        int databaseSizeBeforeUpdate = beneficiaryBankRepository.findAll().size();

        // Update the beneficiaryBank
        BeneficiaryBank updatedBeneficiaryBank = beneficiaryBankRepository.findById(beneficiaryBank.getId()).get();
        // Disconnect from session so that the updates on updatedBeneficiaryBank are not directly saved in db
        em.detach(updatedBeneficiaryBank);
        updatedBeneficiaryBank
            .bankCode(UPDATED_BANK_CODE)
            .bankName(UPDATED_BANK_NAME)
            .bankCountry(UPDATED_BANK_COUNTRY)
            .bankBranchCode(UPDATED_BANK_BRANCH_CODE)
            .branchNameAndAddress(UPDATED_BRANCH_NAME_AND_ADDRESS);

        restBeneficiaryBankMockMvc.perform(put("/api/beneficiary-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeneficiaryBank)))
            .andExpect(status().isOk());

        // Validate the BeneficiaryBank in the database
        List<BeneficiaryBank> beneficiaryBankList = beneficiaryBankRepository.findAll();
        assertThat(beneficiaryBankList).hasSize(databaseSizeBeforeUpdate);
        BeneficiaryBank testBeneficiaryBank = beneficiaryBankList.get(beneficiaryBankList.size() - 1);
        assertThat(testBeneficiaryBank.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
        assertThat(testBeneficiaryBank.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBeneficiaryBank.getBankCountry()).isEqualTo(UPDATED_BANK_COUNTRY);
        assertThat(testBeneficiaryBank.getBankBranchCode()).isEqualTo(UPDATED_BANK_BRANCH_CODE);
        assertThat(testBeneficiaryBank.getBranchNameAndAddress()).isEqualTo(UPDATED_BRANCH_NAME_AND_ADDRESS);

        // Validate the BeneficiaryBank in Elasticsearch
        verify(mockBeneficiaryBankSearchRepository, times(1)).save(testBeneficiaryBank);
    }

    @Test
    @Transactional
    public void updateNonExistingBeneficiaryBank() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaryBankRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficiaryBankMockMvc.perform(put("/api/beneficiary-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryBank)))
            .andExpect(status().isBadRequest());

        // Validate the BeneficiaryBank in the database
        List<BeneficiaryBank> beneficiaryBankList = beneficiaryBankRepository.findAll();
        assertThat(beneficiaryBankList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BeneficiaryBank in Elasticsearch
        verify(mockBeneficiaryBankSearchRepository, times(0)).save(beneficiaryBank);
    }

    @Test
    @Transactional
    public void deleteBeneficiaryBank() throws Exception {
        // Initialize the database
        beneficiaryBankRepository.saveAndFlush(beneficiaryBank);

        int databaseSizeBeforeDelete = beneficiaryBankRepository.findAll().size();

        // Delete the beneficiaryBank
        restBeneficiaryBankMockMvc.perform(delete("/api/beneficiary-banks/{id}", beneficiaryBank.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BeneficiaryBank> beneficiaryBankList = beneficiaryBankRepository.findAll();
        assertThat(beneficiaryBankList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BeneficiaryBank in Elasticsearch
        verify(mockBeneficiaryBankSearchRepository, times(1)).deleteById(beneficiaryBank.getId());
    }

    @Test
    @Transactional
    public void searchBeneficiaryBank() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        beneficiaryBankRepository.saveAndFlush(beneficiaryBank);
        when(mockBeneficiaryBankSearchRepository.search(queryStringQuery("id:" + beneficiaryBank.getId())))
            .thenReturn(Collections.singletonList(beneficiaryBank));

        // Search the beneficiaryBank
        restBeneficiaryBankMockMvc.perform(get("/api/_search/beneficiary-banks?query=id:" + beneficiaryBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiaryBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankCode").value(hasItem(DEFAULT_BANK_CODE)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].bankCountry").value(hasItem(DEFAULT_BANK_COUNTRY)))
            .andExpect(jsonPath("$.[*].bankBranchCode").value(hasItem(DEFAULT_BANK_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].branchNameAndAddress").value(hasItem(DEFAULT_BRANCH_NAME_AND_ADDRESS)));
    }
}
