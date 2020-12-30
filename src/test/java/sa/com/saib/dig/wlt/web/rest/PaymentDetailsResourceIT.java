package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.PaymentDetails;
import sa.com.saib.dig.wlt.repository.PaymentDetailsRepository;
import sa.com.saib.dig.wlt.repository.search.PaymentDetailsSearchRepository;

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
 * Integration tests for the {@link PaymentDetailsResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentDetailsResourceIT {

    private static final String DEFAULT_PAYOUT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_PAYOUT_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_MODE = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE_OF_TRANSFER = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE_OF_TRANSFER = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_OUT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PAY_OUT_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_DETAILS = "BBBBBBBBBB";

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.PaymentDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentDetailsSearchRepository mockPaymentDetailsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentDetailsMockMvc;

    private PaymentDetails paymentDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentDetails createEntity(EntityManager em) {
        PaymentDetails paymentDetails = new PaymentDetails()
            .payoutCurrency(DEFAULT_PAYOUT_CURRENCY)
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .purposeOfTransfer(DEFAULT_PURPOSE_OF_TRANSFER)
            .payOutCountryCode(DEFAULT_PAY_OUT_COUNTRY_CODE)
            .paymentDetails(DEFAULT_PAYMENT_DETAILS);
        return paymentDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentDetails createUpdatedEntity(EntityManager em) {
        PaymentDetails paymentDetails = new PaymentDetails()
            .payoutCurrency(UPDATED_PAYOUT_CURRENCY)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .purposeOfTransfer(UPDATED_PURPOSE_OF_TRANSFER)
            .payOutCountryCode(UPDATED_PAY_OUT_COUNTRY_CODE)
            .paymentDetails(UPDATED_PAYMENT_DETAILS);
        return paymentDetails;
    }

    @BeforeEach
    public void initTest() {
        paymentDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentDetails() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();
        // Create the PaymentDetails
        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isCreated());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getPayoutCurrency()).isEqualTo(DEFAULT_PAYOUT_CURRENCY);
        assertThat(testPaymentDetails.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testPaymentDetails.getPurposeOfTransfer()).isEqualTo(DEFAULT_PURPOSE_OF_TRANSFER);
        assertThat(testPaymentDetails.getPayOutCountryCode()).isEqualTo(DEFAULT_PAY_OUT_COUNTRY_CODE);
        assertThat(testPaymentDetails.getPaymentDetails()).isEqualTo(DEFAULT_PAYMENT_DETAILS);

        // Validate the PaymentDetails in Elasticsearch
        verify(mockPaymentDetailsSearchRepository, times(1)).save(testPaymentDetails);
    }

    @Test
    @Transactional
    public void createPaymentDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails with an existing ID
        paymentDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentDetails in Elasticsearch
        verify(mockPaymentDetailsSearchRepository, times(0)).save(paymentDetails);
    }


    @Test
    @Transactional
    public void getAllPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList
        restPaymentDetailsMockMvc.perform(get("/api/payment-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].payoutCurrency").value(hasItem(DEFAULT_PAYOUT_CURRENCY)))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE)))
            .andExpect(jsonPath("$.[*].purposeOfTransfer").value(hasItem(DEFAULT_PURPOSE_OF_TRANSFER)))
            .andExpect(jsonPath("$.[*].payOutCountryCode").value(hasItem(DEFAULT_PAY_OUT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].paymentDetails").value(hasItem(DEFAULT_PAYMENT_DETAILS)));
    }
    
    @Test
    @Transactional
    public void getPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/{id}", paymentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentDetails.getId().intValue()))
            .andExpect(jsonPath("$.payoutCurrency").value(DEFAULT_PAYOUT_CURRENCY))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE))
            .andExpect(jsonPath("$.purposeOfTransfer").value(DEFAULT_PURPOSE_OF_TRANSFER))
            .andExpect(jsonPath("$.payOutCountryCode").value(DEFAULT_PAY_OUT_COUNTRY_CODE))
            .andExpect(jsonPath("$.paymentDetails").value(DEFAULT_PAYMENT_DETAILS));
    }
    @Test
    @Transactional
    public void getNonExistingPaymentDetails() throws Exception {
        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Update the paymentDetails
        PaymentDetails updatedPaymentDetails = paymentDetailsRepository.findById(paymentDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentDetails are not directly saved in db
        em.detach(updatedPaymentDetails);
        updatedPaymentDetails
            .payoutCurrency(UPDATED_PAYOUT_CURRENCY)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .purposeOfTransfer(UPDATED_PURPOSE_OF_TRANSFER)
            .payOutCountryCode(UPDATED_PAY_OUT_COUNTRY_CODE)
            .paymentDetails(UPDATED_PAYMENT_DETAILS);

        restPaymentDetailsMockMvc.perform(put("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentDetails)))
            .andExpect(status().isOk());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getPayoutCurrency()).isEqualTo(UPDATED_PAYOUT_CURRENCY);
        assertThat(testPaymentDetails.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testPaymentDetails.getPurposeOfTransfer()).isEqualTo(UPDATED_PURPOSE_OF_TRANSFER);
        assertThat(testPaymentDetails.getPayOutCountryCode()).isEqualTo(UPDATED_PAY_OUT_COUNTRY_CODE);
        assertThat(testPaymentDetails.getPaymentDetails()).isEqualTo(UPDATED_PAYMENT_DETAILS);

        // Validate the PaymentDetails in Elasticsearch
        verify(mockPaymentDetailsSearchRepository, times(1)).save(testPaymentDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentDetailsMockMvc.perform(put("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentDetails in Elasticsearch
        verify(mockPaymentDetailsSearchRepository, times(0)).save(paymentDetails);
    }

    @Test
    @Transactional
    public void deletePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        int databaseSizeBeforeDelete = paymentDetailsRepository.findAll().size();

        // Delete the paymentDetails
        restPaymentDetailsMockMvc.perform(delete("/api/payment-details/{id}", paymentDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentDetails in Elasticsearch
        verify(mockPaymentDetailsSearchRepository, times(1)).deleteById(paymentDetails.getId());
    }

    @Test
    @Transactional
    public void searchPaymentDetails() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);
        when(mockPaymentDetailsSearchRepository.search(queryStringQuery("id:" + paymentDetails.getId())))
            .thenReturn(Collections.singletonList(paymentDetails));

        // Search the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/_search/payment-details?query=id:" + paymentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].payoutCurrency").value(hasItem(DEFAULT_PAYOUT_CURRENCY)))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE)))
            .andExpect(jsonPath("$.[*].purposeOfTransfer").value(hasItem(DEFAULT_PURPOSE_OF_TRANSFER)))
            .andExpect(jsonPath("$.[*].payOutCountryCode").value(hasItem(DEFAULT_PAY_OUT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].paymentDetails").value(hasItem(DEFAULT_PAYMENT_DETAILS)));
    }
}
