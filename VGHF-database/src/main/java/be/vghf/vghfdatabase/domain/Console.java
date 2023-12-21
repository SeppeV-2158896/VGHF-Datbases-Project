package be.vghf.vghfdatabase.domain;

import be.vghf.vghfdatabase.Enums.ConsoleType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Console {

    @Column
    @Id
    @GeneratedValue
    private int columnID;

    @Column
    private String consoleName;

    @Column
    @Enumerated(EnumType.STRING)
    private ConsoleType consoleType;

    @Column
    private String company;

    @Column
    private Date releaseYear;

    @Column
    private Date discontinuationYear;

    @Column
    private double unitsSoldInMillions;

    @Column
    private String remarks;

    public int getColumnID() {
        return columnID;
    }

    public void setColumnID(int columnID) {
        this.columnID = columnID;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Date getDiscontinuationYear() {
        return discontinuationYear;
    }

    public void setDiscontinuationYear(Date discontinuationYear) {
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
}
