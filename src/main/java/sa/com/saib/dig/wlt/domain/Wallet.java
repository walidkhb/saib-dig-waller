package sa.com.saib.dig.wlt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Wallet.
 */
@Entity
@Table(name = "wallet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "status")
    private String status;

    @Column(name = "status_update_date_time")
    private String statusUpdateDateTime;

    @Column(name = "currency")
    private String currency;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_sub_type")
    private String accountSubType;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "wallet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AccountScheme> accountSchemes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "wallets", allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletId() {
        return walletId;
    }

    public Wallet walletId(String walletId) {
        this.walletId = walletId;
        return this;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getStatus() {
        return status;
    }

    public Wallet status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusUpdateDateTime() {
        return statusUpdateDateTime;
    }

    public Wallet statusUpdateDateTime(String statusUpdateDateTime) {
        this.statusUpdateDateTime = statusUpdateDateTime;
        return this;
    }

    public void setStatusUpdateDateTime(String statusUpdateDateTime) {
        this.statusUpdateDateTime = statusUpdateDateTime;
    }

    public String getCurrency() {
        return currency;
    }

    public Wallet currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountType() {
        return accountType;
    }

    public Wallet accountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountSubType() {
        return accountSubType;
    }

    public Wallet accountSubType(String accountSubType) {
        this.accountSubType = accountSubType;
        return this;
    }

    public void setAccountSubType(String accountSubType) {
        this.accountSubType = accountSubType;
    }

    public String getDescription() {
        return description;
    }

    public Wallet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AccountScheme> getAccountSchemes() {
        return accountSchemes;
    }

    public Wallet accountSchemes(Set<AccountScheme> accountSchemes) {
        this.accountSchemes = accountSchemes;
        return this;
    }

    public Wallet addAccountScheme(AccountScheme accountScheme) {
        this.accountSchemes.add(accountScheme);
        accountScheme.setWallet(this);
        return this;
    }

    public Wallet removeAccountScheme(AccountScheme accountScheme) {
        this.accountSchemes.remove(accountScheme);
        accountScheme.setWallet(null);
        return this;
    }

    public void setAccountSchemes(Set<AccountScheme> accountSchemes) {
        this.accountSchemes = accountSchemes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Wallet customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wallet)) {
            return false;
        }
        return id != null && id.equals(((Wallet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wallet{" +
            "id=" + getId() +
            ", walletId='" + getWalletId() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusUpdateDateTime='" + getStatusUpdateDateTime() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", accountSubType='" + getAccountSubType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
