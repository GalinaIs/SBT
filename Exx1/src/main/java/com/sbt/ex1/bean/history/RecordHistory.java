package com.sbt.ex1.bean.history;

import java.util.Date;

public class RecordHistory {
    private final Date date;
    private final String filename;
    private final String typeModify;

    public RecordHistory(String filename, String typeModify, Date date) {
        this.date = date;
        this.filename = filename;
        this.typeModify = typeModify;
    }

    public String getDate() {
        return "Date change: " + date.toString() + "\n";
    }

    public String getFilename() {
        return "File change: " + filename + "\n";
    }

    public String getTypeModify() {
        return "Type change: " + typeModify + "\n";
    }

    public String getSeparator() {
        return "-----------------------------------------------------------------\n";
    }
}
