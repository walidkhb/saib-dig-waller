package sa.com.saib.dig.wlt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name_arabic")
    private String firstNameArabic;

    @Column(name = "father_name_arabic")
    private String fatherNameArabic;

    @Column(name = "grand_father_name_arabic")
    private String grandFatherNameArabic;

    @Column(name = "grand_father_name_english")
    private String grandFatherNameEnglish;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "i_d_issue_date")
    private String iDIssueDate;

    @Column(name = "i_d_expiry_date")
    private String iDExpiryDate;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "profile_status")
    private String profileStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private CustomerDetails customerDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private CustomerPreference customerPreference;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToOne
    @JoinColumn(unique = true)
    private KYCInfo kycInfo;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<FingerDetails> fingerDetails = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Wallet> wallets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstNameArabic() {
        return firstNameArabic;
    }

    public Customer firstNameArabic(String firstNameArabic) {
        this.firstNameArabic = firstNameArabic;
        return this;
    }

    public void setFirstNameArabic(String firstNameArabic) {
        this.firstNameArabic = firstNameArabic;
    }

    public String getFatherNameArabic() {
        return fatherNameArabic;
    }

    public Customer fatherNameArabic(String fatherNameArabic) {
        this.fatherNameArabic = fatherNameArabic;
        return this;
    }

    public void setFatherNameArabic(String fatherNameArabic) {
        this.fatherNameArabic = fatherNameArabic;
    }

    public String getGrandFatherNameArabic() {
        return grandFatherNameArabic;
    }

    public Customer grandFatherNameArabic(String grandFatherNameArabic) {
        this.grandFatherNameArabic = grandFatherNameArabic;
        return this;
    }

    public void setGrandFatherNameArabic(String grandFatherNameArabic) {
        this.grandFatherNameArabic = grandFatherNameArabic;
    }

    public String getGrandFatherNameEnglish() {
        return grandFatherNameEnglish;
    }

    public Customer grandFatherNameEnglish(String grandFatherNameEnglish) {
        this.grandFatherNameEnglish = grandFatherNameEnglish;
        return this;
    }

    public void setGrandFatherNameEnglish(String grandFatherNameEnglish) {
        this.grandFatherNameEnglish = grandFatherNameEnglish;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public Customer placeOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getiDIssueDate() {
        return iDIssueDate;
    }

    public Customer iDIssueDate(String iDIssueDate) {
        this.iDIssueDate = iDIssueDate;
        return this;
    }

    public void setiDIssueDate(String iDIssueDate) {
        this.iDIssueDate = iDIssueDate;
    }

    public String getiDExpiryDate() {
        return iDExpiryDate;
    }

    public Customer iDExpiryDate(String iDExpiryDate) {
        this.iDExpiryDate = iDExpiryDate;
        return this;
    }

    public void setiDExpiryDate(String iDExpiryDate) {
        this.iDExpiryDate = iDExpiryDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public Customer maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Customer customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProfileStatus() {
        return profileStatus;
    }

    public Customer profileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
        return this;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public Customer customerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
        return this;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public CustomerPreference getCustomerPreference() {
        return customerPreference;
    }

    public Customer customerPreference(CustomerPreference customerPreference) {
        this.customerPreference = customerPreference;
        return this;
    }

    public void setCustomerPreference(CustomerPreference customerPreference) {
        this.customerPreference = customerPreference;
    }

    public Address getAddress() {
        return address;
    }

    public Customer address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public KYCInfo getKycInfo() {
        return kycInfo;
    }

    public Customer kycInfo(KYCInfo kYCInfo) {
        this.kycInfo = kYCInfo;
        return this;
    }

    public void setKycInfo(KYCInfo kYCInfo) {
        this.kycInfo = kYCInfo;
    }

    public Set<FingerDetails> getFingerDetails() {
        return fingerDetails;
    }

    public Customer fingerDetails(Set<FingerDetails> fingerDetails) {
        this.fingerDetails = fingerDetails;
        return this;
    }

    public Customer addFingerDetails(FingerDetails fingerDetails) {
        this.fingerDetails.add(fingerDetails);
        fingerDetails.setCustomer(this);
        return this;
    }

    public Customer removeFingerDetails(FingerDetails fingerDetails) {
        this.fingerDetails.remove(fingerDetails);
        fingerDetails.setCustomer(null);
        return this;
    }

    public void setFingerDetails(Set<FingerDetails> fingerDetails) {
        this.fingerDetails = fingerDetails;
    }

    public Set<Wallet> getWallets() {
        return wallets;
    }

    public Customer wallets(Set<Wallet> wallets) {
        this.wallets = wallets;
        return this;
    }

    public Customer addWallet(Wallet wallet) {
        this.wallets.add(wallet);
        wallet.setCustomer(this);
        return this;
    }

    public Customer removeWallet(Wallet wallet) {
        this.wallets.remove(wallet);
        wallet.setCustomer(null);
        return this;
    }

    public void setWallets(Set<Wallet> wallets) {
        this.wallets = wallets;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", firstNameArabic='" + getFirstNameArabic() + "'" +
            ", fatherNameArabic='" + getFatherNameArabic() + "'" +
            ", grandFatherNameArabic='" + getGrandFatherNameArabic() + "'" +
            ", grandFatherNameEnglish='" + getGrandFatherNameEnglish() + "'" +
            ", placeOfBirth='" + getPlaceOfBirth() + "'" +
            ", iDIssueDate='" + getiDIssueDate() + "'" +
            ", iDExpiryDate='" + getiDExpiryDate() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", profileStatus='" + getProfileStatus() + "'" +
            "}";
    }
}
