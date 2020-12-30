package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.ChargeAmount;
import sa.com.saib.dig.wlt.repository.ChargeAmountRepository;
import sa.com.saib.dig.wlt.repository.search.ChargeAmountSearchRepository;

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
 * Integration tests for the {@link ChargeAmountResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChargeAmountResourceIT {

    private static final String DEFAULT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_V_AT = "AAAAAAAAAA";
    private static final String UPDATED_V_AT = "BBBBBBBBBB";

    @Autowired
    private ChargeAmountRepository chargeAmountRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.ChargeAmountSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChargeAmountSearchRepository mockChargeAmountSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChargeAmountMockMvc;

    private ChargeAmount chargeAmount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChargeAmount createEntity(EntityManager em) {
        ChargeAmount chargeAmount = new ChargeAmount()
            .amount(DEFAULT_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .vAT(DEFAULT_V_AT);
        return chargeAmount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChargeAmount createUpdatedEntity(EntityManager em) {
        ChargeAmount chargeAmount = new ChargeAmount()
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .vAT(UPDATED_V_AT);
        return chargeAmount;
    }

    @BeforeEach
    public void initTest() {
        chargeAmount = createEntity(em);
    }

    @Test
    @Transactional
    public void createChargeAmount() throws Exception {
        int databaseSizeBeforeCreate = chargeAmountRepository.findAll().size();
        // Create the ChargeAmount
        restChargeAmountMockMvc.perform(post("/api/charge-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chargeAmount)))
            .andExpect(status().isCreated());

        // Validate the ChargeAmount in the database
        List<ChargeAmount> chargeAmountList = chargeAmountRepository.findAll();
        assertThat(chargeAmountList).hasSize(databaseSizeBeforeCreate + 1);
        ChargeAmount testChargeAmount = chargeAmountList.get(chargeAmountList.size() - 1);
        assertThat(testChargeAmount.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testChargeAmount.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testChargeAmount.getvAT()).isEqualTo(DEFAULT_V_AT);

        // Validate the ChargeAmount in Elasticsearch
        verify(mockChargeAmountSearchRepository, times(1)).save(testChargeAmount);
    }

    @Test
    @Transactional
    public void createChargeAmountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chargeAmountRepository.findAll().size();

        // Create the ChargeAmount with an existing ID
        chargeAmount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargeAmountMockMvc.perform(post("/api/charge-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chargeAmount)))
            .andExpect(status().isBadRequest());

        // Validate the ChargeAmount in the database
        List<ChargeAmount> chargeAmountList = chargeAmountRepository.findAll();
        assertThat(chargeAmountList).hasSize(databaseSizeBeforeCreate);

        // Validate the ChargeAmount in Elasticsearch
        verify(mockChargeAmountSearchRepository, times(0)).save(chargeAmount);
    }


    @Test
    @Transactional
    public void getAllChargeAmounts() throws Exception {
        // Initialize the database
        chargeAmountRepository.saveAndFlush(chargeAmount);

        // Get all the chargeAmountList
        restChargeAmountMockMvc.perform(get("/api/charge-amounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargeAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].vAT").value(hasItem(DEFAULT_V_AT)));
    }
    
    @Test
    @Transactional
    public void getChargeAmount() throws Exception {
        // Initialize the database
        chargeAmountRepository.saveAndFlush(chargeAmount);

        // Get the chargeAmount
        restChargeAmountMockMvc.perform(get("/api/charge-amounts/{id}", chargeAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chargeAmount.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.vAT").value(DEFAULT_V_AT));
    }
    @Test
    @Transactional
    public void getNonExistingChargeAmount() throws Exception {
        // Get the chargeAmount
        restChargeAmountMockMvc.perform(get("/api/charge-amounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChargeAmount() throws Exception {
        // Initialize the database
        chargeAmountRepository.saveAndFlush(chargeAmount);

        int databaseSizeBeforeUpdate = chargeAmountRepository.findAll().size();

        // Update the chargeAmount
        ChargeAmount updatedChargeAmount = chargeAmountRepository.findById(chargeAmount.getId()).get();
        // Disconnect from session so that the updates on updatedChargeAmount are not directly saved in db
        em.detach(updatedChargeAmount);
        updatedChargeAmount
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .vAT(UPDATED_V_AT);

        restChargeAmountMockMvc.perform(put("/api/charge-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedChargeAmount)))
            .andExpect(status().isOk());

        // Validate the ChargeAmount in the database
        List<ChargeAmount> chargeAmountList = chargeAmountRepository.findAll();
        assertThat(chargeAmountList).hasSize(databaseSizeBeforeUpdate);
        ChargeAmount testChargeAmount = chargeAmountList.get(chargeAmountList.size() - 1);
        assertThat(testChargeAmount.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testChargeAmount.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testChargeAmount.getvAT()).isEqualTo(UPDATED_V_AT);

        // Validate the ChargeAmount in Elasticsearch
        verify(mockChargeAmountSearchRepository, times(1)).save(testChargeAmount);
    }

    @Test
    @Transactional
    public void updateNonExistingChargeAmount() throws Exception {
        int databaseSizeBeforeUpdate = chargeAmountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargeAmountMockMvc.perform(put("/api/charge-amounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chargeAmount)))
            .andExpect(status().isBadRequest());

        // Validate the ChargeAmount in the database
        List<ChargeAmount> chargeAmountList = chargeAmountRepository.findAll();
        assertThat(chargeAmountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ChargeAmount in Elasticsearch
        verify(mockChargeAmountSearchRepository, times(0)).save(chargeAmount);
    }

    @Test
    @Transactional
    public void deleteChargeAmount() throws Exception {
        // Initialize the database
        chargeAmountRepository.saveAndFlush(chargeAmount);

        int databaseSizeBeforeDelete = chargeAmountRepository.findAll().size();

        // Delete the chargeAmount
        restChargeAmountMockMvc.perform(delete("/api/charge-amounts/{id}", chargeAmount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChargeAmount> chargeAmountList = chargeAmountRepository.findAll();
        assertThat(chargeAmountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ChargeAmount in Elasticsearch
        verify(mockChargeAmountSearchRepository, times(1)).deleteById(chargeAmount.getId());
    }

    @Test
    @Transactional
    public void searchChargeAmount() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        chargeAmountRepository.saveAndFlush(chargeAmount);
        when(mockChargeAmountSearchRepository.search(queryStringQuery("id:" + chargeAmount.getId())))
            .thenReturn(Collections.singletonList(chargeAmount));

        // Search the chargeAmount
        restChargeAmountMockMvc.perform(get("/api/_search/charge-amounts?query=id:" + chargeAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargeAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].vAT").value(hasItem(DEFAULT_V_AT)));
    }
}
