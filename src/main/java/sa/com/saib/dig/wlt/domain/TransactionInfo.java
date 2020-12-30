package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A TransactionInfo.
 */
@Entity
@Table(name = "transaction_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transactioninfo")
public class TransactionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "credit_debit_indicator")
    private String creditDebitIndicator;

    @Column(name = "creation_date_time")
    private String creationDateTime;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(unique = true)
    private TransactionAttribute transactionAttr;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public TransactionInfo transactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public TransactionInfo transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCreditDebitIndicator() {
        return creditDebitIndicator;
    }

    public TransactionInfo creditDebitIndicator(String creditDebitIndicator) {
        this.creditDebitIndicator = creditDebitIndicator;
        return this;
    }

    public void setCreditDebitIndicator(String creditDebitIndicator) {
        this.creditDebitIndicator = creditDebitIndicator;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public TransactionInfo creationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
        return this;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getStatus() {
        return status;
    }

    public TransactionInfo status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TransactionAttribute getTransactionAttr() {
        return transactionAttr;
    }

    public TransactionInfo transactionAttr(TransactionAttribute transactionAttribute) {
        this.transactionAttr = transactionAttribute;
        return this;
    }

    public void setTransactionAttr(TransactionAttribute transactionAttribute) {
        this.transactionAttr = transactionAttribute;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionInfo)) {
            return false;
        }
        return id != null && id.equals(((TransactionInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionInfo{" +
            "id=" + getId() +
            ", transactionType='" + getTransactionType() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", creditDebitIndicator='" + getCreditDebitIndicator() + "'" +
            ", creationDateTime='" + getCreationDateTime() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
