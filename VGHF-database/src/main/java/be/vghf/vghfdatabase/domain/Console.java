package be.vghf.vghfdatabase.domain;

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
    private String consoleType;

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

    public String getConsoleType() {
        return consoleType;
    }

    public void setConsoleType(String consoleType) {
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
