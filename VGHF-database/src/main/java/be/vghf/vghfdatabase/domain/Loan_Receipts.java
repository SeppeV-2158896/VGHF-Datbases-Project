package be.vghf.vghfdatabase.domain;

import be.vghf.vghfdatabase.enums.State;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "loan_receipts")
public class Loan_Receipts {
    @Column(name = "referenceID")
    @Id
    @GeneratedValue
    private int referenceID;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "bridge_games_loanreceipts",
            joinColumns = {@JoinColumn(name = "loanID")},
            inverseJoinColumns = {@JoinColumn(name = "gameID")}
    )
    Set<Game> games = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private User userID;

    @Column(name = "loanedDate")
    private Date loanedDate;

    @Column (name = "loanTermInDays")
    private int returnDate;

    @Column (name = "fine")
    private double fine;

    private State state;

    public int getReferenceID() {
        return referenceID;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Date getLoanedDate() {
        return loanedDate;
    }

    public void setLoanedDate(Date loanedDate) {
        this.loanedDate = loanedDate;
    }

    public int getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(int returnDate) {
        this.returnDate = returnDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
