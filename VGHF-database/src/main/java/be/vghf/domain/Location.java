package be.vghf.domain;

import be.vghf.enums.LocationType;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "locations")
public class Location {
    @Column(name = "locationID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationID;

    @ManyToOne
    @JoinColumn(name = "ownerID", nullable = false)
    private User owner;

    @Column(name = "streetname")
    private String streetName;

    @Column(name = "houseNumber")
    private int houseNumber;

    @Column(name = "bus")
    private String bus;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column
    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "bridge_locations_volunteers",
            joinColumns = { @JoinColumn(name = "locationID") },
            inverseJoinColumns = { @JoinColumn(name = "volunteerID") }
    )
    Set<User> volunteers = new HashSet<>();

    @OneToMany(mappedBy = "currentLocation")
    private Set<Game> currentGames = new HashSet<>();

    @OneToMany(mappedBy = "homeBase")
    private Set<Game> ownedGames = new HashSet<>();

    public int getLocationID() {
        return locationID;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Set<User> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(Set<User> volunteers) {
        this.volunteers = volunteers;
    }

    public Set<Game> getCurrentGames() {
        return currentGames;
    }

    public void setCurrentGames(Set<Game> currentGames) {
        this.currentGames = currentGames;
    }

    public Set<Game> getOwnedGames() {
        return ownedGames;
    }

    public void setOwnedGames(Set<Game> ownedGames) {
        this.ownedGames = ownedGames;
    }

    @Override
    public String toString() {
        if(bus != null){
            return streetName + ' ' + houseNumber + ' ' + bus + ", " + postalCode + ' ' + city + ", " + country;
        }
        else{
            return streetName + ' ' + houseNumber + ", " + postalCode + ' ' + city + ", " + country;
        }
    }
}
