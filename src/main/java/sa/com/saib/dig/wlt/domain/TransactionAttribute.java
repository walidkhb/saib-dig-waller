package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A TransactionAttribute.
 */
@Entity
@Table(name = "transaction_attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transactionattribute")
public class TransactionAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "narative_line_1")
    private String narativeLine1;

    @Column(name = "narative_line_2")
    private String narativeLine2;

    @Column(name = "narative_line_3")
    private String narativeLine3;

    @Column(name = "narative_line_4")
    private String narativeLine4;

    @Column(name = "client_ref_number")
    private String clientRefNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNarativeLine1() {
        return narativeLine1;
    }

    public TransactionAttribute narativeLine1(String narativeLine1) {
        this.narativeLine1 = narativeLine1;
        return this;
    }

    public void setNarativeLine1(String narativeLine1) {
        this.narativeLine1 = narativeLine1;
    }

    public String getNarativeLine2() {
        return narativeLine2;
    }

    public TransactionAttribute narativeLine2(String narativeLine2) {
        this.narativeLine2 = narativeLine2;
        return this;
    }

    public void setNarativeLine2(String narativeLine2) {
        this.narativeLine2 = narativeLine2;
    }

    public String getNarativeLine3() {
        return narativeLine3;
    }

    public TransactionAttribute narativeLine3(String narativeLine3) {
        this.narativeLine3 = narativeLine3;
        return this;
    }

    public void setNarativeLine3(String narativeLine3) {
        this.narativeLine3 = narativeLine3;
    }

    public String getNarativeLine4() {
        return narativeLine4;
    }

    public TransactionAttribute narativeLine4(String narativeLine4) {
        this.narativeLine4 = narativeLine4;
        return this;
    }

    public void setNarativeLine4(String narativeLine4) {
        this.narativeLine4 = narativeLine4;
    }

    public String getClientRefNumber() {
        return clientRefNumber;
    }

    public TransactionAttribute clientRefNumber(String clientRefNumber) {
        this.clientRefNumber = clientRefNumber;
        return this;
    }

    public void setClientRefNumber(String clientRefNumber) {
        this.clientRefNumber = clientRefNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionAttribute)) {
            return false;
        }
        return id != null && id.equals(((TransactionAttribute) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionAttribute{" +
            "id=" + getId() +
            ", narativeLine1='" + getNarativeLine1() + "'" +
            ", narativeLine2='" + getNarativeLine2() + "'" +
            ", narativeLine3='" + getNarativeLine3() + "'" +
            ", narativeLine4='" + getNarativeLine4() + "'" +
            ", clientRefNumber='" + getClientRefNumber() + "'" +
            "}";
    }
}
