package be.vghf.domain;

import be.vghf.controllers.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    private LocalDate date;

    public Date(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = LocalDate.parse(dateString, formatter);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = LocalDate.parse(dateString, formatter);
    }

    public String getFormattedDate(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return this.date.format(formatter);
    }
}
