package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Amount.
 */
@Entity
@Table(name = "amount")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "amount")
public class Amount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "net_amount")
    private Float netAmount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "purpose_of_transfer")
    private String purposeOfTransfer;

    @OneToOne
    @JoinColumn(unique = true)
    private ChargeAmount walletChargeAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public Amount amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getNetAmount() {
        return netAmount;
    }

    public Amount netAmount(Float netAmount) {
        this.netAmount = netAmount;
        return this;
    }

    public void setNetAmount(Float netAmount) {
        this.netAmount = netAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public Amount currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPurposeOfTransfer() {
        return purposeOfTransfer;
    }

    public Amount purposeOfTransfer(String purposeOfTransfer) {
        this.purposeOfTransfer = purposeOfTransfer;
        return this;
    }

    public void setPurposeOfTransfer(String purposeOfTransfer) {
        this.purposeOfTransfer = purposeOfTransfer;
    }

    public ChargeAmount getWalletChargeAmount() {
        return walletChargeAmount;
    }

    public Amount walletChargeAmount(ChargeAmount chargeAmount) {
        this.walletChargeAmount = chargeAmount;
        return this;
    }

    public void setWalletChargeAmount(ChargeAmount chargeAmount) {
        this.walletChargeAmount = chargeAmount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Amount)) {
            return false;
        }
        return id != null && id.equals(((Amount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Amount{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", netAmount=" + getNetAmount() +
            ", currency='" + getCurrency() + "'" +
            ", purposeOfTransfer='" + getPurposeOfTransfer() + "'" +
            "}";
    }
}
