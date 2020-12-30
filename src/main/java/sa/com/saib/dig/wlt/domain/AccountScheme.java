package sa.com.saib.dig.wlt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A AccountScheme.
 */
@Entity
@Table(name = "account_scheme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "accountscheme")
public class AccountScheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "scheme_name")
    private String schemeName;

    @Column(name = "identification")
    private String identification;

    @ManyToOne
    @JsonIgnoreProperties(value = "accountSchemes", allowSetters = true)
    private Wallet wallet;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public AccountScheme schemeName(String schemeName) {
        this.schemeName = schemeName;
        return this;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getIdentification() {
        return identification;
    }

    public AccountScheme identification(String identification) {
        this.identification = identification;
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public AccountScheme wallet(Wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountScheme)) {
            return false;
        }
        return id != null && id.equals(((AccountScheme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountScheme{" +
            "id=" + getId() +
            ", schemeName='" + getSchemeName() + "'" +
            ", identification='" + getIdentification() + "'" +
            "}";
    }
}
