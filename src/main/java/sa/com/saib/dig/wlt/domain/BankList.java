package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A BankList.
 */
@Entity
@Table(name = "bank_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "banklist")
public class BankList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bank_id")
    private String bankId;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch_indicator")
    private String branchIndicator;

    @Column(name = "flag_label")
    private String flagLabel;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankId() {
        return bankId;
    }

    public BankList bankId(String bankId) {
        this.bankId = bankId;
        return this;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public BankList bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchIndicator() {
        return branchIndicator;
    }

    public BankList branchIndicator(String branchIndicator) {
        this.branchIndicator = branchIndicator;
        return this;
    }

    public void setBranchIndicator(String branchIndicator) {
        this.branchIndicator = branchIndicator;
    }

    public String getFlagLabel() {
        return flagLabel;
    }

    public BankList flagLabel(String flagLabel) {
        this.flagLabel = flagLabel;
        return this;
    }

    public void setFlagLabel(String flagLabel) {
        this.flagLabel = flagLabel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankList)) {
            return false;
        }
        return id != null && id.equals(((BankList) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankList{" +
            "id=" + getId() +
            ", bankId='" + getBankId() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", branchIndicator='" + getBranchIndicator() + "'" +
            ", flagLabel='" + getFlagLabel() + "'" +
            "}";
    }
}
