package com.ossovita.contactappmvvm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ossovita.contactappmvvm.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insert(Contact contact);//Contact objesi alıp insert yapacak

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAllContacts();

    @Query("SELECT * FROM contact_table ORDER BY name DESC")
    LiveData<List<Contact>> getAllContacts();


}
