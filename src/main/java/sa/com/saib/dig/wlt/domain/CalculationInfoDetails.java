package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A CalculationInfoDetails.
 */
@Entity
@Table(name = "calculation_info_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "calculationinfodetails")
public class CalculationInfoDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total_debit_amount")
    private String totalDebitAmount;

    @Column(name = "destination_amount")
    private String destinationAmount;

    @Column(name = "destination_exchange_rate")
    private String destinationExchangeRate;

    @Column(name = "destination_currency_indicator")
    private String destinationCurrencyIndicator;

    @Column(name = "discount_amount")
    private String discountAmount;

    @OneToOne
    @JoinColumn(unique = true)
    private TransactionInfo transCalculation;

    @OneToOne
    @JoinColumn(unique = true)
    private ChargeAmount chargeAmount;

    @OneToOne
    @JoinColumn(unique = true)
    private DestinationChargeAmount destChargeAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTotalDebitAmount() {
        return totalDebitAmount;
    }

    public CalculationInfoDetails totalDebitAmount(String totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
        return this;
    }

    public void setTotalDebitAmount(String totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
    }

    public String getDestinationAmount() {
        return destinationAmount;
    }

    public CalculationInfoDetails destinationAmount(String destinationAmount) {
        this.destinationAmount = destinationAmount;
        return this;
    }

    public void setDestinationAmount(String destinationAmount) {
        this.destinationAmount = destinationAmount;
    }

    public String getDestinationExchangeRate() {
        return destinationExchangeRate;
    }

    public CalculationInfoDetails destinationExchangeRate(String destinationExchangeRate) {
        this.destinationExchangeRate = destinationExchangeRate;
        return this;
    }

    public void setDestinationExchangeRate(String destinationExchangeRate) {
        this.destinationExchangeRate = destinationExchangeRate;
    }

    public String getDestinationCurrencyIndicator() {
        return destinationCurrencyIndicator;
    }

    public CalculationInfoDetails destinationCurrencyIndicator(String destinationCurrencyIndicator) {
        this.destinationCurrencyIndicator = destinationCurrencyIndicator;
        return this;
    }

    public void setDestinationCurrencyIndicator(String destinationCurrencyIndicator) {
        this.destinationCurrencyIndicator = destinationCurrencyIndicator;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public CalculationInfoDetails discountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public TransactionInfo getTransCalculation() {
        return transCalculation;
    }

    public CalculationInfoDetails transCalculation(TransactionInfo transactionInfo) {
        this.transCalculation = transactionInfo;
        return this;
    }

    public void setTransCalculation(TransactionInfo transactionInfo) {
        this.transCalculation = transactionInfo;
    }

    public ChargeAmount getChargeAmount() {
        return chargeAmount;
    }

    public CalculationInfoDetails chargeAmount(ChargeAmount chargeAmount) {
        this.chargeAmount = chargeAmount;
        return this;
    }

    public void setChargeAmount(ChargeAmount chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public DestinationChargeAmount getDestChargeAmount() {
        return destChargeAmount;
    }

    public CalculationInfoDetails destChargeAmount(DestinationChargeAmount destinationChargeAmount) {
        this.destChargeAmount = destinationChargeAmount;
        return this;
    }

    public void setDestChargeAmount(DestinationChargeAmount destinationChargeAmount) {
        this.destChargeAmount = destinationChargeAmount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalculationInfoDetails)) {
            return false;
        }
        return id != null && id.equals(((CalculationInfoDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalculationInfoDetails{" +
            "id=" + getId() +
            ", totalDebitAmount='" + getTotalDebitAmount() + "'" +
            ", destinationAmount='" + getDestinationAmount() + "'" +
            ", destinationExchangeRate='" + getDestinationExchangeRate() + "'" +
            ", destinationCurrencyIndicator='" + getDestinationCurrencyIndicator() + "'" +
            ", discountAmount='" + getDiscountAmount() + "'" +
            "}";
    }
}
