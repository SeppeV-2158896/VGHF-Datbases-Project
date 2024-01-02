package be.vghf.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "games")
public class Game {
    @Column (name = "gameID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameID;

    @OneToOne
    @JoinColumn(name = "ownerID")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "homeBaseID", nullable = false)
    private Location homeBase;

    @ManyToOne
    @JoinColumn(name = "currentLocationId", nullable = false)
    private Location currentLocation;

    @Column (name = "title")
    private String title;

    @Column (name = "releaseDate")
    private java.sql.Date releaseDate;

    @Column (name = "genre")
    private String genre;

    @ManyToMany(mappedBy = "games")
    private Set<Console> consoles = new HashSet<>();

    @ManyToMany(mappedBy = "games")
    private Set<Dev_company> devCompanies = new HashSet<>();

    @ManyToMany(mappedBy = "games")
    private Set<Loan_Receipts> loanReceipts = new HashSet<>();

    public int getGameID() {
        return gameID;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Location getHomeBase() {
        return homeBase;
    }

    public void setHomeBase(Location homeBase) {
        this.homeBase = homeBase;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public java.sql.Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(java.sql.Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<Console> getConsoles() {
        return consoles;
    }

    public Set<Dev_company> getDevCompanies() {
        return devCompanies;
    }

    public void setConsoles(Set<Console> consoles) {
        this.consoles = consoles;
    }

    public void setDevCompanies(Set<Dev_company> devCompanies) {
        this.devCompanies = devCompanies;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
