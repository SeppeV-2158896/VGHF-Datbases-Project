package be.vghf.vghfdatabase.domain;

import be.vghf.vghfdatabase.Enums.LocationType;

import javax.persistence.*;
@Entity
public class User {
    @Column
    @Id
    @GeneratedValue
    private int userID;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String streetName;

    @Column
    private int houseNumber;

    @Column
    private String postalCode;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private int telephone;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
