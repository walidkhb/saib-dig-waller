package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.KYCInfo;
import sa.com.saib.dig.wlt.repository.KYCInfoRepository;
import sa.com.saib.dig.wlt.repository.search.KYCInfoSearchRepository;

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
 * Integration tests for the {@link KYCInfoResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class KYCInfoResourceIT {

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private KYCInfoRepository kYCInfoRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.KYCInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private KYCInfoSearchRepository mockKYCInfoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKYCInfoMockMvc;

    private KYCInfo kYCInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KYCInfo createEntity(EntityManager em) {
        KYCInfo kYCInfo = new KYCInfo()
            .level(DEFAULT_LEVEL)
            .status(DEFAULT_STATUS);
        return kYCInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KYCInfo createUpdatedEntity(EntityManager em) {
        KYCInfo kYCInfo = new KYCInfo()
            .level(UPDATED_LEVEL)
            .status(UPDATED_STATUS);
        return kYCInfo;
    }

    @BeforeEach
    public void initTest() {
        kYCInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createKYCInfo() throws Exception {
        int databaseSizeBeforeCreate = kYCInfoRepository.findAll().size();
        // Create the KYCInfo
        restKYCInfoMockMvc.perform(post("/api/kyc-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kYCInfo)))
            .andExpect(status().isCreated());

        // Validate the KYCInfo in the database
        List<KYCInfo> kYCInfoList = kYCInfoRepository.findAll();
        assertThat(kYCInfoList).hasSize(databaseSizeBeforeCreate + 1);
        KYCInfo testKYCInfo = kYCInfoList.get(kYCInfoList.size() - 1);
        assertThat(testKYCInfo.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testKYCInfo.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the KYCInfo in Elasticsearch
        verify(mockKYCInfoSearchRepository, times(1)).save(testKYCInfo);
    }

    @Test
    @Transactional
    public void createKYCInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kYCInfoRepository.findAll().size();

        // Create the KYCInfo with an existing ID
        kYCInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKYCInfoMockMvc.perform(post("/api/kyc-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kYCInfo)))
            .andExpect(status().isBadRequest());

        // Validate the KYCInfo in the database
        List<KYCInfo> kYCInfoList = kYCInfoRepository.findAll();
        assertThat(kYCInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the KYCInfo in Elasticsearch
        verify(mockKYCInfoSearchRepository, times(0)).save(kYCInfo);
    }


    @Test
    @Transactional
    public void getAllKYCInfos() throws Exception {
        // Initialize the database
        kYCInfoRepository.saveAndFlush(kYCInfo);

        // Get all the kYCInfoList
        restKYCInfoMockMvc.perform(get("/api/kyc-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kYCInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getKYCInfo() throws Exception {
        // Initialize the database
        kYCInfoRepository.saveAndFlush(kYCInfo);

        // Get the kYCInfo
        restKYCInfoMockMvc.perform(get("/api/kyc-infos/{id}", kYCInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kYCInfo.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }
    @Test
    @Transactional
    public void getNonExistingKYCInfo() throws Exception {
        // Get the kYCInfo
        restKYCInfoMockMvc.perform(get("/api/kyc-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKYCInfo() throws Exception {
        // Initialize the database
        kYCInfoRepository.saveAndFlush(kYCInfo);

        int databaseSizeBeforeUpdate = kYCInfoRepository.findAll().size();

        // Update the kYCInfo
        KYCInfo updatedKYCInfo = kYCInfoRepository.findById(kYCInfo.getId()).get();
        // Disconnect from session so that the updates on updatedKYCInfo are not directly saved in db
        em.detach(updatedKYCInfo);
        updatedKYCInfo
            .level(UPDATED_LEVEL)
            .status(UPDATED_STATUS);

        restKYCInfoMockMvc.perform(put("/api/kyc-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKYCInfo)))
            .andExpect(status().isOk());

        // Validate the KYCInfo in the database
        List<KYCInfo> kYCInfoList = kYCInfoRepository.findAll();
        assertThat(kYCInfoList).hasSize(databaseSizeBeforeUpdate);
        KYCInfo testKYCInfo = kYCInfoList.get(kYCInfoList.size() - 1);
        assertThat(testKYCInfo.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testKYCInfo.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the KYCInfo in Elasticsearch
        verify(mockKYCInfoSearchRepository, times(1)).save(testKYCInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingKYCInfo() throws Exception {
        int databaseSizeBeforeUpdate = kYCInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKYCInfoMockMvc.perform(put("/api/kyc-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kYCInfo)))
            .andExpect(status().isBadRequest());

        // Validate the KYCInfo in the database
        List<KYCInfo> kYCInfoList = kYCInfoRepository.findAll();
        assertThat(kYCInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the KYCInfo in Elasticsearch
        verify(mockKYCInfoSearchRepository, times(0)).save(kYCInfo);
    }

    @Test
    @Transactional
    public void deleteKYCInfo() throws Exception {
        // Initialize the database
        kYCInfoRepository.saveAndFlush(kYCInfo);

        int databaseSizeBeforeDelete = kYCInfoRepository.findAll().size();

        // Delete the kYCInfo
        restKYCInfoMockMvc.perform(delete("/api/kyc-infos/{id}", kYCInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KYCInfo> kYCInfoList = kYCInfoRepository.findAll();
        assertThat(kYCInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the KYCInfo in Elasticsearch
        verify(mockKYCInfoSearchRepository, times(1)).deleteById(kYCInfo.getId());
    }

    @Test
    @Transactional
    public void searchKYCInfo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        kYCInfoRepository.saveAndFlush(kYCInfo);
        when(mockKYCInfoSearchRepository.search(queryStringQuery("id:" + kYCInfo.getId())))
            .thenReturn(Collections.singletonList(kYCInfo));

        // Search the kYCInfo
        restKYCInfoMockMvc.perform(get("/api/_search/kyc-infos?query=id:" + kYCInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kYCInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
}
