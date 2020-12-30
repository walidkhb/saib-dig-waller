package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A TransactionHistory.
 */
@Entity
@Table(name = "transaction_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transactionhistory")
public class TransactionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_time")
    private String dateTime;

    @Column(name = "t_r_reference_no")
    private String tRReferenceNo;

    @Column(name = "beneficiary_name")
    private String beneficiaryName;

    @Column(name = "pay_mode")
    private String payMode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "pay_out_amount")
    private String payOutAmount;

    @Column(name = "pay_out_currency")
    private String payOutCurrency;

    @Column(name = "exchange_rate")
    private String exchangeRate;

    @Column(name = "pay_in_amount")
    private String payInAmount;

    @Column(name = "pay_in_currency")
    private String payInCurrency;

    @Column(name = "commission")
    private String commission;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "purpose_code")
    private String purposeCode;

    @Column(name = "purpose_of_transfer")
    private String purposeOfTransfer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public TransactionHistory dateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String gettRReferenceNo() {
        return tRReferenceNo;
    }

    public TransactionHistory tRReferenceNo(String tRReferenceNo) {
        this.tRReferenceNo = tRReferenceNo;
        return this;
    }

    public void settRReferenceNo(String tRReferenceNo) {
        this.tRReferenceNo = tRReferenceNo;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public TransactionHistory beneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
        return this;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getPayMode() {
        return payMode;
    }

    public TransactionHistory payMode(String payMode) {
        this.payMode = payMode;
        return this;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getBankName() {
        return bankName;
    }

    public TransactionHistory bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPayOutAmount() {
        return payOutAmount;
    }

    public TransactionHistory payOutAmount(String payOutAmount) {
        this.payOutAmount = payOutAmount;
        return this;
    }

    public void setPayOutAmount(String payOutAmount) {
        this.payOutAmount = payOutAmount;
    }

    public String getPayOutCurrency() {
        return payOutCurrency;
    }

    public TransactionHistory payOutCurrency(String payOutCurrency) {
        this.payOutCurrency = payOutCurrency;
        return this;
    }

    public void setPayOutCurrency(String payOutCurrency) {
        this.payOutCurrency = payOutCurrency;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public TransactionHistory exchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getPayInAmount() {
        return payInAmount;
    }

    public TransactionHistory payInAmount(String payInAmount) {
        this.payInAmount = payInAmount;
        return this;
    }

    public void setPayInAmount(String payInAmount) {
        this.payInAmount = payInAmount;
    }

    public String getPayInCurrency() {
        return payInCurrency;
    }

    public TransactionHistory payInCurrency(String payInCurrency) {
        this.payInCurrency = payInCurrency;
        return this;
    }

    public void setPayInCurrency(String payInCurrency) {
        this.payInCurrency = payInCurrency;
    }

    public String getCommission() {
        return commission;
    }

    public TransactionHistory commission(String commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getStatus() {
        return status;
    }

    public TransactionHistory status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public TransactionHistory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public TransactionHistory purposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
        return this;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getPurposeOfTransfer() {
        return purposeOfTransfer;
    }

    public TransactionHistory purposeOfTransfer(String purposeOfTransfer) {
        this.purposeOfTransfer = purposeOfTransfer;
        return this;
    }

    public void setPurposeOfTransfer(String purposeOfTransfer) {
        this.purposeOfTransfer = purposeOfTransfer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionHistory)) {
            return false;
        }
        return id != null && id.equals(((TransactionHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionHistory{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            ", tRReferenceNo='" + gettRReferenceNo() + "'" +
            ", beneficiaryName='" + getBeneficiaryName() + "'" +
            ", payMode='" + getPayMode() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", payOutAmount='" + getPayOutAmount() + "'" +
            ", payOutCurrency='" + getPayOutCurrency() + "'" +
            ", exchangeRate='" + getExchangeRate() + "'" +
            ", payInAmount='" + getPayInAmount() + "'" +
            ", payInCurrency='" + getPayInCurrency() + "'" +
            ", commission='" + getCommission() + "'" +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", purposeCode='" + getPurposeCode() + "'" +
            ", purposeOfTransfer='" + getPurposeOfTransfer() + "'" +
            "}";
    }
}
