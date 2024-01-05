package be.vghf.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "dev_companies")
public class Dev_company implements Serializable {
    @Column (name = "devID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int devID;

    @Column (name = "companyName")
    private String companyName;

    @Column (name = "website")
    private String website;

    @Column (name = "supportEmail")
    private String supportEmail;
    /* Uitgecomment omdat het programma anders niet wil werken door een of andere gekke ParseException??
    @Column (name = "establishedDate")
    private Date establishedDate;
    Ik snap het ook niet waarom... */
    @Column (name = "streetname")
    private String streetName;

    @Column (name = "houseNumber")
    private int houseNumber;

    @Column (name = "bus")
    private String bus;

    @Column (name = "postalCode")
    private String postalCode;

    @Column (name = "city")
    private String city;

    @Column (name = "country")
    private String country;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "bridge_games_devcompanies",
            joinColumns = { @JoinColumn(name = "devCompanyID") },
            inverseJoinColumns = { @JoinColumn(name = "gameID") }
    )
    Set<Game> games = new HashSet<>();

    public int getDevID() {
        return devID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }
    /*
    public Date getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(Date establishedDate) {
        this.establishedDate = establishedDate;
    }
    */
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
