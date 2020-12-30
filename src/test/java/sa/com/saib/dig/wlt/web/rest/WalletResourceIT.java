package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.Wallet;
import sa.com.saib.dig.wlt.repository.WalletRepository;
import sa.com.saib.dig.wlt.repository.search.WalletSearchRepository;

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
 * Integration tests for the {@link WalletResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class WalletResourceIT {

    private static final String DEFAULT_WALLET_ID = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_UPDATE_DATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_UPDATE_DATE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_SUB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_SUB_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WalletRepository walletRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.WalletSearchRepositoryMockConfiguration
     */
    @Autowired
    private WalletSearchRepository mockWalletSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWalletMockMvc;

    private Wallet wallet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wallet createEntity(EntityManager em) {
        Wallet wallet = new Wallet()
            .walletId(DEFAULT_WALLET_ID)
            .status(DEFAULT_STATUS)
            .statusUpdateDateTime(DEFAULT_STATUS_UPDATE_DATE_TIME)
            .currency(DEFAULT_CURRENCY)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .accountSubType(DEFAULT_ACCOUNT_SUB_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return wallet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wallet createUpdatedEntity(EntityManager em) {
        Wallet wallet = new Wallet()
            .walletId(UPDATED_WALLET_ID)
            .status(UPDATED_STATUS)
            .statusUpdateDateTime(UPDATED_STATUS_UPDATE_DATE_TIME)
            .currency(UPDATED_CURRENCY)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountSubType(UPDATED_ACCOUNT_SUB_TYPE)
            .description(UPDATED_DESCRIPTION);
        return wallet;
    }

    @BeforeEach
    public void initTest() {
        wallet = createEntity(em);
    }

    @Test
    @Transactional
    public void createWallet() throws Exception {
        int databaseSizeBeforeCreate = walletRepository.findAll().size();
        // Create the Wallet
        restWalletMockMvc.perform(post("/api/wallets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wallet)))
            .andExpect(status().isCreated());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeCreate + 1);
        Wallet testWallet = walletList.get(walletList.size() - 1);
        assertThat(testWallet.getWalletId()).isEqualTo(DEFAULT_WALLET_ID);
        assertThat(testWallet.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWallet.getStatusUpdateDateTime()).isEqualTo(DEFAULT_STATUS_UPDATE_DATE_TIME);
        assertThat(testWallet.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testWallet.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testWallet.getAccountSubType()).isEqualTo(DEFAULT_ACCOUNT_SUB_TYPE);
        assertThat(testWallet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Wallet in Elasticsearch
        verify(mockWalletSearchRepository, times(1)).save(testWallet);
    }

    @Test
    @Transactional
    public void createWalletWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = walletRepository.findAll().size();

        // Create the Wallet with an existing ID
        wallet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalletMockMvc.perform(post("/api/wallets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wallet)))
            .andExpect(status().isBadRequest());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeCreate);

        // Validate the Wallet in Elasticsearch
        verify(mockWalletSearchRepository, times(0)).save(wallet);
    }


    @Test
    @Transactional
    public void getAllWallets() throws Exception {
        // Initialize the database
        walletRepository.saveAndFlush(wallet);

        // Get all the walletList
        restWalletMockMvc.perform(get("/api/wallets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wallet.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletId").value(hasItem(DEFAULT_WALLET_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusUpdateDateTime").value(hasItem(DEFAULT_STATUS_UPDATE_DATE_TIME)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].accountSubType").value(hasItem(DEFAULT_ACCOUNT_SUB_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getWallet() throws Exception {
        // Initialize the database
        walletRepository.saveAndFlush(wallet);

        // Get the wallet
        restWalletMockMvc.perform(get("/api/wallets/{id}", wallet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wallet.getId().intValue()))
            .andExpect(jsonPath("$.walletId").value(DEFAULT_WALLET_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statusUpdateDateTime").value(DEFAULT_STATUS_UPDATE_DATE_TIME))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE))
            .andExpect(jsonPath("$.accountSubType").value(DEFAULT_ACCOUNT_SUB_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingWallet() throws Exception {
        // Get the wallet
        restWalletMockMvc.perform(get("/api/wallets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWallet() throws Exception {
        // Initialize the database
        walletRepository.saveAndFlush(wallet);

        int databaseSizeBeforeUpdate = walletRepository.findAll().size();

        // Update the wallet
        Wallet updatedWallet = walletRepository.findById(wallet.getId()).get();
        // Disconnect from session so that the updates on updatedWallet are not directly saved in db
        em.detach(updatedWallet);
        updatedWallet
            .walletId(UPDATED_WALLET_ID)
            .status(UPDATED_STATUS)
            .statusUpdateDateTime(UPDATED_STATUS_UPDATE_DATE_TIME)
            .currency(UPDATED_CURRENCY)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountSubType(UPDATED_ACCOUNT_SUB_TYPE)
            .description(UPDATED_DESCRIPTION);

        restWalletMockMvc.perform(put("/api/wallets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWallet)))
            .andExpect(status().isOk());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
        Wallet testWallet = walletList.get(walletList.size() - 1);
        assertThat(testWallet.getWalletId()).isEqualTo(UPDATED_WALLET_ID);
        assertThat(testWallet.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWallet.getStatusUpdateDateTime()).isEqualTo(UPDATED_STATUS_UPDATE_DATE_TIME);
        assertThat(testWallet.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testWallet.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testWallet.getAccountSubType()).isEqualTo(UPDATED_ACCOUNT_SUB_TYPE);
        assertThat(testWallet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Wallet in Elasticsearch
        verify(mockWalletSearchRepository, times(1)).save(testWallet);
    }

    @Test
    @Transactional
    public void updateNonExistingWallet() throws Exception {
        int databaseSizeBeforeUpdate = walletRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletMockMvc.perform(put("/api/wallets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wallet)))
            .andExpect(status().isBadRequest());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Wallet in Elasticsearch
        verify(mockWalletSearchRepository, times(0)).save(wallet);
    }

    @Test
    @Transactional
    public void deleteWallet() throws Exception {
        // Initialize the database
        walletRepository.saveAndFlush(wallet);

        int databaseSizeBeforeDelete = walletRepository.findAll().size();

        // Delete the wallet
        restWalletMockMvc.perform(delete("/api/wallets/{id}", wallet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Wallet in Elasticsearch
        verify(mockWalletSearchRepository, times(1)).deleteById(wallet.getId());
    }

    @Test
    @Transactional
    public void searchWallet() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        walletRepository.saveAndFlush(wallet);
        when(mockWalletSearchRepository.search(queryStringQuery("id:" + wallet.getId())))
            .thenReturn(Collections.singletonList(wallet));

        // Search the wallet
        restWalletMockMvc.perform(get("/api/_search/wallets?query=id:" + wallet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wallet.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletId").value(hasItem(DEFAULT_WALLET_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusUpdateDateTime").value(hasItem(DEFAULT_STATUS_UPDATE_DATE_TIME)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].accountSubType").value(hasItem(DEFAULT_ACCOUNT_SUB_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
