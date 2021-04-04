package com.ossovita.contactappmvvm.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ossovita.contactappmvvm.R;
import com.ossovita.contactappmvvm.model.Contact;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    private static ContactDatabase instance;
    private static Context activity;

    public abstract ContactDao contactDao();//Note batabase'in objesi oluşturulduğu zaman burası otomatik doldurulur

    public static synchronized ContactDatabase getInstance(Context context) {
        activity = context.getApplicationContext();
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class, "contact_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao contactDao;

        private PopulateDbAsyncTask(ContactDatabase db) {
            contactDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fillWithStartingData(activity);
            return null;
        }
    }

    private static void fillWithStartingData(Context context){
        ContactDao contactDao = getInstance(context).contactDao();
        JSONArray contacts = loadJSONArray(context);
        try{
            for (int i = 0; i < contacts.length(); i++) {
                contactDao.insert(new Contact(contacts.getJSONObject(i).getString("name"),contacts.getJSONObject(i).getString("phone")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static JSONArray loadJSONArray(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.contacts);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("contacts");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
