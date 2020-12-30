package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Creditor.
 */
@Entity
@Table(name = "creditor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "creditor")
public class Creditor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Beneficiary beneficiary;

    @OneToOne
    @JoinColumn(unique = true)
    private Wallet credWalletId;

    @OneToOne
    @JoinColumn(unique = true)
    private Amount credAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public Creditor beneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
        return this;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Wallet getCredWalletId() {
        return credWalletId;
    }

    public Creditor credWalletId(Wallet wallet) {
        this.credWalletId = wallet;
        return this;
    }

    public void setCredWalletId(Wallet wallet) {
        this.credWalletId = wallet;
    }

    public Amount getCredAmount() {
        return credAmount;
    }

    public Creditor credAmount(Amount amount) {
        this.credAmount = amount;
        return this;
    }

    public void setCredAmount(Amount amount) {
        this.credAmount = amount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Creditor)) {
            return false;
        }
        return id != null && id.equals(((Creditor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Creditor{" +
            "id=" + getId() +
            "}";
    }
}
