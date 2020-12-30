package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A BeneficiaryBank.
 */
@Entity
@Table(name = "beneficiary_bank")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "beneficiarybank")
public class BeneficiaryBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_country")
    private String bankCountry;

    @Column(name = "bank_branch_code")
    private String bankBranchCode;

    @Column(name = "branch_name_and_address")
    private String branchNameAndAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankCode() {
        return bankCode;
    }

    public BeneficiaryBank bankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public BeneficiaryBank bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    public BeneficiaryBank bankCountry(String bankCountry) {
        this.bankCountry = bankCountry;
        return this;
    }

    public void setBankCountry(String bankCountry) {
        this.bankCountry = bankCountry;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public BeneficiaryBank bankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
        return this;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getBranchNameAndAddress() {
        return branchNameAndAddress;
    }

    public BeneficiaryBank branchNameAndAddress(String branchNameAndAddress) {
        this.branchNameAndAddress = branchNameAndAddress;
        return this;
    }

    public void setBranchNameAndAddress(String branchNameAndAddress) {
        this.branchNameAndAddress = branchNameAndAddress;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BeneficiaryBank)) {
            return false;
        }
        return id != null && id.equals(((BeneficiaryBank) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BeneficiaryBank{" +
            "id=" + getId() +
            ", bankCode='" + getBankCode() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", bankCountry='" + getBankCountry() + "'" +
            ", bankBranchCode='" + getBankBranchCode() + "'" +
            ", branchNameAndAddress='" + getBranchNameAndAddress() + "'" +
            "}";
    }
}
