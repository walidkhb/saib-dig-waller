package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.Amount;
import sa.com.saib.dig.wlt.repository.AmountRepository;
import sa.com.saib.dig.wlt.repository.search.AmountSearchRepository;

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
 * Integration tests for the {@link AmountResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AmountResourceIT {

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    private static final Float DEFAULT_NET_AMOUNT = 1F;
    private static final Float UPDATED_NET_AMOUNT = 2F;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE_OF_TRANSFER = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE_OF_TRANSFER = "BBBBBBBBBB";

    @Autowired
    private AmountRepository amountRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.AmountSearchRepositoryMockConfiguration
     */
    @Autowired
    private AmountSearchRepository mockAmountSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAmountMockMvc;

    private Amount amount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Amount createEntity(EntityManager em) {
        Amount amount = new Amount()
            .amount(DEFAULT_AMOUNT)
            .netAmount(DEFAULT_NET_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .purposeOfTransfer(DEFAULT_PURPOSE_OF_TRANSFER);
        return amount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Amount createUpdatedEntity(EntityManager em) {
        Amount amount = new Amount()
            .amount(UPDATED_AMOUNT)
            .netAmount(UPDATED_NET_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .purposeOfTransfer(UPDATED_PURPOSE_OF_TRANSFER);
        return amount;
    }

    @BeforeEach
    public void initTest() {
        amount = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmount() throws Exception {
        int databaseSizeBeforeCreate = amountRepository.findAll().size();
        // Create the Amount
        restAmountMockMvc.perform(post("/api/amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isCreated());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeCreate + 1);
        Amount testAmount = amountList.get(amountList.size() - 1);
        assertThat(testAmount.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAmount.getNetAmount()).isEqualTo(DEFAULT_NET_AMOUNT);
        assertThat(testAmount.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testAmount.getPurposeOfTransfer()).isEqualTo(DEFAULT_PURPOSE_OF_TRANSFER);

        // Validate the Amount in Elasticsearch
        verify(mockAmountSearchRepository, times(1)).save(testAmount);
    }

    @Test
    @Transactional
    public void createAmountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amountRepository.findAll().size();

        // Create the Amount with an existing ID
        amount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmountMockMvc.perform(post("/api/amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isBadRequest());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeCreate);

        // Validate the Amount in Elasticsearch
        verify(mockAmountSearchRepository, times(0)).save(amount);
    }


    @Test
    @Transactional
    public void getAllAmounts() throws Exception {
        // Initialize the database
        amountRepository.saveAndFlush(amount);

        // Get all the amountList
        restAmountMockMvc.perform(get("/api/amounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amount.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].netAmount").value(hasItem(DEFAULT_NET_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].purposeOfTransfer").value(hasItem(DEFAULT_PURPOSE_OF_TRANSFER)));
    }
    
    @Test
    @Transactional
    public void getAmount() throws Exception {
        // Initialize the database
        amountRepository.saveAndFlush(amount);

        // Get the amount
        restAmountMockMvc.perform(get("/api/amounts/{id}", amount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(amount.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.netAmount").value(DEFAULT_NET_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.purposeOfTransfer").value(DEFAULT_PURPOSE_OF_TRANSFER));
    }
    @Test
    @Transactional
    public void getNonExistingAmount() throws Exception {
        // Get the amount
        restAmountMockMvc.perform(get("/api/amounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmount() throws Exception {
        // Initialize the database
        amountRepository.saveAndFlush(amount);

        int databaseSizeBeforeUpdate = amountRepository.findAll().size();

        // Update the amount
        Amount updatedAmount = amountRepository.findById(amount.getId()).get();
        // Disconnect from session so that the updates on updatedAmount are not directly saved in db
        em.detach(updatedAmount);
        updatedAmount
            .amount(UPDATED_AMOUNT)
            .netAmount(UPDATED_NET_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .purposeOfTransfer(UPDATED_PURPOSE_OF_TRANSFER);

        restAmountMockMvc.perform(put("/api/amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmount)))
            .andExpect(status().isOk());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeUpdate);
        Amount testAmount = amountList.get(amountList.size() - 1);
        assertThat(testAmount.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAmount.getNetAmount()).isEqualTo(UPDATED_NET_AMOUNT);
        assertThat(testAmount.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testAmount.getPurposeOfTransfer()).isEqualTo(UPDATED_PURPOSE_OF_TRANSFER);

        // Validate the Amount in Elasticsearch
        verify(mockAmountSearchRepository, times(1)).save(testAmount);
    }

    @Test
    @Transactional
    public void updateNonExistingAmount() throws Exception {
        int databaseSizeBeforeUpdate = amountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmountMockMvc.perform(put("/api/amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isBadRequest());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Amount in Elasticsearch
        verify(mockAmountSearchRepository, times(0)).save(amount);
    }

    @Test
    @Transactional
    public void deleteAmount() throws Exception {
        // Initialize the database
        amountRepository.saveAndFlush(amount);

        int databaseSizeBeforeDelete = amountRepository.findAll().size();

        // Delete the amount
        restAmountMockMvc.perform(delete("/api/amounts/{id}", amount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Amount in Elasticsearch
        verify(mockAmountSearchRepository, times(1)).deleteById(amount.getId());
    }

    @Test
    @Transactional
    public void searchAmount() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        amountRepository.saveAndFlush(amount);
        when(mockAmountSearchRepository.search(queryStringQuery("id:" + amount.getId())))
            .thenReturn(Collections.singletonList(amount));

        // Search the amount
        restAmountMockMvc.perform(get("/api/_search/amounts?query=id:" + amount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amount.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].netAmount").value(hasItem(DEFAULT_NET_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].purposeOfTransfer").value(hasItem(DEFAULT_PURPOSE_OF_TRANSFER)));
    }
}
