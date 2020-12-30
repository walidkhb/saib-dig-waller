package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "building_number")
    private String buildingNumber;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "additional_number")
    private String additionalNumber;

    @Column(name = "region_description")
    private String regionDescription;

    @Column(name = "unit_number")
    private String unitNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public Address buildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
        return this;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public Address streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public Address neighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCityName() {
        return cityName;
    }

    public Address cityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAdditionalNumber() {
        return additionalNumber;
    }

    public Address additionalNumber(String additionalNumber) {
        this.additionalNumber = additionalNumber;
        return this;
    }

    public void setAdditionalNumber(String additionalNumber) {
        this.additionalNumber = additionalNumber;
    }

    public String getRegionDescription() {
        return regionDescription;
    }

    public Address regionDescription(String regionDescription) {
        this.regionDescription = regionDescription;
        return this;
    }

    public void setRegionDescription(String regionDescription) {
        this.regionDescription = regionDescription;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public Address unitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
        return this;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", buildingNumber='" + getBuildingNumber() + "'" +
            ", streetName='" + getStreetName() + "'" +
            ", neighborhood='" + getNeighborhood() + "'" +
            ", cityName='" + getCityName() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", additionalNumber='" + getAdditionalNumber() + "'" +
            ", regionDescription='" + getRegionDescription() + "'" +
            ", unitNumber='" + getUnitNumber() + "'" +
            "}";
    }
}
