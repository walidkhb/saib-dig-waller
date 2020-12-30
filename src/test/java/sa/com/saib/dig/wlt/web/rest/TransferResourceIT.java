package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.Transfer;
import sa.com.saib.dig.wlt.repository.TransferRepository;
import sa.com.saib.dig.wlt.repository.search.TransferSearchRepository;

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
 * Integration tests for the {@link TransferResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransferResourceIT {

    @Autowired
    private TransferRepository transferRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.TransferSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransferSearchRepository mockTransferSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferMockMvc;

    private Transfer transfer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createEntity(EntityManager em) {
        Transfer transfer = new Transfer();
        return transfer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createUpdatedEntity(EntityManager em) {
        Transfer transfer = new Transfer();
        return transfer;
    }

    @BeforeEach
    public void initTest() {
        transfer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransfer() throws Exception {
        int databaseSizeBeforeCreate = transferRepository.findAll().size();
        // Create the Transfer
        restTransferMockMvc.perform(post("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isCreated());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate + 1);
        Transfer testTransfer = transferList.get(transferList.size() - 1);

        // Validate the Transfer in Elasticsearch
        verify(mockTransferSearchRepository, times(1)).save(testTransfer);
    }

    @Test
    @Transactional
    public void createTransferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transferRepository.findAll().size();

        // Create the Transfer with an existing ID
        transfer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferMockMvc.perform(post("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate);

        // Validate the Transfer in Elasticsearch
        verify(mockTransferSearchRepository, times(0)).save(transfer);
    }


    @Test
    @Transactional
    public void getAllTransfers() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList
        restTransferMockMvc.perform(get("/api/transfers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfer.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get the transfer
        restTransferMockMvc.perform(get("/api/transfers/{id}", transfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transfer.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTransfer() throws Exception {
        // Get the transfer
        restTransferMockMvc.perform(get("/api/transfers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer
        Transfer updatedTransfer = transferRepository.findById(transfer.getId()).get();
        // Disconnect from session so that the updates on updatedTransfer are not directly saved in db
        em.detach(updatedTransfer);

        restTransferMockMvc.perform(put("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransfer)))
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);

        // Validate the Transfer in Elasticsearch
        verify(mockTransferSearchRepository, times(1)).save(testTransfer);
    }

    @Test
    @Transactional
    public void updateNonExistingTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferMockMvc.perform(put("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Transfer in Elasticsearch
        verify(mockTransferSearchRepository, times(0)).save(transfer);
    }

    @Test
    @Transactional
    public void deleteTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeDelete = transferRepository.findAll().size();

        // Delete the transfer
        restTransferMockMvc.perform(delete("/api/transfers/{id}", transfer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Transfer in Elasticsearch
        verify(mockTransferSearchRepository, times(1)).deleteById(transfer.getId());
    }

    @Test
    @Transactional
    public void searchTransfer() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
        when(mockTransferSearchRepository.search(queryStringQuery("id:" + transfer.getId())))
            .thenReturn(Collections.singletonList(transfer));

        // Search the transfer
        restTransferMockMvc.perform(get("/api/_search/transfers?query=id:" + transfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfer.getId().intValue())));
    }
}
