package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.Customer;
import sa.com.saib.dig.wlt.repository.CustomerRepository;
import sa.com.saib.dig.wlt.repository.search.CustomerSearchRepository;

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
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerResourceIT {

    private static final String DEFAULT_FIRST_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_GRAND_FATHER_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_GRAND_FATHER_NAME_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_GRAND_FATHER_NAME_ENGLISH = "AAAAAAAAAA";
    private static final String UPDATED_GRAND_FATHER_NAME_ENGLISH = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_I_D_ISSUE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_I_D_ISSUE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_I_D_EXPIRY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_I_D_EXPIRY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_STATUS = "BBBBBBBBBB";

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.CustomerSearchRepositoryMockConfiguration
     */
    @Autowired
    private CustomerSearchRepository mockCustomerSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .firstNameArabic(DEFAULT_FIRST_NAME_ARABIC)
            .fatherNameArabic(DEFAULT_FATHER_NAME_ARABIC)
            .grandFatherNameArabic(DEFAULT_GRAND_FATHER_NAME_ARABIC)
            .grandFatherNameEnglish(DEFAULT_GRAND_FATHER_NAME_ENGLISH)
            .placeOfBirth(DEFAULT_PLACE_OF_BIRTH)
            .iDIssueDate(DEFAULT_I_D_ISSUE_DATE)
            .iDExpiryDate(DEFAULT_I_D_EXPIRY_DATE)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .customerId(DEFAULT_CUSTOMER_ID)
            .profileStatus(DEFAULT_PROFILE_STATUS);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .firstNameArabic(UPDATED_FIRST_NAME_ARABIC)
            .fatherNameArabic(UPDATED_FATHER_NAME_ARABIC)
            .grandFatherNameArabic(UPDATED_GRAND_FATHER_NAME_ARABIC)
            .grandFatherNameEnglish(UPDATED_GRAND_FATHER_NAME_ENGLISH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .iDIssueDate(UPDATED_I_D_ISSUE_DATE)
            .iDExpiryDate(UPDATED_I_D_EXPIRY_DATE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .customerId(UPDATED_CUSTOMER_ID)
            .profileStatus(UPDATED_PROFILE_STATUS);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();
        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getFirstNameArabic()).isEqualTo(DEFAULT_FIRST_NAME_ARABIC);
        assertThat(testCustomer.getFatherNameArabic()).isEqualTo(DEFAULT_FATHER_NAME_ARABIC);
        assertThat(testCustomer.getGrandFatherNameArabic()).isEqualTo(DEFAULT_GRAND_FATHER_NAME_ARABIC);
        assertThat(testCustomer.getGrandFatherNameEnglish()).isEqualTo(DEFAULT_GRAND_FATHER_NAME_ENGLISH);
        assertThat(testCustomer.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testCustomer.getiDIssueDate()).isEqualTo(DEFAULT_I_D_ISSUE_DATE);
        assertThat(testCustomer.getiDExpiryDate()).isEqualTo(DEFAULT_I_D_EXPIRY_DATE);
        assertThat(testCustomer.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testCustomer.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testCustomer.getProfileStatus()).isEqualTo(DEFAULT_PROFILE_STATUS);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(1)).save(testCustomer);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(0)).save(customer);
    }


    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstNameArabic").value(hasItem(DEFAULT_FIRST_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].fatherNameArabic").value(hasItem(DEFAULT_FATHER_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].grandFatherNameArabic").value(hasItem(DEFAULT_GRAND_FATHER_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].grandFatherNameEnglish").value(hasItem(DEFAULT_GRAND_FATHER_NAME_ENGLISH)))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].iDIssueDate").value(hasItem(DEFAULT_I_D_ISSUE_DATE)))
            .andExpect(jsonPath("$.[*].iDExpiryDate").value(hasItem(DEFAULT_I_D_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].profileStatus").value(hasItem(DEFAULT_PROFILE_STATUS)));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.firstNameArabic").value(DEFAULT_FIRST_NAME_ARABIC))
            .andExpect(jsonPath("$.fatherNameArabic").value(DEFAULT_FATHER_NAME_ARABIC))
            .andExpect(jsonPath("$.grandFatherNameArabic").value(DEFAULT_GRAND_FATHER_NAME_ARABIC))
            .andExpect(jsonPath("$.grandFatherNameEnglish").value(DEFAULT_GRAND_FATHER_NAME_ENGLISH))
            .andExpect(jsonPath("$.placeOfBirth").value(DEFAULT_PLACE_OF_BIRTH))
            .andExpect(jsonPath("$.iDIssueDate").value(DEFAULT_I_D_ISSUE_DATE))
            .andExpect(jsonPath("$.iDExpiryDate").value(DEFAULT_I_D_EXPIRY_DATE))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.profileStatus").value(DEFAULT_PROFILE_STATUS));
    }
    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .firstNameArabic(UPDATED_FIRST_NAME_ARABIC)
            .fatherNameArabic(UPDATED_FATHER_NAME_ARABIC)
            .grandFatherNameArabic(UPDATED_GRAND_FATHER_NAME_ARABIC)
            .grandFatherNameEnglish(UPDATED_GRAND_FATHER_NAME_ENGLISH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .iDIssueDate(UPDATED_I_D_ISSUE_DATE)
            .iDExpiryDate(UPDATED_I_D_EXPIRY_DATE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .customerId(UPDATED_CUSTOMER_ID)
            .profileStatus(UPDATED_PROFILE_STATUS);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getFirstNameArabic()).isEqualTo(UPDATED_FIRST_NAME_ARABIC);
        assertThat(testCustomer.getFatherNameArabic()).isEqualTo(UPDATED_FATHER_NAME_ARABIC);
        assertThat(testCustomer.getGrandFatherNameArabic()).isEqualTo(UPDATED_GRAND_FATHER_NAME_ARABIC);
        assertThat(testCustomer.getGrandFatherNameEnglish()).isEqualTo(UPDATED_GRAND_FATHER_NAME_ENGLISH);
        assertThat(testCustomer.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testCustomer.getiDIssueDate()).isEqualTo(UPDATED_I_D_ISSUE_DATE);
        assertThat(testCustomer.getiDExpiryDate()).isEqualTo(UPDATED_I_D_EXPIRY_DATE);
        assertThat(testCustomer.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testCustomer.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustomer.getProfileStatus()).isEqualTo(UPDATED_PROFILE_STATUS);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(1)).save(testCustomer);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(0)).save(customer);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(1)).deleteById(customer.getId());
    }

    @Test
    @Transactional
    public void searchCustomer() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        when(mockCustomerSearchRepository.search(queryStringQuery("id:" + customer.getId())))
            .thenReturn(Collections.singletonList(customer));

        // Search the customer
        restCustomerMockMvc.perform(get("/api/_search/customers?query=id:" + customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstNameArabic").value(hasItem(DEFAULT_FIRST_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].fatherNameArabic").value(hasItem(DEFAULT_FATHER_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].grandFatherNameArabic").value(hasItem(DEFAULT_GRAND_FATHER_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].grandFatherNameEnglish").value(hasItem(DEFAULT_GRAND_FATHER_NAME_ENGLISH)))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].iDIssueDate").value(hasItem(DEFAULT_I_D_ISSUE_DATE)))
            .andExpect(jsonPath("$.[*].iDExpiryDate").value(hasItem(DEFAULT_I_D_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].profileStatus").value(hasItem(DEFAULT_PROFILE_STATUS)));
    }
}
