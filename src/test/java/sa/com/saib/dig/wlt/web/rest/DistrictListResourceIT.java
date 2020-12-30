package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.DistrictList;
import sa.com.saib.dig.wlt.repository.DistrictListRepository;
import sa.com.saib.dig.wlt.repository.search.DistrictListSearchRepository;

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
 * Integration tests for the {@link DistrictListResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DistrictListResourceIT {

    private static final String DEFAULT_DISTRICT_ID = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_NAME = "BBBBBBBBBB";

    @Autowired
    private DistrictListRepository districtListRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.DistrictListSearchRepositoryMockConfiguration
     */
    @Autowired
    private DistrictListSearchRepository mockDistrictListSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictListMockMvc;

    private DistrictList districtList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistrictList createEntity(EntityManager em) {
        DistrictList districtList = new DistrictList()
            .districtId(DEFAULT_DISTRICT_ID)
            .districtName(DEFAULT_DISTRICT_NAME);
        return districtList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistrictList createUpdatedEntity(EntityManager em) {
        DistrictList districtList = new DistrictList()
            .districtId(UPDATED_DISTRICT_ID)
            .districtName(UPDATED_DISTRICT_NAME);
        return districtList;
    }

    @BeforeEach
    public void initTest() {
        districtList = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistrictList() throws Exception {
        int databaseSizeBeforeCreate = districtListRepository.findAll().size();
        // Create the DistrictList
        restDistrictListMockMvc.perform(post("/api/district-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtList)))
            .andExpect(status().isCreated());

        // Validate the DistrictList in the database
        List<DistrictList> districtListList = districtListRepository.findAll();
        assertThat(districtListList).hasSize(databaseSizeBeforeCreate + 1);
        DistrictList testDistrictList = districtListList.get(districtListList.size() - 1);
        assertThat(testDistrictList.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
        assertThat(testDistrictList.getDistrictName()).isEqualTo(DEFAULT_DISTRICT_NAME);

        // Validate the DistrictList in Elasticsearch
        verify(mockDistrictListSearchRepository, times(1)).save(testDistrictList);
    }

    @Test
    @Transactional
    public void createDistrictListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = districtListRepository.findAll().size();

        // Create the DistrictList with an existing ID
        districtList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictListMockMvc.perform(post("/api/district-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtList)))
            .andExpect(status().isBadRequest());

        // Validate the DistrictList in the database
        List<DistrictList> districtListList = districtListRepository.findAll();
        assertThat(districtListList).hasSize(databaseSizeBeforeCreate);

        // Validate the DistrictList in Elasticsearch
        verify(mockDistrictListSearchRepository, times(0)).save(districtList);
    }


    @Test
    @Transactional
    public void getAllDistrictLists() throws Exception {
        // Initialize the database
        districtListRepository.saveAndFlush(districtList);

        // Get all the districtListList
        restDistrictListMockMvc.perform(get("/api/district-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districtList.getId().intValue())))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID)))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDistrictList() throws Exception {
        // Initialize the database
        districtListRepository.saveAndFlush(districtList);

        // Get the districtList
        restDistrictListMockMvc.perform(get("/api/district-lists/{id}", districtList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(districtList.getId().intValue()))
            .andExpect(jsonPath("$.districtId").value(DEFAULT_DISTRICT_ID))
            .andExpect(jsonPath("$.districtName").value(DEFAULT_DISTRICT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDistrictList() throws Exception {
        // Get the districtList
        restDistrictListMockMvc.perform(get("/api/district-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistrictList() throws Exception {
        // Initialize the database
        districtListRepository.saveAndFlush(districtList);

        int databaseSizeBeforeUpdate = districtListRepository.findAll().size();

        // Update the districtList
        DistrictList updatedDistrictList = districtListRepository.findById(districtList.getId()).get();
        // Disconnect from session so that the updates on updatedDistrictList are not directly saved in db
        em.detach(updatedDistrictList);
        updatedDistrictList
            .districtId(UPDATED_DISTRICT_ID)
            .districtName(UPDATED_DISTRICT_NAME);

        restDistrictListMockMvc.perform(put("/api/district-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDistrictList)))
            .andExpect(status().isOk());

        // Validate the DistrictList in the database
        List<DistrictList> districtListList = districtListRepository.findAll();
        assertThat(districtListList).hasSize(databaseSizeBeforeUpdate);
        DistrictList testDistrictList = districtListList.get(districtListList.size() - 1);
        assertThat(testDistrictList.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testDistrictList.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);

        // Validate the DistrictList in Elasticsearch
        verify(mockDistrictListSearchRepository, times(1)).save(testDistrictList);
    }

    @Test
    @Transactional
    public void updateNonExistingDistrictList() throws Exception {
        int databaseSizeBeforeUpdate = districtListRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictListMockMvc.perform(put("/api/district-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtList)))
            .andExpect(status().isBadRequest());

        // Validate the DistrictList in the database
        List<DistrictList> districtListList = districtListRepository.findAll();
        assertThat(districtListList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DistrictList in Elasticsearch
        verify(mockDistrictListSearchRepository, times(0)).save(districtList);
    }

    @Test
    @Transactional
    public void deleteDistrictList() throws Exception {
        // Initialize the database
        districtListRepository.saveAndFlush(districtList);

        int databaseSizeBeforeDelete = districtListRepository.findAll().size();

        // Delete the districtList
        restDistrictListMockMvc.perform(delete("/api/district-lists/{id}", districtList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DistrictList> districtListList = districtListRepository.findAll();
        assertThat(districtListList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DistrictList in Elasticsearch
        verify(mockDistrictListSearchRepository, times(1)).deleteById(districtList.getId());
    }

    @Test
    @Transactional
    public void searchDistrictList() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        districtListRepository.saveAndFlush(districtList);
        when(mockDistrictListSearchRepository.search(queryStringQuery("id:" + districtList.getId())))
            .thenReturn(Collections.singletonList(districtList));

        // Search the districtList
        restDistrictListMockMvc.perform(get("/api/_search/district-lists?query=id:" + districtList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districtList.getId().intValue())))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID)))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)));
    }
}
