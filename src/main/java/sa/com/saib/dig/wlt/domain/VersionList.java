package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A VersionList.
 */
@Entity
@Table(name = "version_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "versionlist")
public class VersionList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "a_pi_record")
    private String aPIRecord;

    @Column(name = "version_number")
    private String versionNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getaPIRecord() {
        return aPIRecord;
    }

    public VersionList aPIRecord(String aPIRecord) {
        this.aPIRecord = aPIRecord;
        return this;
    }

    public void setaPIRecord(String aPIRecord) {
        this.aPIRecord = aPIRecord;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public VersionList versionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
        return this;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VersionList)) {
            return false;
        }
        return id != null && id.equals(((VersionList) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VersionList{" +
            "id=" + getId() +
            ", aPIRecord='" + getaPIRecord() + "'" +
            ", versionNumber='" + getVersionNumber() + "'" +
            "}";
    }
}
