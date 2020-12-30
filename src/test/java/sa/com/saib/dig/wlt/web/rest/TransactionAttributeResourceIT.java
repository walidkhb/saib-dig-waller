package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.TransactionAttribute;
import sa.com.saib.dig.wlt.repository.TransactionAttributeRepository;
import sa.com.saib.dig.wlt.repository.search.TransactionAttributeSearchRepository;

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
 * Integration tests for the {@link TransactionAttributeResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionAttributeResourceIT {

    private static final String DEFAULT_NARATIVE_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_NARATIVE_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_NARATIVE_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_NARATIVE_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_NARATIVE_LINE_3 = "AAAAAAAAAA";
    private static final String UPDATED_NARATIVE_LINE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_NARATIVE_LINE_4 = "AAAAAAAAAA";
    private static final String UPDATED_NARATIVE_LINE_4 = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_REF_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_REF_NUMBER = "BBBBBBBBBB";

    @Autowired
    private TransactionAttributeRepository transactionAttributeRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.TransactionAttributeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransactionAttributeSearchRepository mockTransactionAttributeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionAttributeMockMvc;

    private TransactionAttribute transactionAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionAttribute createEntity(EntityManager em) {
        TransactionAttribute transactionAttribute = new TransactionAttribute()
            .narativeLine1(DEFAULT_NARATIVE_LINE_1)
            .narativeLine2(DEFAULT_NARATIVE_LINE_2)
            .narativeLine3(DEFAULT_NARATIVE_LINE_3)
            .narativeLine4(DEFAULT_NARATIVE_LINE_4)
            .clientRefNumber(DEFAULT_CLIENT_REF_NUMBER);
        return transactionAttribute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionAttribute createUpdatedEntity(EntityManager em) {
        TransactionAttribute transactionAttribute = new TransactionAttribute()
            .narativeLine1(UPDATED_NARATIVE_LINE_1)
            .narativeLine2(UPDATED_NARATIVE_LINE_2)
            .narativeLine3(UPDATED_NARATIVE_LINE_3)
            .narativeLine4(UPDATED_NARATIVE_LINE_4)
            .clientRefNumber(UPDATED_CLIENT_REF_NUMBER);
        return transactionAttribute;
    }

    @BeforeEach
    public void initTest() {
        transactionAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionAttribute() throws Exception {
        int databaseSizeBeforeCreate = transactionAttributeRepository.findAll().size();
        // Create the TransactionAttribute
        restTransactionAttributeMockMvc.perform(post("/api/transaction-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionAttribute)))
            .andExpect(status().isCreated());

        // Validate the TransactionAttribute in the database
        List<TransactionAttribute> transactionAttributeList = transactionAttributeRepository.findAll();
        assertThat(transactionAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionAttribute testTransactionAttribute = transactionAttributeList.get(transactionAttributeList.size() - 1);
        assertThat(testTransactionAttribute.getNarativeLine1()).isEqualTo(DEFAULT_NARATIVE_LINE_1);
        assertThat(testTransactionAttribute.getNarativeLine2()).isEqualTo(DEFAULT_NARATIVE_LINE_2);
        assertThat(testTransactionAttribute.getNarativeLine3()).isEqualTo(DEFAULT_NARATIVE_LINE_3);
        assertThat(testTransactionAttribute.getNarativeLine4()).isEqualTo(DEFAULT_NARATIVE_LINE_4);
        assertThat(testTransactionAttribute.getClientRefNumber()).isEqualTo(DEFAULT_CLIENT_REF_NUMBER);

        // Validate the TransactionAttribute in Elasticsearch
        verify(mockTransactionAttributeSearchRepository, times(1)).save(testTransactionAttribute);
    }

    @Test
    @Transactional
    public void createTransactionAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionAttributeRepository.findAll().size();

        // Create the TransactionAttribute with an existing ID
        transactionAttribute.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionAttributeMockMvc.perform(post("/api/transaction-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionAttribute in the database
        List<TransactionAttribute> transactionAttributeList = transactionAttributeRepository.findAll();
        assertThat(transactionAttributeList).hasSize(databaseSizeBeforeCreate);

        // Validate the TransactionAttribute in Elasticsearch
        verify(mockTransactionAttributeSearchRepository, times(0)).save(transactionAttribute);
    }


    @Test
    @Transactional
    public void getAllTransactionAttributes() throws Exception {
        // Initialize the database
        transactionAttributeRepository.saveAndFlush(transactionAttribute);

        // Get all the transactionAttributeList
        restTransactionAttributeMockMvc.perform(get("/api/transaction-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].narativeLine1").value(hasItem(DEFAULT_NARATIVE_LINE_1)))
            .andExpect(jsonPath("$.[*].narativeLine2").value(hasItem(DEFAULT_NARATIVE_LINE_2)))
            .andExpect(jsonPath("$.[*].narativeLine3").value(hasItem(DEFAULT_NARATIVE_LINE_3)))
            .andExpect(jsonPath("$.[*].narativeLine4").value(hasItem(DEFAULT_NARATIVE_LINE_4)))
            .andExpect(jsonPath("$.[*].clientRefNumber").value(hasItem(DEFAULT_CLIENT_REF_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getTransactionAttribute() throws Exception {
        // Initialize the database
        transactionAttributeRepository.saveAndFlush(transactionAttribute);

        // Get the transactionAttribute
        restTransactionAttributeMockMvc.perform(get("/api/transaction-attributes/{id}", transactionAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionAttribute.getId().intValue()))
            .andExpect(jsonPath("$.narativeLine1").value(DEFAULT_NARATIVE_LINE_1))
            .andExpect(jsonPath("$.narativeLine2").value(DEFAULT_NARATIVE_LINE_2))
            .andExpect(jsonPath("$.narativeLine3").value(DEFAULT_NARATIVE_LINE_3))
            .andExpect(jsonPath("$.narativeLine4").value(DEFAULT_NARATIVE_LINE_4))
            .andExpect(jsonPath("$.clientRefNumber").value(DEFAULT_CLIENT_REF_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingTransactionAttribute() throws Exception {
        // Get the transactionAttribute
        restTransactionAttributeMockMvc.perform(get("/api/transaction-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionAttribute() throws Exception {
        // Initialize the database
        transactionAttributeRepository.saveAndFlush(transactionAttribute);

        int databaseSizeBeforeUpdate = transactionAttributeRepository.findAll().size();

        // Update the transactionAttribute
        TransactionAttribute updatedTransactionAttribute = transactionAttributeRepository.findById(transactionAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionAttribute are not directly saved in db
        em.detach(updatedTransactionAttribute);
        updatedTransactionAttribute
            .narativeLine1(UPDATED_NARATIVE_LINE_1)
            .narativeLine2(UPDATED_NARATIVE_LINE_2)
            .narativeLine3(UPDATED_NARATIVE_LINE_3)
            .narativeLine4(UPDATED_NARATIVE_LINE_4)
            .clientRefNumber(UPDATED_CLIENT_REF_NUMBER);

        restTransactionAttributeMockMvc.perform(put("/api/transaction-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionAttribute)))
            .andExpect(status().isOk());

        // Validate the TransactionAttribute in the database
        List<TransactionAttribute> transactionAttributeList = transactionAttributeRepository.findAll();
        assertThat(transactionAttributeList).hasSize(databaseSizeBeforeUpdate);
        TransactionAttribute testTransactionAttribute = transactionAttributeList.get(transactionAttributeList.size() - 1);
        assertThat(testTransactionAttribute.getNarativeLine1()).isEqualTo(UPDATED_NARATIVE_LINE_1);
        assertThat(testTransactionAttribute.getNarativeLine2()).isEqualTo(UPDATED_NARATIVE_LINE_2);
        assertThat(testTransactionAttribute.getNarativeLine3()).isEqualTo(UPDATED_NARATIVE_LINE_3);
        assertThat(testTransactionAttribute.getNarativeLine4()).isEqualTo(UPDATED_NARATIVE_LINE_4);
        assertThat(testTransactionAttribute.getClientRefNumber()).isEqualTo(UPDATED_CLIENT_REF_NUMBER);

        // Validate the TransactionAttribute in Elasticsearch
        verify(mockTransactionAttributeSearchRepository, times(1)).save(testTransactionAttribute);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionAttribute() throws Exception {
        int databaseSizeBeforeUpdate = transactionAttributeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionAttributeMockMvc.perform(put("/api/transaction-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionAttribute in the database
        List<TransactionAttribute> transactionAttributeList = transactionAttributeRepository.findAll();
        assertThat(transactionAttributeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TransactionAttribute in Elasticsearch
        verify(mockTransactionAttributeSearchRepository, times(0)).save(transactionAttribute);
    }

    @Test
    @Transactional
    public void deleteTransactionAttribute() throws Exception {
        // Initialize the database
        transactionAttributeRepository.saveAndFlush(transactionAttribute);

        int databaseSizeBeforeDelete = transactionAttributeRepository.findAll().size();

        // Delete the transactionAttribute
        restTransactionAttributeMockMvc.perform(delete("/api/transaction-attributes/{id}", transactionAttribute.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionAttribute> transactionAttributeList = transactionAttributeRepository.findAll();
        assertThat(transactionAttributeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TransactionAttribute in Elasticsearch
        verify(mockTransactionAttributeSearchRepository, times(1)).deleteById(transactionAttribute.getId());
    }

    @Test
    @Transactional
    public void searchTransactionAttribute() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        transactionAttributeRepository.saveAndFlush(transactionAttribute);
        when(mockTransactionAttributeSearchRepository.search(queryStringQuery("id:" + transactionAttribute.getId())))
            .thenReturn(Collections.singletonList(transactionAttribute));

        // Search the transactionAttribute
        restTransactionAttributeMockMvc.perform(get("/api/_search/transaction-attributes?query=id:" + transactionAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].narativeLine1").value(hasItem(DEFAULT_NARATIVE_LINE_1)))
            .andExpect(jsonPath("$.[*].narativeLine2").value(hasItem(DEFAULT_NARATIVE_LINE_2)))
            .andExpect(jsonPath("$.[*].narativeLine3").value(hasItem(DEFAULT_NARATIVE_LINE_3)))
            .andExpect(jsonPath("$.[*].narativeLine4").value(hasItem(DEFAULT_NARATIVE_LINE_4)))
            .andExpect(jsonPath("$.[*].clientRefNumber").value(hasItem(DEFAULT_CLIENT_REF_NUMBER)));
    }
}
