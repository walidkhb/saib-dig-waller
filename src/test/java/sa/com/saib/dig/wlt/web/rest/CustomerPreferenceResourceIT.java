package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.CustomerPreference;
import sa.com.saib.dig.wlt.repository.CustomerPreferenceRepository;
import sa.com.saib.dig.wlt.repository.search.CustomerPreferenceSearchRepository;

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
 * Integration tests for the {@link CustomerPreferenceResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerPreferenceResourceIT {

    private static final String DEFAULT_EMAIL = ">v\\^@>7_]\\.W";
    private static final String UPDATED_EMAIL = "F&,@=r}%:(.0u";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    @Autowired
    private CustomerPreferenceRepository customerPreferenceRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.CustomerPreferenceSearchRepositoryMockConfiguration
     */
    @Autowired
    private CustomerPreferenceSearchRepository mockCustomerPreferenceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPreferenceMockMvc;

    private CustomerPreference customerPreference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPreference createEntity(EntityManager em) {
        CustomerPreference customerPreference = new CustomerPreference()
            .email(DEFAULT_EMAIL)
            .language(DEFAULT_LANGUAGE);
        return customerPreference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPreference createUpdatedEntity(EntityManager em) {
        CustomerPreference customerPreference = new CustomerPreference()
            .email(UPDATED_EMAIL)
            .language(UPDATED_LANGUAGE);
        return customerPreference;
    }

    @BeforeEach
    public void initTest() {
        customerPreference = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPreference() throws Exception {
        int databaseSizeBeforeCreate = customerPreferenceRepository.findAll().size();
        // Create the CustomerPreference
        restCustomerPreferenceMockMvc.perform(post("/api/customer-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPreference)))
            .andExpect(status().isCreated());

        // Validate the CustomerPreference in the database
        List<CustomerPreference> customerPreferenceList = customerPreferenceRepository.findAll();
        assertThat(customerPreferenceList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPreference testCustomerPreference = customerPreferenceList.get(customerPreferenceList.size() - 1);
        assertThat(testCustomerPreference.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerPreference.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the CustomerPreference in Elasticsearch
        verify(mockCustomerPreferenceSearchRepository, times(1)).save(testCustomerPreference);
    }

    @Test
    @Transactional
    public void createCustomerPreferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerPreferenceRepository.findAll().size();

        // Create the CustomerPreference with an existing ID
        customerPreference.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPreferenceMockMvc.perform(post("/api/customer-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPreference)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPreference in the database
        List<CustomerPreference> customerPreferenceList = customerPreferenceRepository.findAll();
        assertThat(customerPreferenceList).hasSize(databaseSizeBeforeCreate);

        // Validate the CustomerPreference in Elasticsearch
        verify(mockCustomerPreferenceSearchRepository, times(0)).save(customerPreference);
    }


    @Test
    @Transactional
    public void getAllCustomerPreferences() throws Exception {
        // Initialize the database
        customerPreferenceRepository.saveAndFlush(customerPreference);

        // Get all the customerPreferenceList
        restCustomerPreferenceMockMvc.perform(get("/api/customer-preferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPreference.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }
    
    @Test
    @Transactional
    public void getCustomerPreference() throws Exception {
        // Initialize the database
        customerPreferenceRepository.saveAndFlush(customerPreference);

        // Get the customerPreference
        restCustomerPreferenceMockMvc.perform(get("/api/customer-preferences/{id}", customerPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPreference.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerPreference() throws Exception {
        // Get the customerPreference
        restCustomerPreferenceMockMvc.perform(get("/api/customer-preferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPreference() throws Exception {
        // Initialize the database
        customerPreferenceRepository.saveAndFlush(customerPreference);

        int databaseSizeBeforeUpdate = customerPreferenceRepository.findAll().size();

        // Update the customerPreference
        CustomerPreference updatedCustomerPreference = customerPreferenceRepository.findById(customerPreference.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPreference are not directly saved in db
        em.detach(updatedCustomerPreference);
        updatedCustomerPreference
            .email(UPDATED_EMAIL)
            .language(UPDATED_LANGUAGE);

        restCustomerPreferenceMockMvc.perform(put("/api/customer-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerPreference)))
            .andExpect(status().isOk());

        // Validate the CustomerPreference in the database
        List<CustomerPreference> customerPreferenceList = customerPreferenceRepository.findAll();
        assertThat(customerPreferenceList).hasSize(databaseSizeBeforeUpdate);
        CustomerPreference testCustomerPreference = customerPreferenceList.get(customerPreferenceList.size() - 1);
        assertThat(testCustomerPreference.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerPreference.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the CustomerPreference in Elasticsearch
        verify(mockCustomerPreferenceSearchRepository, times(1)).save(testCustomerPreference);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerPreference() throws Exception {
        int databaseSizeBeforeUpdate = customerPreferenceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPreferenceMockMvc.perform(put("/api/customer-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPreference)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPreference in the database
        List<CustomerPreference> customerPreferenceList = customerPreferenceRepository.findAll();
        assertThat(customerPreferenceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CustomerPreference in Elasticsearch
        verify(mockCustomerPreferenceSearchRepository, times(0)).save(customerPreference);
    }

    @Test
    @Transactional
    public void deleteCustomerPreference() throws Exception {
        // Initialize the database
        customerPreferenceRepository.saveAndFlush(customerPreference);

        int databaseSizeBeforeDelete = customerPreferenceRepository.findAll().size();

        // Delete the customerPreference
        restCustomerPreferenceMockMvc.perform(delete("/api/customer-preferences/{id}", customerPreference.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPreference> customerPreferenceList = customerPreferenceRepository.findAll();
        assertThat(customerPreferenceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CustomerPreference in Elasticsearch
        verify(mockCustomerPreferenceSearchRepository, times(1)).deleteById(customerPreference.getId());
    }

    @Test
    @Transactional
    public void searchCustomerPreference() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        customerPreferenceRepository.saveAndFlush(customerPreference);
        when(mockCustomerPreferenceSearchRepository.search(queryStringQuery("id:" + customerPreference.getId())))
            .thenReturn(Collections.singletonList(customerPreference));

        // Search the customerPreference
        restCustomerPreferenceMockMvc.perform(get("/api/_search/customer-preferences?query=id:" + customerPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPreference.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }
}
