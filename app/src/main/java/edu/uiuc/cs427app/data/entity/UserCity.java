package edu.uiuc.cs427app.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "user_city",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE),
        indices = {@Index(value = "user_id")}) // Removed unique = true to allow multiple entries for the same user
public class UserCity {
    @PrimaryKey
    @ColumnInfo(name = "entry_id")
    @NonNull
    private String entryId; // A unique ID for each entry

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "city_name")
    private String cityName;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    // Constructor
    public UserCity(String userId, String cityName, String state, String country, double latitude, double longitude) {
        this.entryId = UUID.randomUUID().toString(); // Generate a unique ID for each entry
        this.userId = userId;
        this.cityName = cityName;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
