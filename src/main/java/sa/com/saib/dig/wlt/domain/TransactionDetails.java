package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A TransactionDetails.
 */
@Entity
@Table(name = "transaction_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transactiondetails")
public class TransactionDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "debit_amount")
    private String debitAmount;

    @Column(name = "debit_currency")
    private String debitCurrency;

    @Column(name = "credit_amount")
    private String creditAmount;

    @Column(name = "credit_currency")
    private String creditCurrency;

    @Column(name = "exchange_rate")
    private String exchangeRate;

    @Column(name = "fees")
    private String fees;

    @Column(name = "purpose_of_transfer")
    private String purposeOfTransfer;

    @Column(name = "partner_reference_number")
    private String partnerReferenceNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public TransactionDetails debitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
        return this;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }

    public String getDebitCurrency() {
        return debitCurrency;
    }

    public TransactionDetails debitCurrency(String debitCurrency) {
        this.debitCurrency = debitCurrency;
        return this;
    }

    public void setDebitCurrency(String debitCurrency) {
        this.debitCurrency = debitCurrency;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public TransactionDetails creditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
        return this;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getCreditCurrency() {
        return creditCurrency;
    }

    public TransactionDetails creditCurrency(String creditCurrency) {
        this.creditCurrency = creditCurrency;
        return this;
    }

    public void setCreditCurrency(String creditCurrency) {
        this.creditCurrency = creditCurrency;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public TransactionDetails exchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getFees() {
        return fees;
    }

    public TransactionDetails fees(String fees) {
        this.fees = fees;
        return this;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getPurposeOfTransfer() {
        return purposeOfTransfer;
    }

    public TransactionDetails purposeOfTransfer(String purposeOfTransfer) {
        this.purposeOfTransfer = purposeOfTransfer;
        return this;
    }

    public void setPurposeOfTransfer(String purposeOfTransfer) {
        this.purposeOfTransfer = purposeOfTransfer;
    }

    public String getPartnerReferenceNumber() {
        return partnerReferenceNumber;
    }

    public TransactionDetails partnerReferenceNumber(String partnerReferenceNumber) {
        this.partnerReferenceNumber = partnerReferenceNumber;
        return this;
    }

    public void setPartnerReferenceNumber(String partnerReferenceNumber) {
        this.partnerReferenceNumber = partnerReferenceNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDetails)) {
            return false;
        }
        return id != null && id.equals(((TransactionDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDetails{" +
            "id=" + getId() +
            ", debitAmount='" + getDebitAmount() + "'" +
            ", debitCurrency='" + getDebitCurrency() + "'" +
            ", creditAmount='" + getCreditAmount() + "'" +
            ", creditCurrency='" + getCreditCurrency() + "'" +
            ", exchangeRate='" + getExchangeRate() + "'" +
            ", fees='" + getFees() + "'" +
            ", purposeOfTransfer='" + getPurposeOfTransfer() + "'" +
            ", partnerReferenceNumber='" + getPartnerReferenceNumber() + "'" +
            "}";
    }
}
