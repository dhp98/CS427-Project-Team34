package edu.uiuc.cs427app.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import edu.uiuc.cs427app.database.AppDatabase;
import edu.uiuc.cs427app.data.dao.UserCityDao;
import edu.uiuc.cs427app.data.entity.UserCity;

public class UserCityRepository {
    private UserCityDao userCityDao;
    private LiveData<List<UserCity>> allUserCities;
    private ExecutorService executorService;

    public UserCityRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        userCityDao = db.userCityDao();
        allUserCities = userCityDao.getAllUserCities();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<UserCity>> getAllUserCities() {
        return allUserCities;
    }

    public LiveData<List<UserCity>> getUserCitiesByUserId(String userId) {
        return userCityDao.getUserCityByUserId(userId);
    }

    public void insert(UserCity userCity) {
        executorService.execute(() -> userCityDao.insert(userCity));
    }

    public void delete(UserCity userCity) {
        executorService.execute(() -> userCityDao.delete(userCity));
    }

    public void update(UserCity userCity) {
        executorService.execute(() -> userCityDao.update(userCity));
    }
}
