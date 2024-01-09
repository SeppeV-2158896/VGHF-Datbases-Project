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

    @ManyToOne
    @JoinColumn(name = "gameID", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private User customer;

    @Column(name = "loanedDate")
    private String loanedDate;

    @Column (name = "loanTermInDays")
    private int loanTerm;

    @Column (name = "returnDate")
    private String returnDate;

    @Column (name = "fine")
    private String fine;

    public int getReferenceID() {
        return referenceID;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getLoanedDate() {
        return loanedDate;
    }

    public void setLoanedDate(String loanedDate) {
        this.loanedDate = loanedDate;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
