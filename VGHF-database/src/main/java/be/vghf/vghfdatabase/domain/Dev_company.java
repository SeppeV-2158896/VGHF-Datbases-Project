package be.vghf.vghfdatabase.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Dev_company {
    @Column
    @Id
    @GeneratedValue
    private int devID;

    @Column
    private String companyName;

    @Column
    private String website;

    @Column
    private String supportEmail;

    @Column
    private Date establishedDate;

    @Column
    private String streetName;

    @Column
    private int houseNumber;

    @Column
    private String bus;

    @Column
    private String postalCode;

    @Column
    private String city;

    @Column
    private String country;

    public int getDevID() {
        return devID;
    }

    public void setDevID(int devID) {
        this.devID = devID;
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

    public Date getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(Date establishedDate) {
        this.establishedDate = establishedDate;
    }

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
