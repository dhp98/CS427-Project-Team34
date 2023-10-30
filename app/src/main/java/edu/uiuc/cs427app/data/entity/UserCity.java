package edu.uiuc.cs427app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "usercity")
public class UserCity implements Serializable {
    @PrimaryKey
    @NonNull
    private UUID id;

    @ColumnInfo(name = "username")
    @NonNull
    private String username;

    @ColumnInfo(name = "city")
    @NonNull
    private String city;


    @NonNull
    public UUID getId() {
        return id;
    }

    /**
     * Set user id
     * Warning:  id is automatically generated when creating a new user and trying to set a value
     * manually for the id can throw violation errors when saving the user to the DB.
     * Currently, there is no way to hide this as the Room library needs public getters and setters for all fields
     * @param id user id
     */
    public void setId(@NonNull UUID id) {
        this.id = id;
    }

}
