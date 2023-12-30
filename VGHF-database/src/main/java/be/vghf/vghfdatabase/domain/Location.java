package be.vghf.vghfdatabase.domain;

import be.vghf.vghfdatabase.enums.LocationType;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "locations")
public class Location {
    @Column(name = "locationID")
    @Id
    @GeneratedValue
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


}
