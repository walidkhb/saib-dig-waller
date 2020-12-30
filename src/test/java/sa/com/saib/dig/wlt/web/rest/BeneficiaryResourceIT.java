package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.Beneficiary;
import sa.com.saib.dig.wlt.repository.BeneficiaryRepository;
import sa.com.saib.dig.wlt.repository.search.BeneficiarySearchRepository;

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
 * Integration tests for the {@link BeneficiaryResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BeneficiaryResourceIT {

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_ID = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_I_D_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_I_D_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_I_D_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_I_D_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_RELATION = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_RELATION = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_CITY = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_PHONE_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_PHONE_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_SOURCE_OF_FUND = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_SOURCE_OF_FUND = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_CURRENCY = "BBBBBBBBBB";

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.BeneficiarySearchRepositoryMockConfiguration
     */
    @Autowired
    private BeneficiarySearchRepository mockBeneficiarySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeneficiaryMockMvc;

    private Beneficiary beneficiary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beneficiary createEntity(EntityManager em) {
        Beneficiary beneficiary = new Beneficiary()
            .nickName(DEFAULT_NICK_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .beneficiaryId(DEFAULT_BENEFICIARY_ID)
            .beneficiaryType(DEFAULT_BENEFICIARY_TYPE)
            .address(DEFAULT_ADDRESS)
            .nationality(DEFAULT_NATIONALITY)
            .telephone(DEFAULT_TELEPHONE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .iDNumber(DEFAULT_I_D_NUMBER)
            .iDType(DEFAULT_I_D_TYPE)
            .beneficiaryRelation(DEFAULT_BENEFICIARY_RELATION)
            .beneficiaryCity(DEFAULT_BENEFICIARY_CITY)
            .beneficiaryPhoneCountryCode(DEFAULT_BENEFICIARY_PHONE_COUNTRY_CODE)
            .beneficiarySourceOfFund(DEFAULT_BENEFICIARY_SOURCE_OF_FUND)
            .beneficiaryZipCode(DEFAULT_BENEFICIARY_ZIP_CODE)
            .beneficiaryStatus(DEFAULT_BENEFICIARY_STATUS)
            .beneficiaryCurrency(DEFAULT_BENEFICIARY_CURRENCY);
        return beneficiary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beneficiary createUpdatedEntity(EntityManager em) {
        Beneficiary beneficiary = new Beneficiary()
            .nickName(UPDATED_NICK_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .beneficiaryId(UPDATED_BENEFICIARY_ID)
            .beneficiaryType(UPDATED_BENEFICIARY_TYPE)
            .address(UPDATED_ADDRESS)
            .nationality(UPDATED_NATIONALITY)
            .telephone(UPDATED_TELEPHONE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .iDNumber(UPDATED_I_D_NUMBER)
            .iDType(UPDATED_I_D_TYPE)
            .beneficiaryRelation(UPDATED_BENEFICIARY_RELATION)
            .beneficiaryCity(UPDATED_BENEFICIARY_CITY)
            .beneficiaryPhoneCountryCode(UPDATED_BENEFICIARY_PHONE_COUNTRY_CODE)
            .beneficiarySourceOfFund(UPDATED_BENEFICIARY_SOURCE_OF_FUND)
            .beneficiaryZipCode(UPDATED_BENEFICIARY_ZIP_CODE)
            .beneficiaryStatus(UPDATED_BENEFICIARY_STATUS)
            .beneficiaryCurrency(UPDATED_BENEFICIARY_CURRENCY);
        return beneficiary;
    }

    @BeforeEach
    public void initTest() {
        beneficiary = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficiary() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryRepository.findAll().size();
        // Create the Beneficiary
        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beneficiary)))
            .andExpect(status().isCreated());

        // Validate the Beneficiary in the database
        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeCreate + 1);
        Beneficiary testBeneficiary = beneficiaryList.get(beneficiaryList.size() - 1);
        assertThat(testBeneficiary.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testBeneficiary.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testBeneficiary.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBeneficiary.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testBeneficiary.getBeneficiaryId()).isEqualTo(DEFAULT_BENEFICIARY_ID);
        assertThat(testBeneficiary.getBeneficiaryType()).isEqualTo(DEFAULT_BENEFICIARY_TYPE);
        assertThat(testBeneficiary.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBeneficiary.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testBeneficiary.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testBeneficiary.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testBeneficiary.getiDNumber()).isEqualTo(DEFAULT_I_D_NUMBER);
        assertThat(testBeneficiary.getiDType()).isEqualTo(DEFAULT_I_D_TYPE);
        assertThat(testBeneficiary.getBeneficiaryRelation()).isEqualTo(DEFAULT_BENEFICIARY_RELATION);
        assertThat(testBeneficiary.getBeneficiaryCity()).isEqualTo(DEFAULT_BENEFICIARY_CITY);
        assertThat(testBeneficiary.getBeneficiaryPhoneCountryCode()).isEqualTo(DEFAULT_BENEFICIARY_PHONE_COUNTRY_CODE);
        assertThat(testBeneficiary.getBeneficiarySourceOfFund()).isEqualTo(DEFAULT_BENEFICIARY_SOURCE_OF_FUND);
        assertThat(testBeneficiary.getBeneficiaryZipCode()).isEqualTo(DEFAULT_BENEFICIARY_ZIP_CODE);
        assertThat(testBeneficiary.getBeneficiaryStatus()).isEqualTo(DEFAULT_BENEFICIARY_STATUS);
        assertThat(testBeneficiary.getBeneficiaryCurrency()).isEqualTo(DEFAULT_BENEFICIARY_CURRENCY);

        // Validate the Beneficiary in Elasticsearch
        verify(mockBeneficiarySearchRepository, times(1)).save(testBeneficiary);
    }

    @Test
    @Transactional
    public void createBeneficiaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryRepository.findAll().size();

        // Create the Beneficiary with an existing ID
        beneficiary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beneficiary)))
            .andExpect(status().isBadRequest());

        // Validate the Beneficiary in the database
        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeCreate);

        // Validate the Beneficiary in Elasticsearch
        verify(mockBeneficiarySearchRepository, times(0)).save(beneficiary);
    }


    @Test
    @Transactional
    public void getAllBeneficiaries() throws Exception {
        // Initialize the database
        beneficiaryRepository.saveAndFlush(beneficiary);

        // Get all the beneficiaryList
        restBeneficiaryMockMvc.perform(get("/api/beneficiaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiary.getId().intValue())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].beneficiaryId").value(hasItem(DEFAULT_BENEFICIARY_ID)))
            .andExpect(jsonPath("$.[*].beneficiaryType").value(hasItem(DEFAULT_BENEFICIARY_TYPE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].iDNumber").value(hasItem(DEFAULT_I_D_NUMBER)))
            .andExpect(jsonPath("$.[*].iDType").value(hasItem(DEFAULT_I_D_TYPE)))
            .andExpect(jsonPath("$.[*].beneficiaryRelation").value(hasItem(DEFAULT_BENEFICIARY_RELATION)))
            .andExpect(jsonPath("$.[*].beneficiaryCity").value(hasItem(DEFAULT_BENEFICIARY_CITY)))
            .andExpect(jsonPath("$.[*].beneficiaryPhoneCountryCode").value(hasItem(DEFAULT_BENEFICIARY_PHONE_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].beneficiarySourceOfFund").value(hasItem(DEFAULT_BENEFICIARY_SOURCE_OF_FUND)))
            .andExpect(jsonPath("$.[*].beneficiaryZipCode").value(hasItem(DEFAULT_BENEFICIARY_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].beneficiaryStatus").value(hasItem(DEFAULT_BENEFICIARY_STATUS)))
            .andExpect(jsonPath("$.[*].beneficiaryCurrency").value(hasItem(DEFAULT_BENEFICIARY_CURRENCY)));
    }
    
    @Test
    @Transactional
    public void getBeneficiary() throws Exception {
        // Initialize the database
        beneficiaryRepository.saveAndFlush(beneficiary);

        // Get the beneficiary
        restBeneficiaryMockMvc.perform(get("/api/beneficiaries/{id}", beneficiary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiary.getId().intValue()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.beneficiaryId").value(DEFAULT_BENEFICIARY_ID))
            .andExpect(jsonPath("$.beneficiaryType").value(DEFAULT_BENEFICIARY_TYPE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH))
            .andExpect(jsonPath("$.iDNumber").value(DEFAULT_I_D_NUMBER))
            .andExpect(jsonPath("$.iDType").value(DEFAULT_I_D_TYPE))
            .andExpect(jsonPath("$.beneficiaryRelation").value(DEFAULT_BENEFICIARY_RELATION))
            .andExpect(jsonPath("$.beneficiaryCity").value(DEFAULT_BENEFICIARY_CITY))
            .andExpect(jsonPath("$.beneficiaryPhoneCountryCode").value(DEFAULT_BENEFICIARY_PHONE_COUNTRY_CODE))
            .andExpect(jsonPath("$.beneficiarySourceOfFund").value(DEFAULT_BENEFICIARY_SOURCE_OF_FUND))
            .andExpect(jsonPath("$.beneficiaryZipCode").value(DEFAULT_BENEFICIARY_ZIP_CODE))
            .andExpect(jsonPath("$.beneficiaryStatus").value(DEFAULT_BENEFICIARY_STATUS))
            .andExpect(jsonPath("$.beneficiaryCurrency").value(DEFAULT_BENEFICIARY_CURRENCY));
    }
    @Test
    @Transactional
    public void getNonExistingBeneficiary() throws Exception {
        // Get the beneficiary
        restBeneficiaryMockMvc.perform(get("/api/beneficiaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficiary() throws Exception {
        // Initialize the database
        beneficiaryRepository.saveAndFlush(beneficiary);

        int databaseSizeBeforeUpdate = beneficiaryRepository.findAll().size();

        // Update the beneficiary
        Beneficiary updatedBeneficiary = beneficiaryRepository.findById(beneficiary.getId()).get();
        // Disconnect from session so that the updates on updatedBeneficiary are not directly saved in db
        em.detach(updatedBeneficiary);
        updatedBeneficiary
            .nickName(UPDATED_NICK_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .beneficiaryId(UPDATED_BENEFICIARY_ID)
            .beneficiaryType(UPDATED_BENEFICIARY_TYPE)
            .address(UPDATED_ADDRESS)
            .nationality(UPDATED_NATIONALITY)
            .telephone(UPDATED_TELEPHONE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .iDNumber(UPDATED_I_D_NUMBER)
            .iDType(UPDATED_I_D_TYPE)
            .beneficiaryRelation(UPDATED_BENEFICIARY_RELATION)
            .beneficiaryCity(UPDATED_BENEFICIARY_CITY)
            .beneficiaryPhoneCountryCode(UPDATED_BENEFICIARY_PHONE_COUNTRY_CODE)
            .beneficiarySourceOfFund(UPDATED_BENEFICIARY_SOURCE_OF_FUND)
            .beneficiaryZipCode(UPDATED_BENEFICIARY_ZIP_CODE)
            .beneficiaryStatus(UPDATED_BENEFICIARY_STATUS)
            .beneficiaryCurrency(UPDATED_BENEFICIARY_CURRENCY);

        restBeneficiaryMockMvc.perform(put("/api/beneficiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeneficiary)))
            .andExpect(status().isOk());

        // Validate the Beneficiary in the database
        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeUpdate);
        Beneficiary testBeneficiary = beneficiaryList.get(beneficiaryList.size() - 1);
        assertThat(testBeneficiary.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testBeneficiary.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBeneficiary.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBeneficiary.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testBeneficiary.getBeneficiaryId()).isEqualTo(UPDATED_BENEFICIARY_ID);
        assertThat(testBeneficiary.getBeneficiaryType()).isEqualTo(UPDATED_BENEFICIARY_TYPE);
        assertThat(testBeneficiary.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBeneficiary.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testBeneficiary.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testBeneficiary.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testBeneficiary.getiDNumber()).isEqualTo(UPDATED_I_D_NUMBER);
        assertThat(testBeneficiary.getiDType()).isEqualTo(UPDATED_I_D_TYPE);
        assertThat(testBeneficiary.getBeneficiaryRelation()).isEqualTo(UPDATED_BENEFICIARY_RELATION);
        assertThat(testBeneficiary.getBeneficiaryCity()).isEqualTo(UPDATED_BENEFICIARY_CITY);
        assertThat(testBeneficiary.getBeneficiaryPhoneCountryCode()).isEqualTo(UPDATED_BENEFICIARY_PHONE_COUNTRY_CODE);
        assertThat(testBeneficiary.getBeneficiarySourceOfFund()).isEqualTo(UPDATED_BENEFICIARY_SOURCE_OF_FUND);
        assertThat(testBeneficiary.getBeneficiaryZipCode()).isEqualTo(UPDATED_BENEFICIARY_ZIP_CODE);
        assertThat(testBeneficiary.getBeneficiaryStatus()).isEqualTo(UPDATED_BENEFICIARY_STATUS);
        assertThat(testBeneficiary.getBeneficiaryCurrency()).isEqualTo(UPDATED_BENEFICIARY_CURRENCY);

        // Validate the Beneficiary in Elasticsearch
        verify(mockBeneficiarySearchRepository, times(1)).save(testBeneficiary);
    }

    @Test
    @Transactional
    public void updateNonExistingBeneficiary() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficiaryMockMvc.perform(put("/api/beneficiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beneficiary)))
            .andExpect(status().isBadRequest());

        // Validate the Beneficiary in the database
        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Beneficiary in Elasticsearch
        verify(mockBeneficiarySearchRepository, times(0)).save(beneficiary);
    }

    @Test
    @Transactional
    public void deleteBeneficiary() throws Exception {
        // Initialize the database
        beneficiaryRepository.saveAndFlush(beneficiary);

        int databaseSizeBeforeDelete = beneficiaryRepository.findAll().size();

        // Delete the beneficiary
        restBeneficiaryMockMvc.perform(delete("/api/beneficiaries/{id}", beneficiary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Beneficiary in Elasticsearch
        verify(mockBeneficiarySearchRepository, times(1)).deleteById(beneficiary.getId());
    }

    @Test
    @Transactional
    public void searchBeneficiary() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        beneficiaryRepository.saveAndFlush(beneficiary);
        when(mockBeneficiarySearchRepository.search(queryStringQuery("id:" + beneficiary.getId())))
            .thenReturn(Collections.singletonList(beneficiary));

        // Search the beneficiary
        restBeneficiaryMockMvc.perform(get("/api/_search/beneficiaries?query=id:" + beneficiary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiary.getId().intValue())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].beneficiaryId").value(hasItem(DEFAULT_BENEFICIARY_ID)))
            .andExpect(jsonPath("$.[*].beneficiaryType").value(hasItem(DEFAULT_BENEFICIARY_TYPE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].iDNumber").value(hasItem(DEFAULT_I_D_NUMBER)))
            .andExpect(jsonPath("$.[*].iDType").value(hasItem(DEFAULT_I_D_TYPE)))
            .andExpect(jsonPath("$.[*].beneficiaryRelation").value(hasItem(DEFAULT_BENEFICIARY_RELATION)))
            .andExpect(jsonPath("$.[*].beneficiaryCity").value(hasItem(DEFAULT_BENEFICIARY_CITY)))
            .andExpect(jsonPath("$.[*].beneficiaryPhoneCountryCode").value(hasItem(DEFAULT_BENEFICIARY_PHONE_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].beneficiarySourceOfFund").value(hasItem(DEFAULT_BENEFICIARY_SOURCE_OF_FUND)))
            .andExpect(jsonPath("$.[*].beneficiaryZipCode").value(hasItem(DEFAULT_BENEFICIARY_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].beneficiaryStatus").value(hasItem(DEFAULT_BENEFICIARY_STATUS)))
            .andExpect(jsonPath("$.[*].beneficiaryCurrency").value(hasItem(DEFAULT_BENEFICIARY_CURRENCY)));
    }
}
