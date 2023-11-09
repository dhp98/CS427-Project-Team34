package edu.uiuc.cs427app.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.uiuc.cs427app.data.dao.UserCityDao;
import edu.uiuc.cs427app.data.dao.UserDao;
import edu.uiuc.cs427app.data.entity.User;
import edu.uiuc.cs427app.data.entity.UserCity;
import edu.uiuc.cs427app.data.util.typeconverters.TimestampConverter;
import edu.uiuc.cs427app.data.util.typeconverters.UUIDConverter;

@Database(entities = {User.class, UserCity.class}, version = 7, exportSchema = true)
@TypeConverters({UUIDConverter.class, TimestampConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase DB_INSTANCE;
    private static final String DB_NAME = "cityweathermap_database";
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public abstract UserDao userDao();
    public abstract UserCityDao userCityDao();

    /**
     * Get app database singleton instance.
     * Singletons are best practice since initializing a database is costly.
     * @param context application context
     * @return AppDatabase instance
     */
    public static AppDatabase getDatabase(final Context context) {
        if (DB_INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (DB_INSTANCE == null) {
                    DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).addCallback(sRoomDatabaseCallback).fallbackToDestructiveMigration().build();
                }
            }
        }
        return DB_INSTANCE;
    }

    /**
     * Callback to do database cleanup operations once the database initialized.
     * This is useful during development phase as we want to startup with a clean slate.
     * Obviously this needs to be removed in production code.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
            });
        }
    };
}
