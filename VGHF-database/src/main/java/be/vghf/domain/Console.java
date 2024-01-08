package be.vghf.domain;

import be.vghf.enums.ConsoleType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "consoles")
public class Console implements Serializable{

    @Column (name = "consoleID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consoleID;

    @Column (name = "consoleName")
    private String consoleName;

    @Column
    @Enumerated(EnumType.STRING)
    private ConsoleType consoleType;

    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private Dev_company company;

    @Column (name = "releasedYear")
    private String releaseYear;

    @Column (name = "discontinuationYear")
    private String discontinuationYear;

    @Column (name = "unitsSoldMillion")
    private double unitsSoldInMillions;

    @Column (name = "remarks")
    private String remarks;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "bridge_games_consoles",
            joinColumns = { @JoinColumn(name = "consoleID") },
            inverseJoinColumns = { @JoinColumn(name = "gameID") }
    )
    //@ManyToMany(mappedBy = "consoles")
    private Set<Game> games = new HashSet<>();

    public int getColumnID() {
        return consoleID;
    }

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    public ConsoleType getConsoleType() {
        return consoleType;
    }

    public void setConsoleType(ConsoleType consoleType) {
        this.consoleType = consoleType;
    }

    public Dev_company getCompany() {
        return company;
    }

    public void setCompany(Dev_company company) {
        this.company = company;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDiscontinuationYear() {
        return discontinuationYear;
    }

    public void setDiscontinuationYear(String discontinuationYear) {
        this.discontinuationYear = discontinuationYear;
    }

    public double getUnitsSoldInMillions() {
        return unitsSoldInMillions;
    }

    public void setUnitsSoldInMillions(double unitsSoldInMillions) {
        this.unitsSoldInMillions = unitsSoldInMillions;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Set<Game> getGames(){
        return games;
    }
}
