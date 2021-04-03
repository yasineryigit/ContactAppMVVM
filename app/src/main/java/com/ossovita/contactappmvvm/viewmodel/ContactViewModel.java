package com.ossovita.contactappmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ossovita.contactappmvvm.db.ContactRepository;
import com.ossovita.contactappmvvm.model.Contact;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository repository;
    LiveData<List<Contact>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository=new ContactRepository(application);
        allContacts=repository.getAllContacts();
    }

    public void insert(Contact contact){repository.insert(contact);}
    public void update(Contact contact){repository.update(contact);}
    public void delete(Contact contact){repository.delete(contact);}
    public void deleteAllContacts(){repository.deleteAllContacts();}
    public LiveData<List<Contact>> getAllContacts(){return allContacts;}
}
