package pl.edu.pg.student.smarthome.DBUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.edu.pg.student.smarthome.models.Device;
import pl.edu.pg.student.smarthome.models.Profile;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "profiles_db";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Profile.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Profile.TABLE_NAME);
        onCreate(db);
    }

    public long insertProfile(Profile profile) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Gson gson = new Gson();
        Type type = new TypeToken<List<Device>>(){}.getType();
        String devicesToStore = gson.toJson(profile.getDevices());

        values.put(Profile.COLUMN_NAME, profile.getName());
//        values.put(Profile.COLUMN_STATUS, profile.getActive());
        values.put(Profile.COLUMN_DEVICES, devicesToStore);

        long id = db.insert(Profile.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public Profile getProfile(long id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Profile.TABLE_NAME,
                new String[]{Profile.COLUMN_ID, Profile.COLUMN_NAME, Profile.COLUMN_DEVICES},
                Profile.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Gson gson = new Gson();
        Type type = new TypeToken<List<Device>>(){}.getType();
        List<Device> devs = gson.fromJson(cursor.getString(cursor.getColumnIndex(Profile.COLUMN_DEVICES)), type);

        Profile profile = new Profile(
                cursor.getInt(cursor.getColumnIndex(Profile.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Profile.COLUMN_NAME)),
                devs,
                false);

        cursor.close();
        return profile;
    }

    public List<Profile> getAllProfiles(){

        List<Profile> profiles = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Profile.TABLE_NAME; // order by name

        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor cursor = db.rawQuery(selectQuery, null)) {

            if (cursor.moveToFirst()) {
                do {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Device>>() {
                    }.getType();
                    List<Device> devs = gson.fromJson(cursor.getString(cursor.getColumnIndex(Profile.COLUMN_DEVICES)), type);

                    Profile profile = new Profile(
                            cursor.getInt(cursor.getColumnIndex(Profile.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(Profile.COLUMN_NAME)),
                            devs,
                            false);

                    profiles.add(profile);
                } while (cursor.moveToNext());
            }
        }

        db.close();
        return profiles;
    }

    public void updateProfile(Profile profile){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Gson gson = new Gson();
        Type type = new TypeToken<List<Device>>(){}.getType();
        String devicesToStore = gson.toJson(profile.getDevices());

        values.put(Profile.COLUMN_NAME, profile.getName());
        values.put(Profile.COLUMN_DEVICES, devicesToStore);

        db.update(Profile.TABLE_NAME, values, Profile.COLUMN_ID
            + " = ?",
                new String[]{String.valueOf(profile.getId())});
    }

    public void deleteProfile(Profile profile){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Profile.TABLE_NAME, Profile.COLUMN_ID +
            "=?",
                new String[]{String.valueOf(profile.getId())});

        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ Profile.TABLE_NAME);
    }
}
