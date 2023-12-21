package be.vghf.vghfdatabase.domain;

import javafx.scene.chart.PieChart;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Game {
    @Column
    @Id
    @GeneratedValue
    private int gameID;

    @Column
    private int ownerID;

    @Column
    private int homeBaseID;

    @Column
    private int currentLocationID;

    @Column
    private String title;

    @Column
    private Date releaseDate;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getHomeBaseID() {
        return homeBaseID;
    }

    public void setHomeBaseID(int homeBaseID) {
        this.homeBaseID = homeBaseID;
    }

    public int getCurrentLocationID() {
        return currentLocationID;
    }

    public void setCurrentLocationID(int currentLocationID) {
        this.currentLocationID = currentLocationID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
