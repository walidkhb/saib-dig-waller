package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.Creditor;
import sa.com.saib.dig.wlt.repository.CreditorRepository;
import sa.com.saib.dig.wlt.repository.search.CreditorSearchRepository;

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
 * Integration tests for the {@link CreditorResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CreditorResourceIT {

    @Autowired
    private CreditorRepository creditorRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.CreditorSearchRepositoryMockConfiguration
     */
    @Autowired
    private CreditorSearchRepository mockCreditorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditorMockMvc;

    private Creditor creditor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Creditor createEntity(EntityManager em) {
        Creditor creditor = new Creditor();
        return creditor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Creditor createUpdatedEntity(EntityManager em) {
        Creditor creditor = new Creditor();
        return creditor;
    }

    @BeforeEach
    public void initTest() {
        creditor = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditor() throws Exception {
        int databaseSizeBeforeCreate = creditorRepository.findAll().size();
        // Create the Creditor
        restCreditorMockMvc.perform(post("/api/creditors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditor)))
            .andExpect(status().isCreated());

        // Validate the Creditor in the database
        List<Creditor> creditorList = creditorRepository.findAll();
        assertThat(creditorList).hasSize(databaseSizeBeforeCreate + 1);
        Creditor testCreditor = creditorList.get(creditorList.size() - 1);

        // Validate the Creditor in Elasticsearch
        verify(mockCreditorSearchRepository, times(1)).save(testCreditor);
    }

    @Test
    @Transactional
    public void createCreditorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditorRepository.findAll().size();

        // Create the Creditor with an existing ID
        creditor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditorMockMvc.perform(post("/api/creditors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditor)))
            .andExpect(status().isBadRequest());

        // Validate the Creditor in the database
        List<Creditor> creditorList = creditorRepository.findAll();
        assertThat(creditorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Creditor in Elasticsearch
        verify(mockCreditorSearchRepository, times(0)).save(creditor);
    }


    @Test
    @Transactional
    public void getAllCreditors() throws Exception {
        // Initialize the database
        creditorRepository.saveAndFlush(creditor);

        // Get all the creditorList
        restCreditorMockMvc.perform(get("/api/creditors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditor.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCreditor() throws Exception {
        // Initialize the database
        creditorRepository.saveAndFlush(creditor);

        // Get the creditor
        restCreditorMockMvc.perform(get("/api/creditors/{id}", creditor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creditor.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCreditor() throws Exception {
        // Get the creditor
        restCreditorMockMvc.perform(get("/api/creditors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditor() throws Exception {
        // Initialize the database
        creditorRepository.saveAndFlush(creditor);

        int databaseSizeBeforeUpdate = creditorRepository.findAll().size();

        // Update the creditor
        Creditor updatedCreditor = creditorRepository.findById(creditor.getId()).get();
        // Disconnect from session so that the updates on updatedCreditor are not directly saved in db
        em.detach(updatedCreditor);

        restCreditorMockMvc.perform(put("/api/creditors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCreditor)))
            .andExpect(status().isOk());

        // Validate the Creditor in the database
        List<Creditor> creditorList = creditorRepository.findAll();
        assertThat(creditorList).hasSize(databaseSizeBeforeUpdate);
        Creditor testCreditor = creditorList.get(creditorList.size() - 1);

        // Validate the Creditor in Elasticsearch
        verify(mockCreditorSearchRepository, times(1)).save(testCreditor);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditor() throws Exception {
        int databaseSizeBeforeUpdate = creditorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditorMockMvc.perform(put("/api/creditors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditor)))
            .andExpect(status().isBadRequest());

        // Validate the Creditor in the database
        List<Creditor> creditorList = creditorRepository.findAll();
        assertThat(creditorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Creditor in Elasticsearch
        verify(mockCreditorSearchRepository, times(0)).save(creditor);
    }

    @Test
    @Transactional
    public void deleteCreditor() throws Exception {
        // Initialize the database
        creditorRepository.saveAndFlush(creditor);

        int databaseSizeBeforeDelete = creditorRepository.findAll().size();

        // Delete the creditor
        restCreditorMockMvc.perform(delete("/api/creditors/{id}", creditor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Creditor> creditorList = creditorRepository.findAll();
        assertThat(creditorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Creditor in Elasticsearch
        verify(mockCreditorSearchRepository, times(1)).deleteById(creditor.getId());
    }

    @Test
    @Transactional
    public void searchCreditor() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        creditorRepository.saveAndFlush(creditor);
        when(mockCreditorSearchRepository.search(queryStringQuery("id:" + creditor.getId())))
            .thenReturn(Collections.singletonList(creditor));

        // Search the creditor
        restCreditorMockMvc.perform(get("/api/_search/creditors?query=id:" + creditor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditor.getId().intValue())));
    }
}
