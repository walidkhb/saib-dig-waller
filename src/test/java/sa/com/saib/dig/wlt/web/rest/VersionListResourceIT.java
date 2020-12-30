package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.VersionList;
import sa.com.saib.dig.wlt.repository.VersionListRepository;
import sa.com.saib.dig.wlt.repository.search.VersionListSearchRepository;

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
 * Integration tests for the {@link VersionListResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class VersionListResourceIT {

    private static final String DEFAULT_A_PI_RECORD = "AAAAAAAAAA";
    private static final String UPDATED_A_PI_RECORD = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VERSION_NUMBER = "BBBBBBBBBB";

    @Autowired
    private VersionListRepository versionListRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.VersionListSearchRepositoryMockConfiguration
     */
    @Autowired
    private VersionListSearchRepository mockVersionListSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVersionListMockMvc;

    private VersionList versionList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VersionList createEntity(EntityManager em) {
        VersionList versionList = new VersionList()
            .aPIRecord(DEFAULT_A_PI_RECORD)
            .versionNumber(DEFAULT_VERSION_NUMBER);
        return versionList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VersionList createUpdatedEntity(EntityManager em) {
        VersionList versionList = new VersionList()
            .aPIRecord(UPDATED_A_PI_RECORD)
            .versionNumber(UPDATED_VERSION_NUMBER);
        return versionList;
    }

    @BeforeEach
    public void initTest() {
        versionList = createEntity(em);
    }

    @Test
    @Transactional
    public void createVersionList() throws Exception {
        int databaseSizeBeforeCreate = versionListRepository.findAll().size();
        // Create the VersionList
        restVersionListMockMvc.perform(post("/api/version-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(versionList)))
            .andExpect(status().isCreated());

        // Validate the VersionList in the database
        List<VersionList> versionListList = versionListRepository.findAll();
        assertThat(versionListList).hasSize(databaseSizeBeforeCreate + 1);
        VersionList testVersionList = versionListList.get(versionListList.size() - 1);
        assertThat(testVersionList.getaPIRecord()).isEqualTo(DEFAULT_A_PI_RECORD);
        assertThat(testVersionList.getVersionNumber()).isEqualTo(DEFAULT_VERSION_NUMBER);

        // Validate the VersionList in Elasticsearch
        verify(mockVersionListSearchRepository, times(1)).save(testVersionList);
    }

    @Test
    @Transactional
    public void createVersionListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = versionListRepository.findAll().size();

        // Create the VersionList with an existing ID
        versionList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVersionListMockMvc.perform(post("/api/version-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(versionList)))
            .andExpect(status().isBadRequest());

        // Validate the VersionList in the database
        List<VersionList> versionListList = versionListRepository.findAll();
        assertThat(versionListList).hasSize(databaseSizeBeforeCreate);

        // Validate the VersionList in Elasticsearch
        verify(mockVersionListSearchRepository, times(0)).save(versionList);
    }


    @Test
    @Transactional
    public void getAllVersionLists() throws Exception {
        // Initialize the database
        versionListRepository.saveAndFlush(versionList);

        // Get all the versionListList
        restVersionListMockMvc.perform(get("/api/version-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versionList.getId().intValue())))
            .andExpect(jsonPath("$.[*].aPIRecord").value(hasItem(DEFAULT_A_PI_RECORD)))
            .andExpect(jsonPath("$.[*].versionNumber").value(hasItem(DEFAULT_VERSION_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getVersionList() throws Exception {
        // Initialize the database
        versionListRepository.saveAndFlush(versionList);

        // Get the versionList
        restVersionListMockMvc.perform(get("/api/version-lists/{id}", versionList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(versionList.getId().intValue()))
            .andExpect(jsonPath("$.aPIRecord").value(DEFAULT_A_PI_RECORD))
            .andExpect(jsonPath("$.versionNumber").value(DEFAULT_VERSION_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingVersionList() throws Exception {
        // Get the versionList
        restVersionListMockMvc.perform(get("/api/version-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVersionList() throws Exception {
        // Initialize the database
        versionListRepository.saveAndFlush(versionList);

        int databaseSizeBeforeUpdate = versionListRepository.findAll().size();

        // Update the versionList
        VersionList updatedVersionList = versionListRepository.findById(versionList.getId()).get();
        // Disconnect from session so that the updates on updatedVersionList are not directly saved in db
        em.detach(updatedVersionList);
        updatedVersionList
            .aPIRecord(UPDATED_A_PI_RECORD)
            .versionNumber(UPDATED_VERSION_NUMBER);

        restVersionListMockMvc.perform(put("/api/version-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVersionList)))
            .andExpect(status().isOk());

        // Validate the VersionList in the database
        List<VersionList> versionListList = versionListRepository.findAll();
        assertThat(versionListList).hasSize(databaseSizeBeforeUpdate);
        VersionList testVersionList = versionListList.get(versionListList.size() - 1);
        assertThat(testVersionList.getaPIRecord()).isEqualTo(UPDATED_A_PI_RECORD);
        assertThat(testVersionList.getVersionNumber()).isEqualTo(UPDATED_VERSION_NUMBER);

        // Validate the VersionList in Elasticsearch
        verify(mockVersionListSearchRepository, times(1)).save(testVersionList);
    }

    @Test
    @Transactional
    public void updateNonExistingVersionList() throws Exception {
        int databaseSizeBeforeUpdate = versionListRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersionListMockMvc.perform(put("/api/version-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(versionList)))
            .andExpect(status().isBadRequest());

        // Validate the VersionList in the database
        List<VersionList> versionListList = versionListRepository.findAll();
        assertThat(versionListList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VersionList in Elasticsearch
        verify(mockVersionListSearchRepository, times(0)).save(versionList);
    }

    @Test
    @Transactional
    public void deleteVersionList() throws Exception {
        // Initialize the database
        versionListRepository.saveAndFlush(versionList);

        int databaseSizeBeforeDelete = versionListRepository.findAll().size();

        // Delete the versionList
        restVersionListMockMvc.perform(delete("/api/version-lists/{id}", versionList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VersionList> versionListList = versionListRepository.findAll();
        assertThat(versionListList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VersionList in Elasticsearch
        verify(mockVersionListSearchRepository, times(1)).deleteById(versionList.getId());
    }

    @Test
    @Transactional
    public void searchVersionList() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        versionListRepository.saveAndFlush(versionList);
        when(mockVersionListSearchRepository.search(queryStringQuery("id:" + versionList.getId())))
            .thenReturn(Collections.singletonList(versionList));

        // Search the versionList
        restVersionListMockMvc.perform(get("/api/_search/version-lists?query=id:" + versionList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versionList.getId().intValue())))
            .andExpect(jsonPath("$.[*].aPIRecord").value(hasItem(DEFAULT_A_PI_RECORD)))
            .andExpect(jsonPath("$.[*].versionNumber").value(hasItem(DEFAULT_VERSION_NUMBER)));
    }
}
