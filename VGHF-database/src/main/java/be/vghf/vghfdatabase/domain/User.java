package be.vghf.vghfdatabase.domain;

import be.vghf.vghfdatabase.enums.UserType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "users")
public class User {
    @Column (name = "userID")
    @Id
    @GeneratedValue
    private int userID;

    @Column (name = "firstname")
    private String firstName;

    @Column (name = "lastname")
    private String lastName;

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

    @Column (name = "telephone")
    private int telephone;

    @Column (name = "email")
    private String email;

    @Column (name = "password")
    private String password;

    @ManyToMany(mappedBy = "volunteers")
    private Set<Location> locations = new HashSet<>();
    @Column
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
