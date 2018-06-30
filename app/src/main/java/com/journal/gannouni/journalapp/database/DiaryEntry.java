package com.journal.gannouni.journalapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "diary")
public class DiaryEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String title;
    private String description;
    private Date dateEntry;

    @Ignore
    public DiaryEntry(String userId, String title, String description, Date dateEntry) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.dateEntry = dateEntry;
    }

    public DiaryEntry(int id, String userId, String title, String description, Date dateEntry) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateEntry = dateEntry;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(Date dateEntry) {
        this.dateEntry = dateEntry;
    }

}
