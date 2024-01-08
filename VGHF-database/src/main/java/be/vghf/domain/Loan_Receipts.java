package be.vghf.domain;

import be.vghf.enums.State;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "loan_receips")
public class Loan_Receipts {
    @Column(name = "referenceID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int referenceID;

    @ManyToMany
    Set<Game> games = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private User customer;

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

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
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
