package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.CustomerDetails;
import sa.com.saib.dig.wlt.repository.CustomerDetailsRepository;
import sa.com.saib.dig.wlt.repository.search.CustomerDetailsSearchRepository;

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
 * Integration tests for the {@link CustomerDetailsResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerDetailsResourceIT {

    private static final String DEFAULT_NATIONAL_IDENTITY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_IDENTITY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ID_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE_NUMBER = "+1-615524";
    private static final String UPDATED_MOBILE_PHONE_NUMBER = "+75-4364";

    private static final String DEFAULT_AGENT_VERIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AGENT_VERIFICATION_NUMBER = "BBBBBBBBBB";

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.CustomerDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private CustomerDetailsSearchRepository mockCustomerDetailsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerDetailsMockMvc;

    private CustomerDetails customerDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerDetails createEntity(EntityManager em) {
        CustomerDetails customerDetails = new CustomerDetails()
            .nationalIdentityNumber(DEFAULT_NATIONAL_IDENTITY_NUMBER)
            .idType(DEFAULT_ID_TYPE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .mobilePhoneNumber(DEFAULT_MOBILE_PHONE_NUMBER)
            .agentVerificationNumber(DEFAULT_AGENT_VERIFICATION_NUMBER);
        return customerDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerDetails createUpdatedEntity(EntityManager em) {
        CustomerDetails customerDetails = new CustomerDetails()
            .nationalIdentityNumber(UPDATED_NATIONAL_IDENTITY_NUMBER)
            .idType(UPDATED_ID_TYPE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .agentVerificationNumber(UPDATED_AGENT_VERIFICATION_NUMBER);
        return customerDetails;
    }

    @BeforeEach
    public void initTest() {
        customerDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerDetails() throws Exception {
        int databaseSizeBeforeCreate = customerDetailsRepository.findAll().size();
        // Create the CustomerDetails
        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isCreated());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerDetails testCustomerDetails = customerDetailsList.get(customerDetailsList.size() - 1);
        assertThat(testCustomerDetails.getNationalIdentityNumber()).isEqualTo(DEFAULT_NATIONAL_IDENTITY_NUMBER);
        assertThat(testCustomerDetails.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testCustomerDetails.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testCustomerDetails.getMobilePhoneNumber()).isEqualTo(DEFAULT_MOBILE_PHONE_NUMBER);
        assertThat(testCustomerDetails.getAgentVerificationNumber()).isEqualTo(DEFAULT_AGENT_VERIFICATION_NUMBER);

        // Validate the CustomerDetails in Elasticsearch
        verify(mockCustomerDetailsSearchRepository, times(1)).save(testCustomerDetails);
    }

    @Test
    @Transactional
    public void createCustomerDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerDetailsRepository.findAll().size();

        // Create the CustomerDetails with an existing ID
        customerDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the CustomerDetails in Elasticsearch
        verify(mockCustomerDetailsSearchRepository, times(0)).save(customerDetails);
    }


    @Test
    @Transactional
    public void getAllCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList
        restCustomerDetailsMockMvc.perform(get("/api/customer-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].nationalIdentityNumber").value(hasItem(DEFAULT_NATIONAL_IDENTITY_NUMBER)))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].mobilePhoneNumber").value(hasItem(DEFAULT_MOBILE_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].agentVerificationNumber").value(hasItem(DEFAULT_AGENT_VERIFICATION_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get the customerDetails
        restCustomerDetailsMockMvc.perform(get("/api/customer-details/{id}", customerDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerDetails.getId().intValue()))
            .andExpect(jsonPath("$.nationalIdentityNumber").value(DEFAULT_NATIONAL_IDENTITY_NUMBER))
            .andExpect(jsonPath("$.idType").value(DEFAULT_ID_TYPE))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH))
            .andExpect(jsonPath("$.mobilePhoneNumber").value(DEFAULT_MOBILE_PHONE_NUMBER))
            .andExpect(jsonPath("$.agentVerificationNumber").value(DEFAULT_AGENT_VERIFICATION_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerDetails() throws Exception {
        // Get the customerDetails
        restCustomerDetailsMockMvc.perform(get("/api/customer-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();

        // Update the customerDetails
        CustomerDetails updatedCustomerDetails = customerDetailsRepository.findById(customerDetails.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerDetails are not directly saved in db
        em.detach(updatedCustomerDetails);
        updatedCustomerDetails
            .nationalIdentityNumber(UPDATED_NATIONAL_IDENTITY_NUMBER)
            .idType(UPDATED_ID_TYPE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .agentVerificationNumber(UPDATED_AGENT_VERIFICATION_NUMBER);

        restCustomerDetailsMockMvc.perform(put("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerDetails)))
            .andExpect(status().isOk());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
        CustomerDetails testCustomerDetails = customerDetailsList.get(customerDetailsList.size() - 1);
        assertThat(testCustomerDetails.getNationalIdentityNumber()).isEqualTo(UPDATED_NATIONAL_IDENTITY_NUMBER);
        assertThat(testCustomerDetails.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testCustomerDetails.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCustomerDetails.getMobilePhoneNumber()).isEqualTo(UPDATED_MOBILE_PHONE_NUMBER);
        assertThat(testCustomerDetails.getAgentVerificationNumber()).isEqualTo(UPDATED_AGENT_VERIFICATION_NUMBER);

        // Validate the CustomerDetails in Elasticsearch
        verify(mockCustomerDetailsSearchRepository, times(1)).save(testCustomerDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerDetails() throws Exception {
        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerDetailsMockMvc.perform(put("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CustomerDetails in Elasticsearch
        verify(mockCustomerDetailsSearchRepository, times(0)).save(customerDetails);
    }

    @Test
    @Transactional
    public void deleteCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        int databaseSizeBeforeDelete = customerDetailsRepository.findAll().size();

        // Delete the customerDetails
        restCustomerDetailsMockMvc.perform(delete("/api/customer-details/{id}", customerDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CustomerDetails in Elasticsearch
        verify(mockCustomerDetailsSearchRepository, times(1)).deleteById(customerDetails.getId());
    }

    @Test
    @Transactional
    public void searchCustomerDetails() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);
        when(mockCustomerDetailsSearchRepository.search(queryStringQuery("id:" + customerDetails.getId())))
            .thenReturn(Collections.singletonList(customerDetails));

        // Search the customerDetails
        restCustomerDetailsMockMvc.perform(get("/api/_search/customer-details?query=id:" + customerDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].nationalIdentityNumber").value(hasItem(DEFAULT_NATIONAL_IDENTITY_NUMBER)))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].mobilePhoneNumber").value(hasItem(DEFAULT_MOBILE_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].agentVerificationNumber").value(hasItem(DEFAULT_AGENT_VERIFICATION_NUMBER)));
    }
}
