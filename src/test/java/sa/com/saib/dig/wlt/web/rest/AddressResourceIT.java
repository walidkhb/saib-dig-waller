package sa.com.saib.dig.wlt.web.rest;

import sa.com.saib.dig.wlt.SaibDigitalWalletApp;
import sa.com.saib.dig.wlt.domain.Address;
import sa.com.saib.dig.wlt.repository.AddressRepository;
import sa.com.saib.dig.wlt.repository.search.AddressSearchRepository;

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
 * Integration tests for the {@link AddressResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AddressResourceIT {

    private static final String DEFAULT_BUILDING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NEIGHBORHOOD = "AAAAAAAAAA";
    private static final String UPDATED_NEIGHBORHOOD = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_REGION_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private AddressRepository addressRepository;

    /**
     * This repository is mocked in the sa.com.saib.dig.wlt.repository.search test package.
     *
     * @see sa.com.saib.dig.wlt.repository.search.AddressSearchRepositoryMockConfiguration
     */
    @Autowired
    private AddressSearchRepository mockAddressSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .buildingNumber(DEFAULT_BUILDING_NUMBER)
            .streetName(DEFAULT_STREET_NAME)
            .neighborhood(DEFAULT_NEIGHBORHOOD)
            .cityName(DEFAULT_CITY_NAME)
            .zipCode(DEFAULT_ZIP_CODE)
            .additionalNumber(DEFAULT_ADDITIONAL_NUMBER)
            .regionDescription(DEFAULT_REGION_DESCRIPTION)
            .unitNumber(DEFAULT_UNIT_NUMBER);
        return address;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .buildingNumber(UPDATED_BUILDING_NUMBER)
            .streetName(UPDATED_STREET_NAME)
            .neighborhood(UPDATED_NEIGHBORHOOD)
            .cityName(UPDATED_CITY_NAME)
            .zipCode(UPDATED_ZIP_CODE)
            .additionalNumber(UPDATED_ADDITIONAL_NUMBER)
            .regionDescription(UPDATED_REGION_DESCRIPTION)
            .unitNumber(UPDATED_UNIT_NUMBER);
        return address;
    }

    @BeforeEach
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        // Create the Address
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(address)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getBuildingNumber()).isEqualTo(DEFAULT_BUILDING_NUMBER);
        assertThat(testAddress.getStreetName()).isEqualTo(DEFAULT_STREET_NAME);
        assertThat(testAddress.getNeighborhood()).isEqualTo(DEFAULT_NEIGHBORHOOD);
        assertThat(testAddress.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testAddress.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testAddress.getAdditionalNumber()).isEqualTo(DEFAULT_ADDITIONAL_NUMBER);
        assertThat(testAddress.getRegionDescription()).isEqualTo(DEFAULT_REGION_DESCRIPTION);
        assertThat(testAddress.getUnitNumber()).isEqualTo(DEFAULT_UNIT_NUMBER);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(1)).save(testAddress);
    }

    @Test
    @Transactional
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address with an existing ID
        address.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(address)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(0)).save(address);
    }


    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].buildingNumber").value(hasItem(DEFAULT_BUILDING_NUMBER)))
            .andExpect(jsonPath("$.[*].streetName").value(hasItem(DEFAULT_STREET_NAME)))
            .andExpect(jsonPath("$.[*].neighborhood").value(hasItem(DEFAULT_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].additionalNumber").value(hasItem(DEFAULT_ADDITIONAL_NUMBER)))
            .andExpect(jsonPath("$.[*].regionDescription").value(hasItem(DEFAULT_REGION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].unitNumber").value(hasItem(DEFAULT_UNIT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.buildingNumber").value(DEFAULT_BUILDING_NUMBER))
            .andExpect(jsonPath("$.streetName").value(DEFAULT_STREET_NAME))
            .andExpect(jsonPath("$.neighborhood").value(DEFAULT_NEIGHBORHOOD))
            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.additionalNumber").value(DEFAULT_ADDITIONAL_NUMBER))
            .andExpect(jsonPath("$.regionDescription").value(DEFAULT_REGION_DESCRIPTION))
            .andExpect(jsonPath("$.unitNumber").value(DEFAULT_UNIT_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .buildingNumber(UPDATED_BUILDING_NUMBER)
            .streetName(UPDATED_STREET_NAME)
            .neighborhood(UPDATED_NEIGHBORHOOD)
            .cityName(UPDATED_CITY_NAME)
            .zipCode(UPDATED_ZIP_CODE)
            .additionalNumber(UPDATED_ADDITIONAL_NUMBER)
            .regionDescription(UPDATED_REGION_DESCRIPTION)
            .unitNumber(UPDATED_UNIT_NUMBER);

        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAddress)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getBuildingNumber()).isEqualTo(UPDATED_BUILDING_NUMBER);
        assertThat(testAddress.getStreetName()).isEqualTo(UPDATED_STREET_NAME);
        assertThat(testAddress.getNeighborhood()).isEqualTo(UPDATED_NEIGHBORHOOD);
        assertThat(testAddress.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testAddress.getAdditionalNumber()).isEqualTo(UPDATED_ADDITIONAL_NUMBER);
        assertThat(testAddress.getRegionDescription()).isEqualTo(UPDATED_REGION_DESCRIPTION);
        assertThat(testAddress.getUnitNumber()).isEqualTo(UPDATED_UNIT_NUMBER);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(1)).save(testAddress);
    }

    @Test
    @Transactional
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(address)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(0)).save(address);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Delete the address
        restAddressMockMvc.perform(delete("/api/addresses/{id}", address.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(1)).deleteById(address.getId());
    }

    @Test
    @Transactional
    public void searchAddress() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        addressRepository.saveAndFlush(address);
        when(mockAddressSearchRepository.search(queryStringQuery("id:" + address.getId())))
            .thenReturn(Collections.singletonList(address));

        // Search the address
        restAddressMockMvc.perform(get("/api/_search/addresses?query=id:" + address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].buildingNumber").value(hasItem(DEFAULT_BUILDING_NUMBER)))
            .andExpect(jsonPath("$.[*].streetName").value(hasItem(DEFAULT_STREET_NAME)))
            .andExpect(jsonPath("$.[*].neighborhood").value(hasItem(DEFAULT_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].additionalNumber").value(hasItem(DEFAULT_ADDITIONAL_NUMBER)))
            .andExpect(jsonPath("$.[*].regionDescription").value(hasItem(DEFAULT_REGION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].unitNumber").value(hasItem(DEFAULT_UNIT_NUMBER)));
    }
}
