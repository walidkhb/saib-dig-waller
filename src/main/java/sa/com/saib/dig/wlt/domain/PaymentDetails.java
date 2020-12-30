package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A PaymentDetails.
 */
@Entity
@Table(name = "payment_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "paymentdetails")
public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "payout_currency")
    private String payoutCurrency;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "purpose_of_transfer")
    private String purposeOfTransfer;

    @Column(name = "pay_out_country_code")
    private String payOutCountryCode;

    @Column(name = "payment_details")
    private String paymentDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayoutCurrency() {
        return payoutCurrency;
    }

    public PaymentDetails payoutCurrency(String payoutCurrency) {
        this.payoutCurrency = payoutCurrency;
        return this;
    }

    public void setPayoutCurrency(String payoutCurrency) {
        this.payoutCurrency = payoutCurrency;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public PaymentDetails paymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPurposeOfTransfer() {
        return purposeOfTransfer;
    }

    public PaymentDetails purposeOfTransfer(String purposeOfTransfer) {
        this.purposeOfTransfer = purposeOfTransfer;
        return this;
    }

    public void setPurposeOfTransfer(String purposeOfTransfer) {
        this.purposeOfTransfer = purposeOfTransfer;
    }

    public String getPayOutCountryCode() {
        return payOutCountryCode;
    }

    public PaymentDetails payOutCountryCode(String payOutCountryCode) {
        this.payOutCountryCode = payOutCountryCode;
        return this;
    }

    public void setPayOutCountryCode(String payOutCountryCode) {
        this.payOutCountryCode = payOutCountryCode;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public PaymentDetails paymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
        return this;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDetails)) {
            return false;
        }
        return id != null && id.equals(((PaymentDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDetails{" +
            "id=" + getId() +
            ", payoutCurrency='" + getPayoutCurrency() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", purposeOfTransfer='" + getPurposeOfTransfer() + "'" +
            ", payOutCountryCode='" + getPayOutCountryCode() + "'" +
            ", paymentDetails='" + getPaymentDetails() + "'" +
            "}";
    }
}
