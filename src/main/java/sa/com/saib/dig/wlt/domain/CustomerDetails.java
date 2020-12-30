package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A CustomerDetails.
 */
@Entity
@Table(name = "customer_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "customerdetails")
public class CustomerDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "national_identity_number")
    private String nationalIdentityNumber;

    @Column(name = "id_type")
    private String idType;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Pattern(regexp = "[+]\\d+-\\d+")
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    @Column(name = "agent_verification_number")
    private String agentVerificationNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNationalIdentityNumber() {
        return nationalIdentityNumber;
    }

    public CustomerDetails nationalIdentityNumber(String nationalIdentityNumber) {
        this.nationalIdentityNumber = nationalIdentityNumber;
        return this;
    }

    public void setNationalIdentityNumber(String nationalIdentityNumber) {
        this.nationalIdentityNumber = nationalIdentityNumber;
    }

    public String getIdType() {
        return idType;
    }

    public CustomerDetails idType(String idType) {
        this.idType = idType;
        return this;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public CustomerDetails dateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public CustomerDetails mobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
        return this;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getAgentVerificationNumber() {
        return agentVerificationNumber;
    }

    public CustomerDetails agentVerificationNumber(String agentVerificationNumber) {
        this.agentVerificationNumber = agentVerificationNumber;
        return this;
    }

    public void setAgentVerificationNumber(String agentVerificationNumber) {
        this.agentVerificationNumber = agentVerificationNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDetails)) {
            return false;
        }
        return id != null && id.equals(((CustomerDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDetails{" +
            "id=" + getId() +
            ", nationalIdentityNumber='" + getNationalIdentityNumber() + "'" +
            ", idType='" + getIdType() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", mobilePhoneNumber='" + getMobilePhoneNumber() + "'" +
            ", agentVerificationNumber='" + getAgentVerificationNumber() + "'" +
            "}";
    }
}
