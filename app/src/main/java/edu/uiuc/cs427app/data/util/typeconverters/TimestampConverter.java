package edu.uiuc.cs427app.data.util.typeconverters;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Timestamp/Date converter
 * SQLite does not support Date column types, so they need to be stored as an 8 byte integer (Long)
 * For list of supported types in SQLite, see: https://www.sqlite.org/datatype3.html
 */
public class TimestampConverter {
    /**
     * Convert long integer to Java Date type.
     * @param value Long integer
     * @return Date
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Convert date to long integer
     * @param date Date
     * @return Long integer
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
