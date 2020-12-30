package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A CalculationInfo.
 */
@Entity
@Table(name = "calculation_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "calculationinfo")
public class CalculationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "beneficiary_id")
    private Integer beneficiaryId;

    @Column(name = "from_currency")
    private String fromCurrency;

    @Column(name = "to_currency")
    private String toCurrency;

    @Column(name = "transaction_amount")
    private Float transactionAmount;

    @Column(name = "transaction_currency")
    private String transactionCurrency;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public CalculationInfo customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getBeneficiaryId() {
        return beneficiaryId;
    }

    public CalculationInfo beneficiaryId(Integer beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
        return this;
    }

    public void setBeneficiaryId(Integer beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public CalculationInfo fromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
        return this;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public CalculationInfo toCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
        return this;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public CalculationInfo transactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public CalculationInfo transactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
        return this;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalculationInfo)) {
            return false;
        }
        return id != null && id.equals(((CalculationInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalculationInfo{" +
            "id=" + getId() +
            ", customerId='" + getCustomerId() + "'" +
            ", beneficiaryId=" + getBeneficiaryId() +
            ", fromCurrency='" + getFromCurrency() + "'" +
            ", toCurrency='" + getToCurrency() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", transactionCurrency='" + getTransactionCurrency() + "'" +
            "}";
    }
}
