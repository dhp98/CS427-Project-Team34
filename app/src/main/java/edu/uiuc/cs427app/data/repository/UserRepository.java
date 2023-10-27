package edu.uiuc.cs427app.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

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
}
