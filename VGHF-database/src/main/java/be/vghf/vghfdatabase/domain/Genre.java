package be.vghf.vghfdatabase.domain;

import javax.persistence.*;
@Entity
public class Genre {
    @Column
    @Id
    @GeneratedValue
    private int genreID;

    @Column
    private String genreName;

    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
