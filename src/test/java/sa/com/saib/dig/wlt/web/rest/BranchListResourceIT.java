package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.BranchList;
import sa.com.saib.dig.wlt.repository.BranchListRepository;
import sa.com.saib.dig.wlt.repository.search.BranchListSearchRepository;

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
 * Integration tests for the {@link BranchListResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BranchListResourceIT {

    private static final String DEFAULT_BRANCH_ID = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    @Autowired
    private BranchListRepository branchListRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.BranchListSearchRepositoryMockConfiguration
     */
    @Autowired
    private BranchListSearchRepository mockBranchListSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBranchListMockMvc;

    private BranchList branchList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BranchList createEntity(EntityManager em) {
        BranchList branchList = new BranchList()
            .branchId(DEFAULT_BRANCH_ID)
            .branchName(DEFAULT_BRANCH_NAME);
        return branchList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BranchList createUpdatedEntity(EntityManager em) {
        BranchList branchList = new BranchList()
            .branchId(UPDATED_BRANCH_ID)
            .branchName(UPDATED_BRANCH_NAME);
        return branchList;
    }

    @BeforeEach
    public void initTest() {
        branchList = createEntity(em);
    }

    @Test
    @Transactional
    public void createBranchList() throws Exception {
        int databaseSizeBeforeCreate = branchListRepository.findAll().size();
        // Create the BranchList
        restBranchListMockMvc.perform(post("/api/branch-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchList)))
            .andExpect(status().isCreated());

        // Validate the BranchList in the database
        List<BranchList> branchListList = branchListRepository.findAll();
        assertThat(branchListList).hasSize(databaseSizeBeforeCreate + 1);
        BranchList testBranchList = branchListList.get(branchListList.size() - 1);
        assertThat(testBranchList.getBranchId()).isEqualTo(DEFAULT_BRANCH_ID);
        assertThat(testBranchList.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);

        // Validate the BranchList in Elasticsearch
        verify(mockBranchListSearchRepository, times(1)).save(testBranchList);
    }

    @Test
    @Transactional
    public void createBranchListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = branchListRepository.findAll().size();

        // Create the BranchList with an existing ID
        branchList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchListMockMvc.perform(post("/api/branch-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchList)))
            .andExpect(status().isBadRequest());

        // Validate the BranchList in the database
        List<BranchList> branchListList = branchListRepository.findAll();
        assertThat(branchListList).hasSize(databaseSizeBeforeCreate);

        // Validate the BranchList in Elasticsearch
        verify(mockBranchListSearchRepository, times(0)).save(branchList);
    }


    @Test
    @Transactional
    public void getAllBranchLists() throws Exception {
        // Initialize the database
        branchListRepository.saveAndFlush(branchList);

        // Get all the branchListList
        restBranchListMockMvc.perform(get("/api/branch-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branchList.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)));
    }
    
    @Test
    @Transactional
    public void getBranchList() throws Exception {
        // Initialize the database
        branchListRepository.saveAndFlush(branchList);

        // Get the branchList
        restBranchListMockMvc.perform(get("/api/branch-lists/{id}", branchList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(branchList.getId().intValue()))
            .andExpect(jsonPath("$.branchId").value(DEFAULT_BRANCH_ID))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingBranchList() throws Exception {
        // Get the branchList
        restBranchListMockMvc.perform(get("/api/branch-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBranchList() throws Exception {
        // Initialize the database
        branchListRepository.saveAndFlush(branchList);

        int databaseSizeBeforeUpdate = branchListRepository.findAll().size();

        // Update the branchList
        BranchList updatedBranchList = branchListRepository.findById(branchList.getId()).get();
        // Disconnect from session so that the updates on updatedBranchList are not directly saved in db
        em.detach(updatedBranchList);
        updatedBranchList
            .branchId(UPDATED_BRANCH_ID)
            .branchName(UPDATED_BRANCH_NAME);

        restBranchListMockMvc.perform(put("/api/branch-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBranchList)))
            .andExpect(status().isOk());

        // Validate the BranchList in the database
        List<BranchList> branchListList = branchListRepository.findAll();
        assertThat(branchListList).hasSize(databaseSizeBeforeUpdate);
        BranchList testBranchList = branchListList.get(branchListList.size() - 1);
        assertThat(testBranchList.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testBranchList.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);

        // Validate the BranchList in Elasticsearch
        verify(mockBranchListSearchRepository, times(1)).save(testBranchList);
    }

    @Test
    @Transactional
    public void updateNonExistingBranchList() throws Exception {
        int databaseSizeBeforeUpdate = branchListRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchListMockMvc.perform(put("/api/branch-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchList)))
            .andExpect(status().isBadRequest());

        // Validate the BranchList in the database
        List<BranchList> branchListList = branchListRepository.findAll();
        assertThat(branchListList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BranchList in Elasticsearch
        verify(mockBranchListSearchRepository, times(0)).save(branchList);
    }

    @Test
    @Transactional
    public void deleteBranchList() throws Exception {
        // Initialize the database
        branchListRepository.saveAndFlush(branchList);

        int databaseSizeBeforeDelete = branchListRepository.findAll().size();

        // Delete the branchList
        restBranchListMockMvc.perform(delete("/api/branch-lists/{id}", branchList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BranchList> branchListList = branchListRepository.findAll();
        assertThat(branchListList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BranchList in Elasticsearch
        verify(mockBranchListSearchRepository, times(1)).deleteById(branchList.getId());
    }

    @Test
    @Transactional
    public void searchBranchList() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        branchListRepository.saveAndFlush(branchList);
        when(mockBranchListSearchRepository.search(queryStringQuery("id:" + branchList.getId())))
            .thenReturn(Collections.singletonList(branchList));

        // Search the branchList
        restBranchListMockMvc.perform(get("/api/_search/branch-lists?query=id:" + branchList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branchList.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)));
    }
}
