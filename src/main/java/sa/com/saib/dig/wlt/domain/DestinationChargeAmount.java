package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A DestinationChargeAmount.
 */
@Entity
@Table(name = "destination_charge_amount")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "destinationchargeamount")
public class DestinationChargeAmount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "v_at_estimated")
    private String vATEstimated;

    @Column(name = "amount_estimated")
    private String amountEstimated;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getvATEstimated() {
        return vATEstimated;
    }

    public DestinationChargeAmount vATEstimated(String vATEstimated) {
        this.vATEstimated = vATEstimated;
        return this;
    }

    public void setvATEstimated(String vATEstimated) {
        this.vATEstimated = vATEstimated;
    }

    public String getAmountEstimated() {
        return amountEstimated;
    }

    public DestinationChargeAmount amountEstimated(String amountEstimated) {
        this.amountEstimated = amountEstimated;
        return this;
    }

    public void setAmountEstimated(String amountEstimated) {
        this.amountEstimated = amountEstimated;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DestinationChargeAmount)) {
            return false;
        }
        return id != null && id.equals(((DestinationChargeAmount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DestinationChargeAmount{" +
            "id=" + getId() +
            ", vATEstimated='" + getvATEstimated() + "'" +
            ", amountEstimated='" + getAmountEstimated() + "'" +
            "}";
    }
}
