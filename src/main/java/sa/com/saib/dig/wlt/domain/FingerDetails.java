package sa.com.saib.dig.wlt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A FingerDetails.
 */
@Entity
@Table(name = "finger_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fingerdetails")
public class FingerDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "finger_print")
    private String fingerPrint;

    @Column(name = "finger_index")
    private String fingerIndex;

    @ManyToOne
    @JsonIgnoreProperties(value = "fingerDetails", allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public FingerDetails fingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
        return this;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getFingerIndex() {
        return fingerIndex;
    }

    public FingerDetails fingerIndex(String fingerIndex) {
        this.fingerIndex = fingerIndex;
        return this;
    }

    public void setFingerIndex(String fingerIndex) {
        this.fingerIndex = fingerIndex;
    }

    public Customer getCustomer() {
        return customer;
    }

    public FingerDetails customer(Customer customer) {
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
        if (!(o instanceof FingerDetails)) {
            return false;
        }
        return id != null && id.equals(((FingerDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FingerDetails{" +
            "id=" + getId() +
            ", fingerPrint='" + getFingerPrint() + "'" +
            ", fingerIndex='" + getFingerIndex() + "'" +
            "}";
    }
}
