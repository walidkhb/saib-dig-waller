package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Transfer.
 */
@Entity
@Table(name = "transfer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transfer")
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Debtor debtDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private Creditor credDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private Beneficiary beneficiaryInfo;

    @OneToOne
    @JoinColumn(unique = true)
    private TransactionInfo transactionInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Debtor getDebtDetails() {
        return debtDetails;
    }

    public Transfer debtDetails(Debtor debtor) {
        this.debtDetails = debtor;
        return this;
    }

    public void setDebtDetails(Debtor debtor) {
        this.debtDetails = debtor;
    }

    public Creditor getCredDetails() {
        return credDetails;
    }

    public Transfer credDetails(Creditor creditor) {
        this.credDetails = creditor;
        return this;
    }

    public void setCredDetails(Creditor creditor) {
        this.credDetails = creditor;
    }

    public Beneficiary getBeneficiaryInfo() {
        return beneficiaryInfo;
    }

    public Transfer beneficiaryInfo(Beneficiary beneficiary) {
        this.beneficiaryInfo = beneficiary;
        return this;
    }

    public void setBeneficiaryInfo(Beneficiary beneficiary) {
        this.beneficiaryInfo = beneficiary;
    }

    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }

    public Transfer transactionInfo(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
        return this;
    }

    public void setTransactionInfo(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transfer)) {
            return false;
        }
        return id != null && id.equals(((Transfer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transfer{" +
            "id=" + getId() +
            "}";
    }
}
