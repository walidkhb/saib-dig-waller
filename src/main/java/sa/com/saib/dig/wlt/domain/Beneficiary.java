package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Beneficiary.
 */
@Entity
@Table(name = "beneficiary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "beneficiary")
public class Beneficiary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "beneficiary_id")
    private String beneficiaryId;

    @Column(name = "beneficiary_type")
    private String beneficiaryType;

    @Column(name = "address")
    private String address;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "i_d_number")
    private String iDNumber;

    @Column(name = "i_d_type")
    private String iDType;

    @Column(name = "beneficiary_relation")
    private String beneficiaryRelation;

    @Column(name = "beneficiary_city")
    private String beneficiaryCity;

    @Column(name = "beneficiary_phone_country_code")
    private String beneficiaryPhoneCountryCode;

    @Column(name = "beneficiary_source_of_fund")
    private String beneficiarySourceOfFund;

    @Column(name = "beneficiary_zip_code")
    private String beneficiaryZipCode;

    @Column(name = "beneficiary_status")
    private String beneficiaryStatus;

    @Column(name = "beneficiary_currency")
    private String beneficiaryCurrency;

    @OneToOne
    @JoinColumn(unique = true)
    private PaymentDetails beneficiaryDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private BeneficiaryBank beneficiaryBank;

    @OneToOne
    @JoinColumn(unique = true)
    private AccountScheme beneficiaryAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public Beneficiary nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Beneficiary firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Beneficiary lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Beneficiary middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public Beneficiary beneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
        return this;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    public Beneficiary beneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
        return this;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public String getAddress() {
        return address;
    }

    public Beneficiary address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public Beneficiary nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTelephone() {
        return telephone;
    }

    public Beneficiary telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Beneficiary dateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getiDNumber() {
        return iDNumber;
    }

    public Beneficiary iDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
        return this;
    }

    public void setiDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
    }

    public String getiDType() {
        return iDType;
    }

    public Beneficiary iDType(String iDType) {
        this.iDType = iDType;
        return this;
    }

    public void setiDType(String iDType) {
        this.iDType = iDType;
    }

    public String getBeneficiaryRelation() {
        return beneficiaryRelation;
    }

    public Beneficiary beneficiaryRelation(String beneficiaryRelation) {
        this.beneficiaryRelation = beneficiaryRelation;
        return this;
    }

    public void setBeneficiaryRelation(String beneficiaryRelation) {
        this.beneficiaryRelation = beneficiaryRelation;
    }

    public String getBeneficiaryCity() {
        return beneficiaryCity;
    }

    public Beneficiary beneficiaryCity(String beneficiaryCity) {
        this.beneficiaryCity = beneficiaryCity;
        return this;
    }

    public void setBeneficiaryCity(String beneficiaryCity) {
        this.beneficiaryCity = beneficiaryCity;
    }

    public String getBeneficiaryPhoneCountryCode() {
        return beneficiaryPhoneCountryCode;
    }

    public Beneficiary beneficiaryPhoneCountryCode(String beneficiaryPhoneCountryCode) {
        this.beneficiaryPhoneCountryCode = beneficiaryPhoneCountryCode;
        return this;
    }

    public void setBeneficiaryPhoneCountryCode(String beneficiaryPhoneCountryCode) {
        this.beneficiaryPhoneCountryCode = beneficiaryPhoneCountryCode;
    }

    public String getBeneficiarySourceOfFund() {
        return beneficiarySourceOfFund;
    }

    public Beneficiary beneficiarySourceOfFund(String beneficiarySourceOfFund) {
        this.beneficiarySourceOfFund = beneficiarySourceOfFund;
        return this;
    }

    public void setBeneficiarySourceOfFund(String beneficiarySourceOfFund) {
        this.beneficiarySourceOfFund = beneficiarySourceOfFund;
    }

    public String getBeneficiaryZipCode() {
        return beneficiaryZipCode;
    }

    public Beneficiary beneficiaryZipCode(String beneficiaryZipCode) {
        this.beneficiaryZipCode = beneficiaryZipCode;
        return this;
    }

    public void setBeneficiaryZipCode(String beneficiaryZipCode) {
        this.beneficiaryZipCode = beneficiaryZipCode;
    }

    public String getBeneficiaryStatus() {
        return beneficiaryStatus;
    }

    public Beneficiary beneficiaryStatus(String beneficiaryStatus) {
        this.beneficiaryStatus = beneficiaryStatus;
        return this;
    }

    public void setBeneficiaryStatus(String beneficiaryStatus) {
        this.beneficiaryStatus = beneficiaryStatus;
    }

    public String getBeneficiaryCurrency() {
        return beneficiaryCurrency;
    }

    public Beneficiary beneficiaryCurrency(String beneficiaryCurrency) {
        this.beneficiaryCurrency = beneficiaryCurrency;
        return this;
    }

    public void setBeneficiaryCurrency(String beneficiaryCurrency) {
        this.beneficiaryCurrency = beneficiaryCurrency;
    }

    public PaymentDetails getBeneficiaryDetails() {
        return beneficiaryDetails;
    }

    public Beneficiary beneficiaryDetails(PaymentDetails paymentDetails) {
        this.beneficiaryDetails = paymentDetails;
        return this;
    }

    public void setBeneficiaryDetails(PaymentDetails paymentDetails) {
        this.beneficiaryDetails = paymentDetails;
    }

    public BeneficiaryBank getBeneficiaryBank() {
        return beneficiaryBank;
    }

    public Beneficiary beneficiaryBank(BeneficiaryBank beneficiaryBank) {
        this.beneficiaryBank = beneficiaryBank;
        return this;
    }

    public void setBeneficiaryBank(BeneficiaryBank beneficiaryBank) {
        this.beneficiaryBank = beneficiaryBank;
    }

    public AccountScheme getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public Beneficiary beneficiaryAccount(AccountScheme accountScheme) {
        this.beneficiaryAccount = accountScheme;
        return this;
    }

    public void setBeneficiaryAccount(AccountScheme accountScheme) {
        this.beneficiaryAccount = accountScheme;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beneficiary)) {
            return false;
        }
        return id != null && id.equals(((Beneficiary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beneficiary{" +
            "id=" + getId() +
            ", nickName='" + getNickName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", beneficiaryId='" + getBeneficiaryId() + "'" +
            ", beneficiaryType='" + getBeneficiaryType() + "'" +
            ", address='" + getAddress() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", iDNumber='" + getiDNumber() + "'" +
            ", iDType='" + getiDType() + "'" +
            ", beneficiaryRelation='" + getBeneficiaryRelation() + "'" +
            ", beneficiaryCity='" + getBeneficiaryCity() + "'" +
            ", beneficiaryPhoneCountryCode='" + getBeneficiaryPhoneCountryCode() + "'" +
            ", beneficiarySourceOfFund='" + getBeneficiarySourceOfFund() + "'" +
            ", beneficiaryZipCode='" + getBeneficiaryZipCode() + "'" +
            ", beneficiaryStatus='" + getBeneficiaryStatus() + "'" +
            ", beneficiaryCurrency='" + getBeneficiaryCurrency() + "'" +
            "}";
    }
}
