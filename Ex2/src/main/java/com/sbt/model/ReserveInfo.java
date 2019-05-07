package com.sbt.model;

import com.sbt.model.entity.User;

import java.time.LocalDate;

public class ReserveInfo {
    private LocalDate date;
    private String timeFrom;
    private String timeTo;
    private User user;

    private ActionReserve action = new ActionReserve();

    public ReserveInfo(LocalDate date, String timeFrom, String timeTo, User user) {
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.user = user;
    }

    public ReserveInfo(LocalDate date, String timeFrom, String timeTo) {
        this(date, timeFrom, timeTo, null);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActionReserve getAction() {
        return action;
    }

    public void setAction(ActionReserve action) {
        this.action = action;
    }
}
