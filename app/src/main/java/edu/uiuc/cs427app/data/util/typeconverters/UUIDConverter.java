package edu.uiuc.cs427app.data.util.typeconverters;

import androidx.room.TypeConverter;

import java.util.UUID;

/**
 * UUID converter
 * SQLite does not support UUID column types, so they need to be stored as a text string (TEXT)
 * For list of supported types in SQLite, see: https://www.sqlite.org/datatype3.html
 */
public class UUIDConverter {
    /**
     * Convert uuid to string
     * @param uuid UUID object
     * @return String
     */
    @TypeConverter
    public static String fromUUIDtoString(UUID uuid) {
        return uuid.toString();
    }

    /**
     * Convert string to UUID
     * @param stringUUID valid string representation of a UUID
     * @return UUID
     */
    @TypeConverter
    public static UUID toUUIDfromString(String stringUUID) {
        return UUID.fromString(stringUUID);
    }
}
