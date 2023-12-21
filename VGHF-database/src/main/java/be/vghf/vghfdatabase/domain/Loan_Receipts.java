package be.vghf.vghfdatabase.domain;

import be.vghf.vghfdatabase.Enums.LocationType;
import be.vghf.vghfdatabase.Enums.State;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Loan_Receipts {
    @Column
    @Id
    @GeneratedValue
    private int referenceID;

    @Column
    private int gameID;

    @Column
    private int userID;

    @Column
    private Date loanedDate;

    @Column
    private Date returnDate;

    @Column
    private double fine;

    @Column
    @Enumerated(EnumType.STRING)
    private State state;

    public int getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(int referenceID) {
        this.referenceID = referenceID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getLoanedDate() {
        return loanedDate;
    }

    public void setLoanedDate(Date loanedDate) {
        this.loanedDate = loanedDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
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
