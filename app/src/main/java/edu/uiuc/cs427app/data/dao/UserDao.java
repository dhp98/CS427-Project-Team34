package edu.uiuc.cs427app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import edu.uiuc.cs427app.data.entity.User;

@Dao
public interface UserDao {

    /**
     * Get user by email with an observable interface
     * @param email email id
     * @return User
     */
    @Query("SELECT * FROM user where email = :email")
    LiveData<User> getByEmail(String email);

    /**
     * Get user by email
     * @param email email id
     * @return User
     */
    @Query("SELECT * FROM user where email = :email")
    User getByEmailSync(String email);

    /**
     * Insert user into DB
     * @param user User
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    /**
     * Delete user from DB
     * @param user User
     */
    @Delete
    void delete(User user);

    /**
     * Update user in DB with a listenable interface
     * @param user User
     * @return void
     */
    @Update
    ListenableFuture<Void> update(User user);

    /**
     * Get all users with sorted by login name with an observable interface
     * @return User list
     */
    @Query("SELECT * FROM user ORDER BY login_name ASC")
    LiveData<List<User>> getAllUsersObservable();

    /**
     * Get user matching email and password with an observable interface
     * @param email user email
     * @param password user password
     * @return User
     */
    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    LiveData<User> login(String email, String password);
}
