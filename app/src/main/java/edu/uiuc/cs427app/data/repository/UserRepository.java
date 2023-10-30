package edu.uiuc.cs427app.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

import edu.uiuc.cs427app.data.dao.UserDao;
import edu.uiuc.cs427app.data.entity.User;
import edu.uiuc.cs427app.database.AppDatabase;

public class UserRepository {
    private final UserDao userDao;
    private final LiveData<List<User>> allUsers;

    /**
     * Create an instance of the user repository.
     * This is a wrapper around the UserDao so that we don't have to inject the DAO and Entity in
     * the presentation layer.
     * @param context application context
     */
    public UserRepository(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context.getApplicationContext());
        this.userDao = database.userDao();
        this.allUsers = userDao.getAllUsersObservable();
    }

    /**
     * Get all users with an observable interface
     * @return User list
     */
    public LiveData<List<User>> getAllUsersObservable() {
        return allUsers;
    }

    /**
     * Insert user asynchronously
     * @param user User instance
     */
    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    /**
     * Update user asynchronously
     * @param user User instance
     * @return ListenableFuture interface to listen for completion
     */
    public ListenableFuture<Void> update(User user) {
        return userDao.update(user);
    }

    /**
     * Delete user asynchronously
     * @param user User instance
     */
    public void delete(User user) {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    /**
     * Get user by email and password with an observable interface
     * @param email user email
     * @param password user password
     * @return User
     */
    public LiveData<User> login(String email, String password) {
        return userDao.login(email, password);
    }

    /**
     * Get user by email with an observable interface
     * @param email user email
     * @return User
     */
    public LiveData<User> getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    /**
     * Get user by email synchronously
     * @param email user email
     * @return User
     */
    public User getByEmailSync(String email) {
        return userDao.getByEmailSync(email);
    }

    /**
     * Insert user async task
     */
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDao userDao;

        /**
         * Create instance of Async task to insert user
         * @param userDao UserDao instance
         */
        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        /**
         * Do operation in background without blocking main thread
         * @param users User
         * @return null
         */
        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    /**
     * Delete user async task
     */
    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDao userDao;

        /**
         * Create instance of Async task to insert user
         * @param userDao UserDao instance
         */
        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        /**
         * Do operation in background without blocking main thread
         * @param users User
         * @return null
         */
        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }


    /**
     * Add a city to a user's city list.
     * @param email User's email
     * @param city The city to add
     */
    public void addCity(String email, String city) {
        new ModifyCityAsyncTask(userDao, ModifyAction.ADD).execute(email, city);
    }

    /**
     * Remove a city from a user's city list.
     * @param email User's email
     * @param city The city to remove
     */
    public void removeCity(String email, String city) {
        new ModifyCityAsyncTask(userDao, ModifyAction.REMOVE).execute(email, city);
    }

    private enum ModifyAction { ADD, REMOVE }

    private static class ModifyCityAsyncTask extends AsyncTask<String, Void, Void> {
        private final UserDao userDao;
        private final ModifyAction action;

        private ModifyCityAsyncTask(UserDao userDao, ModifyAction action) {
            this.userDao = userDao;
            this.action = action;
        }

        @Override
        protected Void doInBackground(String... params) {
            String email = params[0];
            String city = params[1];
            User user = userDao.getByEmailSync(email);
            if (user == null) return null;

            String cityListStr = user.getCityList();
            List<String> cityList = cityListStr == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(cityListStr.split(",")));

            if (action == ModifyAction.ADD && !cityList.contains(city)) {
                cityList.add(city);
            } else if (action == ModifyAction.REMOVE) {
                cityList.remove(city);
            }
            user.setCityList(String.join(",", cityList));
            userDao.update(user);
            return null;
        }
    }
}


