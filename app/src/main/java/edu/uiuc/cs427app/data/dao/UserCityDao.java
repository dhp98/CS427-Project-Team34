package edu.uiuc.cs427app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import edu.uiuc.cs427app.data.entity.UserCity;

@Dao
public interface UserCityDao {

    /**
     * Insert a UserCity into the database.
     * @param userCity The UserCity object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserCity userCity);

    /**
     * Delete a UserCity from the database.
     * @param userCity The UserCity object to be deleted.
     */
    @Delete
    void delete(UserCity userCity);

    /**
     * Update a UserCity in the database.
     * @param userCity The UserCity object to be updated.
     */
    @Update
    void update(UserCity userCity);

    /**
     * Get all UserCity entries from the database.
     * @return A list of UserCity objects.
     */
    @Query("SELECT * FROM user_city")
    LiveData<List<UserCity>> getAllUserCities();

    /**
     * Get a single UserCity by user ID.
     * @param userId The user ID to query.
     * @return The UserCity object associated with the user ID.
     */
    @Query("SELECT * FROM user_city WHERE user_id = :userId")
    LiveData<List<UserCity>> getUserCityByUserId(String userId);

    /**
     * Get a list of UserCity entries for a specific country.
     * @param country The country to query.
     * @return A list of UserCity objects.
     */
    @Query("SELECT * FROM user_city WHERE country = :country")
    LiveData<List<UserCity>> getUserCitiesByCountry(String country);

    /**
     * Get a list of UserCity entries for a specific state.
     * @param state The state to query.
     * @return A list of UserCity objects.
     */
    @Query("SELECT * FROM user_city WHERE state = :state")
    LiveData<List<UserCity>> getUserCitiesByState(String state);

    /**
     * Get a list of UserCity entries for a specific city name.
     * @param cityName The city name to query.
     * @return A list of UserCity objects.
     */
    @Query("SELECT * FROM user_city WHERE city_name = :cityName")
    LiveData<List<UserCity>> getUserCitiesByCityName(String cityName);
}
