package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Debtor.
 */
@Entity
@Table(name = "debtor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "debtor")
public class Debtor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private AccountScheme debtorAccount;

    @OneToOne
    @JoinColumn(unique = true)
    private Wallet debtWalletId;

    @OneToOne
    @JoinColumn(unique = true)
    private Amount debtAmount;

    @OneToOne
    @JoinColumn(unique = true)
    private Customer debtCustomer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountScheme getDebtorAccount() {
        return debtorAccount;
    }

    public Debtor debtorAccount(AccountScheme accountScheme) {
        this.debtorAccount = accountScheme;
        return this;
    }

    public void setDebtorAccount(AccountScheme accountScheme) {
        this.debtorAccount = accountScheme;
    }

    public Wallet getDebtWalletId() {
        return debtWalletId;
    }

    public Debtor debtWalletId(Wallet wallet) {
        this.debtWalletId = wallet;
        return this;
    }

    public void setDebtWalletId(Wallet wallet) {
        this.debtWalletId = wallet;
    }

    public Amount getDebtAmount() {
        return debtAmount;
    }

    public Debtor debtAmount(Amount amount) {
        this.debtAmount = amount;
        return this;
    }

    public void setDebtAmount(Amount amount) {
        this.debtAmount = amount;
    }

    public Customer getDebtCustomer() {
        return debtCustomer;
    }

    public Debtor debtCustomer(Customer customer) {
        this.debtCustomer = customer;
        return this;
    }

    public void setDebtCustomer(Customer customer) {
        this.debtCustomer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Debtor)) {
            return false;
        }
        return id != null && id.equals(((Debtor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Debtor{" +
            "id=" + getId() +
            "}";
    }
}
