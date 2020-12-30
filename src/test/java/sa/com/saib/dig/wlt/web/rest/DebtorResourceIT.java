package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.Debtor;
import sa.com.saib.dig.wlt.repository.DebtorRepository;
import sa.com.saib.dig.wlt.repository.search.DebtorSearchRepository;

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
 * Integration tests for the {@link DebtorResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DebtorResourceIT {

    @Autowired
    private DebtorRepository debtorRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.DebtorSearchRepositoryMockConfiguration
     */
    @Autowired
    private DebtorSearchRepository mockDebtorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDebtorMockMvc;

    private Debtor debtor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Debtor createEntity(EntityManager em) {
        Debtor debtor = new Debtor();
        return debtor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Debtor createUpdatedEntity(EntityManager em) {
        Debtor debtor = new Debtor();
        return debtor;
    }

    @BeforeEach
    public void initTest() {
        debtor = createEntity(em);
    }

    @Test
    @Transactional
    public void createDebtor() throws Exception {
        int databaseSizeBeforeCreate = debtorRepository.findAll().size();
        // Create the Debtor
        restDebtorMockMvc.perform(post("/api/debtors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(debtor)))
            .andExpect(status().isCreated());

        // Validate the Debtor in the database
        List<Debtor> debtorList = debtorRepository.findAll();
        assertThat(debtorList).hasSize(databaseSizeBeforeCreate + 1);
        Debtor testDebtor = debtorList.get(debtorList.size() - 1);

        // Validate the Debtor in Elasticsearch
        verify(mockDebtorSearchRepository, times(1)).save(testDebtor);
    }

    @Test
    @Transactional
    public void createDebtorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = debtorRepository.findAll().size();

        // Create the Debtor with an existing ID
        debtor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDebtorMockMvc.perform(post("/api/debtors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(debtor)))
            .andExpect(status().isBadRequest());

        // Validate the Debtor in the database
        List<Debtor> debtorList = debtorRepository.findAll();
        assertThat(debtorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Debtor in Elasticsearch
        verify(mockDebtorSearchRepository, times(0)).save(debtor);
    }


    @Test
    @Transactional
    public void getAllDebtors() throws Exception {
        // Initialize the database
        debtorRepository.saveAndFlush(debtor);

        // Get all the debtorList
        restDebtorMockMvc.perform(get("/api/debtors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debtor.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDebtor() throws Exception {
        // Initialize the database
        debtorRepository.saveAndFlush(debtor);

        // Get the debtor
        restDebtorMockMvc.perform(get("/api/debtors/{id}", debtor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(debtor.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDebtor() throws Exception {
        // Get the debtor
        restDebtorMockMvc.perform(get("/api/debtors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDebtor() throws Exception {
        // Initialize the database
        debtorRepository.saveAndFlush(debtor);

        int databaseSizeBeforeUpdate = debtorRepository.findAll().size();

        // Update the debtor
        Debtor updatedDebtor = debtorRepository.findById(debtor.getId()).get();
        // Disconnect from session so that the updates on updatedDebtor are not directly saved in db
        em.detach(updatedDebtor);

        restDebtorMockMvc.perform(put("/api/debtors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDebtor)))
            .andExpect(status().isOk());

        // Validate the Debtor in the database
        List<Debtor> debtorList = debtorRepository.findAll();
        assertThat(debtorList).hasSize(databaseSizeBeforeUpdate);
        Debtor testDebtor = debtorList.get(debtorList.size() - 1);

        // Validate the Debtor in Elasticsearch
        verify(mockDebtorSearchRepository, times(1)).save(testDebtor);
    }

    @Test
    @Transactional
    public void updateNonExistingDebtor() throws Exception {
        int databaseSizeBeforeUpdate = debtorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDebtorMockMvc.perform(put("/api/debtors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(debtor)))
            .andExpect(status().isBadRequest());

        // Validate the Debtor in the database
        List<Debtor> debtorList = debtorRepository.findAll();
        assertThat(debtorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Debtor in Elasticsearch
        verify(mockDebtorSearchRepository, times(0)).save(debtor);
    }

    @Test
    @Transactional
    public void deleteDebtor() throws Exception {
        // Initialize the database
        debtorRepository.saveAndFlush(debtor);

        int databaseSizeBeforeDelete = debtorRepository.findAll().size();

        // Delete the debtor
        restDebtorMockMvc.perform(delete("/api/debtors/{id}", debtor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Debtor> debtorList = debtorRepository.findAll();
        assertThat(debtorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Debtor in Elasticsearch
        verify(mockDebtorSearchRepository, times(1)).deleteById(debtor.getId());
    }

    @Test
    @Transactional
    public void searchDebtor() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        debtorRepository.saveAndFlush(debtor);
        when(mockDebtorSearchRepository.search(queryStringQuery("id:" + debtor.getId())))
            .thenReturn(Collections.singletonList(debtor));

        // Search the debtor
        restDebtorMockMvc.perform(get("/api/_search/debtors?query=id:" + debtor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debtor.getId().intValue())));
    }
}
